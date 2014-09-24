package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

import exceptions.ServerFileException;

import account.User;

import model.creatures.Job;
import model.creatures.Mob;
import model.creatures.NPC;
import model.creatures.Player;
import model.mechanics.Mechanics;
import model.world.Direction;
import model.world.Room;
import model.world.World;
import model.world.Zone;
import network.LoginPacket;
import network.LookPacket;
import network.Packet;
import network.SafeCreaturePacket;
import network.SafePlayerPacket;
import network.Type;

/**
 * Server class contains main method for MUD server.
 * 
 * 
 * @author Daniel Eisenberg
 */
public class Server {

	/**
	 * This is the port Server will listen for connections on.
	 */
	private static final int PORT = 4200;
	private ServerSocket serverSocket;

	private boolean restart;
	private boolean shuttingDown;
	private boolean shutdownCommand;

	static ConcurrentMap<Player, Connection> connections;
	static ConcurrentMap<String, Player> playerLookup; // lookup table for
														// player by name
	static ConcurrentMap<String, User> usersOnline; // lookup table for user by
													// name
	private static BlockingQueue<Command> eventQueue;

	private boolean threadFinished; // flag tells main thread when at least one
									// thread should be dereferenced
	private List<ListenerThread> inputThreads;
	private List<ReporterThread> outputThreads;
	private Thread eventThread;
	private Thread cityActionThread;
	private Thread shutdownThread;
	private Thread consoleThread;
	static ConcurrentMap<MobActionThread, Zone> dungeonList;

	private ConcurrentSkipListSet<String> userList;
	private ConcurrentSkipListSet<String> characterNameList;
	private ConcurrentSkipListSet<String> ipBanList;
	private ConcurrentSkipListSet<String> banList;

	private static World world;

	private static final String PATH = "serverfiles";
	private File usersFile;
	private File characterNameFile;
	private File ipBanFile;
	private File banFile;

	private File errorLog;
	private PrintStream err;

	private static final String LOG_NAME = "error.txt";

	/**
	 * Creates and initializes a Server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server server = new Server();
			while (server.restart)
				server = new Server();
			System.exit(0);
		} catch (ServerFileException sfe) {
			System.out.println(sfe);
			System.exit(2);
		}
	}

	/**
	 * Initializes non-persistent server objects.
	 * 
	 */
	private Server() {
		restart = false;
		shuttingDown = false;
		shutdownCommand = false;
		threadFinished = false;

		// Route System.err to log file
		errorLog = new File(PATH, LOG_NAME);
		try {
			if (!errorLog.isFile()) {
				errorLog.getParentFile().mkdirs();
			} else {
				File prev = new File(PATH, "prevlog.txt");
				if (prev.isFile())
					prev.delete();
				errorLog.renameTo(prev);
			}
			errorLog.createNewFile();
			err = new PrintStream(errorLog);
			System.setErr(err);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// build Mechanics static tables
		new Mechanics();

		// open server socket
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("Unable to create server socket on port " + PORT);
			System.exit(1);
		}

		// initialize tables
		connections = new ConcurrentHashMap<Player, Connection>();
		playerLookup = new ConcurrentHashMap<String, Player>();
		usersOnline = new ConcurrentHashMap<String, User>();

		// initialize thread reference lists
		// note: these are only intended to prevent garbage collection of active
		// threads, they are not intended to be accessed by anything but the
		// eventThread
		inputThreads = new LinkedList<ListenerThread>();
		outputThreads = new LinkedList<ReporterThread>();

		// setup eventThread and eventQueue, start eventThread
		eventThread = new EventThread();
		eventQueue = new LinkedBlockingQueue<Command>();
		eventThread.start();

		// initialize mobActionThread
		cityActionThread = new CityActionThread();

		consoleThread = new ConsoleThread();

		startup();
	}

	/**
	 * Loads files to support Server and begins listen loop.
	 */
	private void startup() {
		// initialize File objects
		usersFile = new File(PATH, "userList.dat");
		characterNameFile = new File(PATH, "characterNameList.dat");
		ipBanFile = new File(PATH, "ipBanList.dat");
		banFile = new File(PATH, "banList.dat");

		userList = loadListFile(usersFile);
		characterNameList = loadListFile(characterNameFile);
		ipBanList = loadListFile(ipBanFile);
		banList = loadListFile(banFile);

		loadWorld();

		dungeonList = new ConcurrentHashMap<MobActionThread, Zone>();

		cityActionThread.start();
		consoleThread.start();

		listen();
	}

	/**
	 * Attempts to deserialize the specified file into a ConcurrentSkipListSet
	 * of String objects. If the file cannot be resolved, this method will
	 * return an initialized ConcurrentSkipListSet.
	 * 
	 * @param file
	 *            - a file corresponding to the ConcurrentSkipListSet object to
	 *            be initialized.
	 * @return The object stored in the specified file, or a new
	 *         ConcurrentSkipListSet if no such file exists.
	 */
	@SuppressWarnings("unchecked")
	private ConcurrentSkipListSet<String> loadListFile(File file) {
		try {
			ConcurrentSkipListSet<String> set;
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			set = (ConcurrentSkipListSet<String>) ois.readObject();
			ois.close();
			fis.close();
			if (set != null)
				return set;
			return new ConcurrentSkipListSet<String>();
		} catch (FileNotFoundException e) {
			System.out.println("No file exists: " + file.getPath());
			System.out.println("Creating new list.");
			return new ConcurrentSkipListSet<String>();
		} catch (IOException e) {
			e.printStackTrace();
			try { // attempt to load backup file
				ConcurrentSkipListSet<String> set;
				FileInputStream fis = new FileInputStream(new File(
						file.getPath() + ".bak"));
				ObjectInputStream ois = new ObjectInputStream(fis);
				set = (ConcurrentSkipListSet<String>) ois.readObject();
				ois.close();
				fis.close();
				if (set != null)
					return set;
				return new ConcurrentSkipListSet<String>();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				System.out
						.println("No file exists: " + file.getPath() + ".bak");
				System.out.println("Creating new list.");
				return new ConcurrentSkipListSet<String>();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new ServerFileException("Could not load "
						+ file.getPath());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				throw new ServerFileException("Could not load "
						+ file.getPath());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ServerFileException("Could not load " + file.getPath());
		}
	}

	/**
	 * Loads the server's World file. If there is no file, a default World
	 * builder is invoked instead.
	 */
	private void loadWorld() {

		FileInputStream fis;
		ObjectInputStream ois;
		try {
			fis = new FileInputStream(new File(PATH, "world.dat"));
			ois = new ObjectInputStream(fis);
			world = (World) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No world file found.");
			System.out.println("Creating new world.");
			world = new World();
			if (world == null) {
				throw new ServerFileException("Could not load world.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ServerFileException("Could not load world.");
		}
	}

	/**
	 * Server main loop. Listens for connections on PORT and starts threads to
	 * handle them.
	 * 
	 */
	private void listen() {
		System.out.println("Listening for connections on port " + PORT + ".");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				if (ipBanList
						.contains(socket.getInetAddress().getHostAddress())) {
					ObjectOutputStream oos = new ObjectOutputStream(
							socket.getOutputStream());
					oos.writeObject(new Packet(Type.ERROR,
							"This address has been banned."));
					oos.flush();
					oos.close();
					socket.close();
					continue;
				}
				Connection c = new Connection(socket);

				// start threads for this connection
				Thread thread = new ListenerThread(c);
				thread.start();
				inputThreads.add((ListenerThread) thread);

				thread = new ReporterThread(c);
				thread.start();
				outputThreads.add((ReporterThread) thread);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (shuttingDown) {
					shuttingDown = false;
					shutdown();
					return;
				}
			}
			// remove unneeded threads
			if (threadFinished) {
				for (ListenerThread t : inputThreads)
					if (!t.needed)
						inputThreads.remove(t);
				for (ReporterThread t : outputThreads)
					if (t.done)
						outputThreads.remove(t);
				// reset flag
				threadFinished = false;
			}
		}
	}

	/**
	 * Tells all threads to cease activity, all persistent data written to file.
	 */
	private void shutdown() {

		endThreads();

		saveFile(usersFile, userList);
		saveFile(characterNameFile, characterNameList);
		saveFile(ipBanFile, ipBanList);
		saveFile(banFile, banList);
		saveFile(new File(PATH, "world.dat"), world);

		err.close();
		while (!okayToExit())
			; // ListenerThreads may be writing to file.
		return;
	}

	/**
	 * Checks all ListenerThreads to see if any are still saving files.
	 * 
	 * @return true if and only if all ListenerThreads have finished their
	 *         tasks.
	 */
	private boolean okayToExit() {
		for (ListenerThread t : inputThreads)
			if (t.needed)
				return false;
		return true;
	}

	/**
	 * Saves the specified Serializable object to the specified file.
	 * 
	 * @param file
	 *            - the destination file
	 * @param toSave
	 *            - the Serializable to be written
	 */
	private void saveFile(File file, Serializable toSave) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			if (!file.isFile()) {
				file.createNewFile();
			} else {
				File backup = new File(PATH, file.getName() + ".bak");
				if (backup.isFile())
					backup.delete(); // remove old backup file
				file.renameTo(backup);
			}
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(toSave);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			try { // make second attempt
				if (!file.isFile())
					file.createNewFile();
				fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(toSave);
				oos.close();
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Could not write " + file.getName() + "!");
			}
		}
	}

	/**
	 * Tells all connection threads to close their connections and save data.
	 */
	private void endThreads() {
		for (ListenerThread t : inputThreads) {
			t.end();
		}

		for (ReporterThread t : outputThreads) {
			t.end();
		}
	}

	/**
	 * Places a Command on the eventQueue.
	 * 
	 * @param event
	 *            - the Command to be executed.
	 * @throws InterruptedException
	 */
	public static void queueEvent(Command event) throws InterruptedException {
		eventQueue.put(event);
	}

	/**
	 * Convenience method broadcasts to all players in the specified mob's room.
	 * 
	 * @param m
	 *            - the mob triggering the event.
	 * @param message
	 *            - the message to broadcast.
	 */
	public static void roomEvent(Mob m, String message) {
		try {
			eventQueue.put(new RoomCommand(m.getRoom(), message,
					Type.SERVER_MESSAGE));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the server's world's default spawn location as specified by
	 * World.getSpawn().
	 * 
	 * @return the server's world's default spawn location.
	 */
	public static Room getSpawn() {
		return world.getSpawn();
	}

	/**
	 * Handles banbyip, ban, and kick commands from users with moderation
	 * privileges.
	 * 
	 * @param which
	 *            - the moderation Type: BAN_IP, BAN, or KICK. <br>
	 * <br>
	 *            A BAN_IP request is also a BAN request, <br>
	 *            a BAN action is also a KICK request, <br>
	 *            KICK removes the specified user from the server without
	 *            enacting any bans. <br>
	 * <br>
	 * @param who
	 *            - the Player belonging to the User who is to be moderated.
	 * @return true if the player was successfully moderated.
	 * @throws InterruptedException
	 */
	private boolean moderate(Type which, Player who)
			throws InterruptedException {
		Connection c = connections.get(who);
		String message = "You have been ";
		if (c == null) {
			// banning requires player to be online
			// otherwise write username to appropriate serverfiles file
			return false;
		}
		if (which == Type.BAN_IP) {
			ipBanList.add(c.socket.getInetAddress().getHostAddress());
		}
		if (which != Type.KICK) {
			banList.add(c.user.getUsername());
			message += "banned ";
		} else
			message += "kicked ";
		message += "from the server.";
		c.outputQueue.put(new Packet(Type.SERVER_MESSAGE, message));
		c.outputQueue.put(Packet.valueOf(Packet.POISON));
		try {
			c.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * ListenerThreads handle client handshaking and route client commands.
	 * 
	 * start() logs in client creating new user and/or player as necessary.
	 * 
	 * @author Daniel Eisenberg
	 */
	private class ListenerThread extends Thread {

		private Connection connection;
		private boolean done;
		private boolean needed;
		private long healTimer;

		/**
		 * Creates a new ListenerThread with the specified Connection.
		 * 
		 * @param c
		 *            - the connection for this ListenerThread
		 * 
		 */
		public ListenerThread(Connection c) {
			super();
			connection = c;
			needed = true;
		}

		/**
		 * ListenerThread will handle login before entering main loop to receive
		 * commands.
		 * 
		 */
		public void run() {
			while (connection.user == null && !done)
				try {
					login();
				} catch (IOException e1) {
					e1.printStackTrace();
					end();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
					end();
				} catch (InterruptedException e2) {
					interrupt();
				} finally {
					if (isInterrupted())
						interrupted();
				}
			// TODO more robust spawning
			connection.player.setRoom(world.getSpawn());
			world.getSpawn().addPlayer(connection.player);
			healTimer = System.currentTimeMillis();
			try {
				eventQueue.put(new CreatureUpdate(connection.player,
						connection.player));
				eventQueue.put(new Broadcast(new Packet(Type.SERVER_BROADCAST,
						connection.player.getName() + " has connected.")));
				connection.outputQueue.put(new LookPacket(Type.LOOK, world
						.getSpawn().look(), "icons" + File.separator
						+ "background.png"));
			} catch (InterruptedException e1) {
				interrupted();
			}
			while (!done) {
				try {
					Packet command = (Packet) connection.ois.readObject();

					if (command == null) {// client disconnected
						done = true;
						break;
					}

					sort(command);
					if (System.currentTimeMillis() - healTimer > 60000) {
						connection.player.heal();
						healTimer = System.currentTimeMillis();
					}
				} catch (IOException e) {
					e.printStackTrace();
					done = true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					done = true;
				} catch (InterruptedException e) {
					interrupt();
				} finally {
					if (isInterrupted())
						interrupted();
				}
			}
			cleanup();
		}

		/**
		 * Determines an action for the server to take based upon the content of
		 * the packet argument and adds this action to the eventQueue if needed.
		 * 
		 * @param packet
		 *            - the Packet to be converted to a Command.
		 * @throws IOException
		 *             , InterruptedException
		 */
		private void sort(Packet packet) throws IOException,
				InterruptedException {
			String msg = packet.getMessage();
			switch (packet.getType()) {
			case ABILITIES:
				eventQueue.put(new GetAbilitiesCommand(connection.player));
				break;
			case ATTACK:
				eventQueue.put(new AttackCommand(connection.player, msg));
				break;
			case CAST:
				String abil = model.mechanics.Abilities.getAbilityByName(msg);
				if (abil != null) {
					String creature = msg.substring(abil.length()).trim();
					eventQueue.put(new CastCommand(connection.player, abil,
							creature));
				} else {
					connection.outputQueue.put(new Packet(Type.ERROR,
							"Ability not found."));
				}
				break;
			case DROP:
				eventQueue.put(new DropCommand(connection.player, msg));
				break;
			case ENTER:
				if (connection.player.isLeader()
						|| !connection.player.hasParty()) {
					if (world.getCity().listRooms()
							.contains(connection.player.getRoom())) {
						connection.dungeonKey = new MobActionThread();
						// create a new dungeon to map to this key thread
						eventQueue.put(new CreateDungeonCommand(
								connection.player, connection.dungeonKey, msg));
					} else {
						connection.outputQueue.put(new Packet(Type.ERROR,
								"You must leave your current dungeon first."));
					}
				} else {
					connection.outputQueue.put(Packet
							.valueOf(Packet.NOT_LEADER));
				}
				break;
			case EQUIP:
				eventQueue.put(new EquipCommand(connection.player, msg));
				break;
			case EXIT:
				if (connection.dungeonKey != null)
					dungeonList.remove(connection.dungeonKey);
				if (connection.player.hasParty()) {
					eventQueue.put(new MoveCommand(connection.player, null));
				} else {
					connection.player.setRoom(getSpawn());
					eventQueue.put(new LookCommand(connection.player));
				}
				break;
			case GET:
				eventQueue.put(new GetCommand(connection.player, msg));
				break;
			case GIVE:
				int space = msg.lastIndexOf(" ");
				if (space != -1) {
					String to = Names.formatName(msg.substring(space + 1));
					Player recipient = playerLookup.get(to);
					String toGive = msg.substring(0, space);
					if (recipient != null)
						eventQueue.put(new GiveCommand(connection.player,
								recipient, toGive));
					else
						connection.outputQueue.put(new Packet(Type.ERROR, to
								+ "not found."));
				} else {
					invalidCommand();
				}
				break;
			case INVENTORY:
				eventQueue.put(new InventoryCommand(connection.player));
				break;
			case INVITE:
				String target = Names.formatName(msg);
				Player toInvite = playerLookup.get(target);
				if (toInvite != null) {
					if (connection.player.isLeader()) {
						eventQueue.put(new InviteCommand(connection.player,
								toInvite));
						connection.pendingInvites.add(toInvite);
					} else {
						connection.outputQueue.put(Packet
								.valueOf(Packet.NOT_LEADER));
					}
				} else {
					connection.outputQueue.put(new Packet(Type.ERROR, target
							+ "not found."));
				}
				break;
			case JOIN:
				target = Names.formatName(msg);
				Player leader = playerLookup.get(msg);
				if (leader == null) {
					connection.outputQueue.put(new Packet(Type.ERROR, target
							+ "not found."));
					break;
				}
				if (connections.get(leader).pendingInvites
						.contains(connection.player)) {
					eventQueue.put(new JoinCommand(leader, connection.player));
					connections.get(leader).pendingInvites
							.remove(connection.player);
				} else
					connection.outputQueue
							.put(new Packet(Type.ERROR,
									"You must be invited before you can join a party."));
				break;
			case LEAVE_PARTY:
				if (connection.player.hasParty())
					eventQueue.put(new LeavePartyCommand(connection.player));
				else
					connection.outputQueue.put(new Packet(Type.ERROR,
							"You are not in a party."));
				break;
			case LOGOUT:
				connection.outputQueue.put(Packet
						.valueOf(Packet.LOGOUT_MESSAGE));
				connection.outputQueue.put(Packet.valueOf(Packet.POISON));
				sleep(3000);
				cleanup();
				break;
			case LOOK:
				eventQueue.put(new LookCommand(connection.player));
				break;
			case MAKE_LEADER:
				if (connection.player.isLeader()) {
					target = Names.formatName(msg);
					Player makeLead = playerLookup.get(target);
					if (makeLead != null)
						eventQueue.put(new ChangeLeader(connection.player,
								makeLead));
					else
						connection.outputQueue.put(new Packet(Type.ERROR,
								target + "not found."));
				} else {
					connection.outputQueue.put(Packet
							.valueOf(Packet.NOT_LEADER));
				}
				break;
			case META_DATA:
				connection.outputQueue.put(new SafePlayerPacket(
						connection.player));
				break;
			case MOVE:
				Direction direction = null;
				switch (msg.charAt(0)) {
				case 'n':
					direction = Direction.NORTH;
					break;
				case 's':
					direction = Direction.SOUTH;
					break;
				case 'w':
					direction = Direction.WEST;
					break;
				case 'e':
					direction = Direction.EAST;
					break;
				case 'u':
					direction = Direction.UP;
					break;
				case 'd':
					direction = Direction.DOWN;
					break;

				}
				if (direction != null)
					eventQueue
							.put(new MoveCommand(connection.player, direction));
				else
					invalidCommand();
				break;
			case OOC: // broadcast ooc chat message
				eventQueue.put(new Broadcast(new Packet(Type.OOC,
						connection.player.getName() + ": " + msg)));
				break;
			case PARTY: // send chat message to each party member
				if (connection.player.hasParty())
					eventQueue.put(new PartyCommand(connection.player, msg));
				else
					connection.outputQueue.put(new Packet(Type.ERROR,
							"You are not in a party."));
				break;
			case SAY:
				eventQueue.put(new RoomCommand(connection.player.getRoom(),
						connection.player.getName() + " says: " + msg, packet
								.getType()));
				break;
			case SERVER_MESSAGE:
				// not currently used for any incoming packets
				invalidCommand();
				break;
			case SOCIAL: // emote or similar
				eventQueue.put(new RoomCommand(connection.player.getRoom(),
						connection.player.getName() + " " + msg, packet
								.getType()));
				break;
			case STATUS:
				connection.outputQueue.put(new Packet(Type.STATUS,
						connection.player.status()));
				break;
			case TELL: // sends a private message
				if (msg.indexOf(' ') == -1) {
					invalidCommand();
					break;
				}
				String message = msg.substring(msg.indexOf(' ') + 1);
				target = msg.substring(0, msg.indexOf(' '));
				target = Names.formatName(target);
				if (playerLookup.get(target) != null)
					eventQueue.put(new TellCommand(connection.player,
							playerLookup.get(target), message));
				else
					connection.outputQueue.put(new Packet(Type.ERROR, target
							+ "not found."));
				break;
			case UNEQUIP:
				eventQueue.put(new UnequipCommand(connection.player, msg));
				break;
			case UNINVITE:
				target = Names.formatName(msg);
				if (connection.player.hasParty()) {
					Player toRemove = playerLookup.get(target);
					if (toRemove != null)
						eventQueue.put(new UninviteCommand(connection.player,
								toRemove));
					else
						connection.outputQueue.put(new Packet(Type.ERROR,
								target + "not found."));
				} else {
					connection.outputQueue.put(new Packet(Type.ERROR, target
							+ "not found."));
				}
				break;
			case USE:
				eventQueue.put(new UseCommand(connection.player, msg));
				break;
			case WHO:
				if (msg.indexOf(' ') == -1) {
					invalidCommand();
					break;
				}
				target = msg.substring(0, msg.indexOf(' '));
				target = Names.formatName(target);
				Player who = playerLookup.get(target);
				if (who != null)
					connection.outputQueue.put(new Packet(Type.WHO, target
							+ ": " + who.status()));
				else
					connection.outputQueue.put(new Packet(Type.ERROR, target
							+ "not found."));
				break;
			case MAKE_MODERATOR:
				if (!connection.user.isModerator())
					connection.outputQueue.put(Packet
							.valueOf(Packet.NO_PERMISSION));
				else {
					target = msg.substring(0, msg.indexOf(' '));
					target = Names.formatName(target);
					Connection tarConn = connections.get(playerLookup
							.get(target));
					if (tarConn != null) {
						tarConn.user.setModerator(true);
						connection.outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, target
										+ " now has moderation privileges."));

						tarConn.outputQueue
								.put(new Packet(
										Type.SERVER_MESSAGE,
										connection.player.getName()
												+ " has granted you moderation privileges."));
					} else {
						connection.outputQueue.put(new Packet(Type.ERROR,
								target + "not found."));
					}
				}
				break;
			case BAN_IP: // moderation commands
			case BAN:
			case KICK:
				if (!connection.user.isModerator())
					connection.outputQueue.put(Packet
							.valueOf(Packet.NO_PERMISSION));
				else {
					Type which = packet.getType();
					msg = Names.formatName(msg);
					who = playerLookup.get(msg);
					if (who != null && moderate(which, who))
						connection.outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, msg
										+ " has been moderated."));
				}
				break;
			case RESTART: // a moderator can order server shutdown or restart
							// remotely
			case SHUTDOWN:
				if (connection.user.isModerator()) {
					int time = 0;
					if (!msg.equals(""))
						try {
							time = Integer.parseInt(msg);
						} catch (NumberFormatException nfe) {
							invalidCommand();
						}
					if (!shutdownCommand)
						eventQueue.put(new ShutdownCommand(time, (packet
								.getType() == Type.RESTART)));
					else
						connection.outputQueue.put(Packet
								.valueOf(Packet.SHUTDOWN_IN_PROGRESS));
				} else {
					connection.outputQueue.put(Packet
							.valueOf(Packet.NO_PERMISSION));
				}
				break;
			default:
				invalidCommand();
				break;
			}
		}

		/**
		 * Enqueues an "invalid command" response packet.
		 * 
		 * @throws InterruptedException
		 */
		private void invalidCommand() throws InterruptedException {
			connection.outputQueue.put(Packet.valueOf(Packet.INVALID_COMMAND));
		}

		/**
		 * User login including new account creation.
		 * 
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * @throws InterruptedException
		 */
		private void login() throws IOException, ClassNotFoundException,
				InterruptedException {
			LoginPacket loginPacket = (LoginPacket) connection.ois.readObject();
			String username;
			boolean success = false;
			do {
				username = loginPacket.getUsername().toLowerCase();
				switch (loginPacket.getType()) {
				case NEW_ACCOUNT:
					// try to create a new account with the specified name
					if (userList.contains(username)) {
						connection.outputQueue.put(new Packet(Type.ERROR,
								"That username is taken."));
					} else if (username.equals("")) {
						connection.outputQueue.put(new Packet(Type.ERROR,
								"Please enter a username."));
					} else { // create new user
						connection.user = new User(username);
						connection.user.setPassword(loginPacket.getPassword());
						userList.add(connection.user.getUsername());
						usersOnline.put(connection.user.getUsername(),
								connection.user);
						success = true;
					}
					break;

				case LOGIN:
					if (!usersOnline.containsKey(username)
							&& !banList.contains(username)) {
						connection.user = User.load(username);
					}
					// check loaded and check password
					if (connection.user != null) {
						if (connection.user.checkPassword(loginPacket
								.getPassword())) {
							usersOnline.put(connection.user.getUsername(),
									connection.user);
							success = true;
							userList.add(username);
						} else
							connection.outputQueue.put(new Packet(Type.ERROR,
									"Incorrect password."));
					} else if (usersOnline.containsKey(username)) {
						connection.outputQueue.put(new Packet(Type.ERROR,
								"That user is online."));
					} else {
						connection.outputQueue.put(Packet
								.valueOf(Packet.LOGIN_FAILED));
					}
					break;

				default:
					connection.oos
							.writeObject(new Packet(Type.ERROR,
									"Login error. Please ensure client is up to date."));
				}
				if (!success) // login failed: get new loginPacket
					loginPacket = (LoginPacket) connection.ois.readObject();
			} while (!success);
			connection.outputQueue.put(Packet.valueOf(Packet.LOGIN_SUCCESSFUL));
			selectCharacter();
		}

		/**
		 * Prompts client to select character. Calls createCharacter() if user
		 * has no characters.
		 * 
		 * @throws IOException
		 * @throws ClassNotFoundException
		 * @throws InterruptedException
		 * 
		 */
		private void selectCharacter() throws IOException,
				ClassNotFoundException, InterruptedException {
			List<Player> characters = connection.user.listCharacters();
			if (!characters.isEmpty()) { // prompt client for character choice
				do {
					// send character list
					String listOfChars = "";
					for (Player p : characters)
						listOfChars += p.getName() + " ";
					connection.outputQueue.put(new Packet(
							Type.CHARACTER_SELECT, listOfChars.trim()));
					Packet choice = (Packet) connection.ois.readObject();
					if (choice.getType() == Type.CHARACTER_SELECT) {
						String name = Names.formatName(choice.getMessage());
						// find character
						for (Player p : characters)
							if (p.getName().equalsIgnoreCase(name)) {
								// store references
								connection.player = p;
								connections.put(p, connection);
								playerLookup.put(name, p);
								connection.outputQueue
										.put(new Packet(Type.SERVER_MESSAGE,
												name + " loaded."));
								return;
							}
					} else if (choice.getType() == Type.DELETE_CHARACTER) {
						deleteCharacter(choice);
						if (characters.isEmpty())
							connection.outputQueue.put(new Packet(
									Type.SERVER_MESSAGE,
									"Create a new character."));
					} else if (choice.getType() == Type.CREATE_CHARACTER) {
						if (!createCharacter(choice))
							selectCharacter();
						return;
					}
				} while (connection.player == null && !characters.isEmpty());

			} else
				connection.outputQueue.put(new Packet(Type.SERVER_MESSAGE,
						"Create a new character."));
			while (characters.isEmpty() && !createCharacter())
				connection.outputQueue.put(new Packet(Type.SERVER_MESSAGE,
						"Create a new character."));
		}

		private boolean createCharacter() throws IOException,
				ClassNotFoundException, InterruptedException {
			return createCharacter(null);
		}

		/**
		 * Creates a new character and associates it with this user's
		 * connection.
		 * 
		 * @throws IOException
		 * @throws ClassNotFoundException
		 * @throws InterruptedException
		 */
		private boolean createCharacter(Packet p) throws IOException,
				ClassNotFoundException, InterruptedException {
			Packet create = (p == null) ? (Packet) connection.ois.readObject() : p;
			int space = create.getMessage().indexOf(" ");
			if (space < 2 || space > 14) {
				connection.oos
						.writeObject(new Packet(Type.ERROR,
								"Please enter a name for your character between 2 and 15 letters long."));
				return false;
			}

			String name = Names.formatName(create.getMessage().substring(0,
					space));
			String job = create.getMessage().substring(space + 1);
			if (!Names.validateName(name)) {
				connection.outputQueue.put(new Packet(Type.ERROR,
						"Character names may only contain letters."));
				return false;
			}

			if (characterNameList.contains(name)) {
				connection.outputQueue.put(new Packet(Type.ERROR,
						"A character with that name already exists."));
				return false;
			}

			Player created = connection.user.createNewCharacter(name,
					Job.valueOf(job));
			if (created == null) {
				connection.outputQueue
						.put(new Packet(Type.ERROR,
								"Character creation error. Please check client version."));
				return false;
			}
			// register new character
			connection.player = created;
			connections.put(created, connection);
			playerLookup.put(name, created);
			characterNameList.add(name);
			connection.outputQueue.put(new Packet(Type.SERVER_MESSAGE, name
					+ " created."));
			return true;
		}

		/**
		 * Called by selectCharacter(), this method attempts to remove a
		 * character from a user's list of characters and from the server's list
		 * of character names.
		 * 
		 * @throws IOException
		 * @throws InterruptedException
		 * 
		 */
		private void deleteCharacter(Packet choice) throws IOException,
				InterruptedException {
			for (Player p : connection.user.listCharacters())
				if (p.getName().equalsIgnoreCase(choice.getMessage())) {
					if (connection.user.deleteCharacter(p)) {
						characterNameList.remove(p.getName());
						connection.outputQueue.put(new Packet(
								Type.SERVER_MESSAGE, choice.getMessage()
										+ " deleted."));
						return;
					}
				}
			connection.outputQueue.put(new Packet(Type.SERVER_MESSAGE,
					"Could not delete " + choice.getMessage() + "."));
		}

		/**
		 * Tells the thread to close its associated stream.
		 */
		public void end() {
			done = true;
			try {
				connection.ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Writes this thread's associated User to file and sets flag for this
		 * thread to be dereferenced.
		 */
		private void cleanup() {
			if (connection.player != null) {
				if (connection.player.getRoom() != null)
					connection.player.getRoom().removePlayer(connection.player);
				if (connection.player.hasParty()) {
					if (connection.player.isLeader())
						try {
							eventQueue.put(new ChangeLeader(connection.player));
						} catch (InterruptedException e) {
							interrupted();
						}
					else
						try {
							eventQueue.put(new LeavePartyCommand(
									connection.player));
						} catch (InterruptedException e) {
							interrupted();
						}
				} else if (connection.dungeonKey != null)
					dungeonList.remove(connection.dungeonKey);
			}
			if (!shuttingDown && connection.player != null) {
				try {
					eventQueue.put(new Broadcast(new Packet(
							Type.SERVER_BROADCAST, connection.player.getName()
									+ "has disconnected.")));
				} catch (InterruptedException e) {
					interrupted();
				}
			}
			if (connection.user != null) {
				try {
					// first attempt
					connection.user.save(false);
				} catch (IOException e) {
					e.printStackTrace();
					try {
						// second attempt
						connection.user.save(true);
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println(connection.user.getUsername()
								+ " user file could not be saved!");
					}
				}
				// clean up reference tables
				usersOnline.remove(connection.user.getUsername());
				if (connection.player != null) {
					connections.remove(connection.player);
					playerLookup.remove(connection.player.getName());
				}
			}
			if (!done)
				end();
			needed = false;
			threadFinished = true;
		}
	}

	/**
	 * ReporterThread serializes outgoing Packets.
	 * 
	 * Calling start() asks ReporterThread to begin sending output.
	 * 
	 * @author Daniel Eisenberg
	 */
	private class ReporterThread extends Thread {

		private Connection connection;
		private boolean done;

		/**
		 * ReporterThread constructor.
		 * 
		 * @param c
		 *            - the connection for this ReporterThread.
		 * 
		 * @author Daniel Eisenberg
		 */
		public ReporterThread(Connection c) {
			super();
			connection = c;
		}

		public void run() {
			while (true) {
				try {
					Packet p = connection.outputQueue.take();
					if (p.getType() == Type.OUTPUT_QUEUE_POISON_PILL)
						end();
					connection.oos.writeObject(p);
					connection.oos.flush();
					// if (p.getClass() != Packet.class)
					connection.oos.reset();
				} catch (InterruptedException e) {
					interrupt();
				} catch (IOException e) {
					e.printStackTrace();
					end();
				} finally {
					if (isInterrupted())
						interrupted();
				}
			}
		}

		/**
		 * Attempts to close this thread's stream and sets thread dereference
		 * flag.
		 */
		public void end() {
			done = true;
			try {
				connection.oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			threadFinished = true;
		}
	}

	/**
	 * EventThread takes Command objects from the eventQueue and executes them.
	 * 
	 * @author Daniel Eisenberg
	 * 
	 */
	private class EventThread extends Thread {

		/**
		 * Takes Commands from eventQueue and executes them.
		 */
		public void run() {
			while (!shuttingDown) {
				try {
					Command next = eventQueue.take();
					next.execute();
				} catch (InterruptedException e) {
					interrupt();
				} finally {
					if (isInterrupted())
						interrupted();
				}
			}
		}
	}

	/**
	 * Command object initiates server shutdown or restart.
	 * 
	 * @author Daniel Eisenberg
	 * 
	 */
	private class ShutdownCommand extends Command {
		private int minutes;
		private boolean restart;

		/**
		 * Creates a shutdown command waiting the specified time in minutes (not
		 * to exceed 15 minutes) before shutdown or restart.
		 * 
		 * @param mins
		 *            - minutes until shutdown.
		 * @param restart
		 *            - if true, the server will restart.
		 */
		public ShutdownCommand(int mins, boolean restart) {
			if (mins < 0)
				minutes = 1;
			else if (mins > 15)
				minutes = 15;
			else
				minutes = mins;
			this.restart = restart;
		}

		public void execute() {
			if (!shutdownCommand) {
				shutdownCommand = true;
				shutdownThread = new Thread(new ShutdownTimer(minutes, restart));
				shutdownThread.start();
			}
		}

	}

	/**
	 * Thread for handling server shutdowns and restarts.
	 * 
	 * @author Daniel Eisenberg
	 */
	private class ShutdownTimer extends Thread {

		private int minutes;
		private String event;
		public boolean cancel;

		/**
		 * Creates a new ShutdownTimer which waits for the specified amount of
		 * time. Server will always broadcast at least a 30 second warning, so
		 * every shutdown takes at least 30 seconds.
		 * 
		 * @param mins
		 *            - time in minutes until shutdown.
		 * @param restart
		 *            - if true, the server will restart.
		 */
		public ShutdownTimer(int mins, boolean restarting) {
			minutes = mins;
			event = (restarting) ? " restart " : " shutdown ";
			cancel = false;
			restart = restarting;
		}

		public void run() {
			// broadcast every minute
			for (int i = minutes; i > 0; i--)
				try {
					String message = "Server" + event + "in " + i + " minutes.";
					eventQueue.put(new Broadcast(new Packet(
							Type.SERVER_BROADCAST, message)));
					System.out.println(message);
					if (i > 1)
						sleep(60000L);
					else
						sleep(30000L);
				} catch (InterruptedException e) {
					if (cancel) {
						eventQueue.add(new Broadcast(new Packet(
								Type.SERVER_BROADCAST, "Shutdown aborted.")));
						System.out.println("Shutdown aborted.");
						shutdownCommand = false;
						return;
					}
				}
			// broadcast at 30s to shutdown
			try {
				String message = "Server" + event + "in " + "30 seconds.";
				eventQueue.put(new Broadcast(new Packet(Type.SERVER_BROADCAST,
						message)));
				System.out.println(message);
				sleep(30000L);
			} catch (InterruptedException e) {
				if (cancel) {
					eventQueue.add(new Broadcast(new Packet(
							Type.SERVER_BROADCAST, "Shutdown aborted.")));
					System.out.println("Shutdown aborted.");
					shutdownCommand = false;
					return;
				}
			}

			// shutdown server
			shuttingDown = true;
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * MobActionThread iterates through creatures and calls their action
	 * methods.
	 * 
	 * @author Daniel Eisenberg
	 * 
	 */
	class MobActionThread extends Thread {

		private long moveTimer;

		public MobActionThread() {
			super();
			moveTimer = System.currentTimeMillis();
		}

		public void run() {
			while (!shuttingDown) {
				boolean alsoMove = false;
				long curTime = System.currentTimeMillis();
				if (curTime - moveTimer > 60000)
					alsoMove = true;
				Zone zone = dungeonList.get(this);
				if (zone != null)
					for (Mob m : zone.listMobs())
						try {
							if (m.getRoom() == null)
								eventQueue.put(new RemoveMobCommand(m, zone));
							else if (m.ready())
								if (alsoMove) {
									eventQueue.put(new MobCommand(m, true));
									m.heal();
								} else
									eventQueue.put(new MobCommand(m, false));
						} catch (InterruptedException e) {
							interrupt();
						} finally {
							if (isInterrupted())
								interrupted();
						}
				else
					return;
			}
		}
	}

	/**
	 * CityActionThread mirrors MobActionThread outside dungeons.
	 */
	private class CityActionThread extends Thread {

		public void run() {
			while (!shuttingDown) {
				for (NPC n : world.getCity().listMobs())
					try {
						if (n.ready()) {
							eventQueue.put(new MobCommand(n, true));
						}
					} catch (InterruptedException e) {
						interrupt();
					} finally {
						if (isInterrupted())
							interrupted();
					}
			}
		}
	}

	/**
	 * Thread handles local server commands.
	 * 
	 * @author Daniel Eisenberg
	 * 
	 */
	private class ConsoleThread extends Thread {

		public void run() {
			Scanner kb = new Scanner(System.in);
			while (true) {
				String input = kb.nextLine().toUpperCase();

				// parse input to command and parameters
				int space = input.indexOf(" ");
				String command = (space == -1) ? input : input.substring(0,
						space);
				String argument = (space == -1) ? null : input
						.substring(space + 1);

				if (command.equalsIgnoreCase("SHUTDOWN")
						|| command.equalsIgnoreCase("RESTART")) {
					int next = 0;
					if (argument != null)
						try {
							next = Integer.parseInt(argument);
						} catch (NumberFormatException e1) {
							next = 0;
						}
					try {
						eventQueue.put(new ShutdownCommand(next, command
								.equalsIgnoreCase("RESTART")));
					} catch (InterruptedException e) {
						interrupt();
						System.out.println("Command may have failed.");
					}
				} else if (command.equalsIgnoreCase("MOD")
						|| command.equalsIgnoreCase("DEMOD")) {
					if (argument != null) {
						String who = Names.formatName(argument);
						connections.get(playerLookup.get(who)).user
								.setModerator(command.substring(0, 1)
										.equalsIgnoreCase("M"));
					}
				} else if (command.equalsIgnoreCase("BAN")
						|| command.equalsIgnoreCase("KICK")) {
					if (argument != null) {
						try {
							String who = Names.formatName(argument);
							if (moderate(Type.valueOf(command),
									playerLookup.get(who)))
								System.out
										.println(who + " has been moderated.");
						} catch (InterruptedException e) {
							interrupt();
							System.out.println("Command may have failed.");
						}
					}
				} else if (command.equalsIgnoreCase("IPBAN")) {
					if (argument != null) {
						String who = Names.formatName(argument);
						try {
							if (moderate(Type.BAN_IP, playerLookup.get(who)))
								System.out
										.println(who + " has been moderated.");
						} catch (InterruptedException e) {
							interrupt();
							System.out.println("Command may have failed.");
						}
					}
					if (isInterrupted())
						interrupted();
				} else if (command.equalsIgnoreCase("BANUSER")) {
					// bans a user by username rather than character name
					// this command may be used when user is not online
					if (argument != null) {
						String who = argument.toLowerCase();
						if (banList.add(who)) {
							System.out.println(who + " has been banned.");
							// ensure user is kicked if online
							if (usersOnline.containsKey(who)) {
								User u = usersOnline.get(who);
								for (Player p : connections.keySet())
									if (connections.get(p).user == u)
										try {
											moderate(Type.BAN, p);
										} catch (InterruptedException e) {
											interrupt();
											System.out
													.println("Command may have failed.");
										}
							}
						}
					}
				}
			}
		}
	}
}