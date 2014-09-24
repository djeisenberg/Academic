package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import network.Packet;
import network.Type;

/**
 * Class to handle input/output between the server and client
 * 
 * @author james_carpenter
 * 
 */
public class ClientModel extends Observable {

	private Socket socket;
	private ObjectOutputStream clientoos;
	private ObjectInputStream clientois;
	private Thread inputThread;

	// private model.creatures.SafePlayer safePlayer;

	/**
	 * Thread to handle input from the Server. Attempt to read a packet from the
	 * object input stream and gives the packet to the GUI for processing
	 * 
	 * @author james_carpenter
	 */
	private class InputThread implements Runnable {
		network.Packet incomingPacket;

		@Override
		public void run() {
			while (true) {
				try {
					incomingPacket = (network.Packet) clientois.readObject();
				} catch (IOException e) {
					disconnectFromServer();
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					break;
				}
				forwardPacketToObservers(incomingPacket);
			}
		}
	}

	/**
	 * Method to give the packet to the GUI
	 * 
	 * @param packet
	 */
	private void forwardPacketToObservers(network.Packet packet) {
		this.setChanged();
		this.notifyObservers(packet);
	}

	/**
	 * Establish a connection to the server and creates the object input and
	 * output streams
	 * 
	 * @param ip
	 *            ip address to connect to
	 * @param port
	 *            port # to connect to
	 */
	public void connectToServer(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			clientoos = new ObjectOutputStream(socket.getOutputStream());
			clientois = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Safely close the connection to the server
	 */
	public void disconnectFromServer() {
		try {
			socket.close();
		} catch (IOException e) {
			// ignore
		}
		if (inputThread != null)
			inputThread.interrupt();
	}

	/**
	 * Attempt to log in to the server. Creates a network.LoginPacket and sends
	 * it to the server.
	 * 
	 * @param isNewAccount
	 * @param userName
	 * @param password
	 */
	public boolean logIn(boolean isNewAccount, String userName, char[] password) {
		byte[] hashed = hash(userName.toLowerCase().toCharArray(), password);
		network.LoginPacket loginPacket = new network.LoginPacket(isNewAccount,
				userName, hashed);
		try {
			clientoos.writeObject(loginPacket);
			clientoos.flush(); // ensures packet is sent
			network.Packet response = (Packet) clientois.readObject();
			ClientGUI.packetDialog(response);
			if (response.getType() != network.Type.LOGIN_SUCCESS)
				return false;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Packet nextLoginPacket() {
		try {
			return (Packet) clientois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendAccountPacket(Packet packet) {
		try {
			clientoos.writeObject(packet);
			clientoos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startInputThread() {
		inputThread = new Thread(new InputThread());
		inputThread.start();
	}

	/**
	 * Hashes a char[] password to a byte[].
	 * 
	 * @param password
	 *            - the password to be encrypted.
	 * @return a byte[] encrypted from password.
	 */
	private byte[] hash(char[] username, char[] password) {
		try {
			java.security.MessageDigest md5 = java.security.MessageDigest
					.getInstance("MD5");
			md5.reset();
			md5.update(charToByte(username)); // salt by username
			byte[] plain = charToByte(password);
			byte[] result = md5.digest(plain);
			// set plain to 0
			for (int i = 0; i < plain.length; i++)
				plain[i] = 0;
			return result;
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Converts a char[] to a byte[] array of twice the length. Also sets the
	 * argument char[] to all zeros.
	 * 
	 * @param password
	 *            - the char[]
	 * @return a byte[] with the byte-by-byte value of password.
	 */
	private byte[] charToByte(char[] password) {
		byte[] result = new byte[password.length << 1];
		for (int i = 0; i < password.length; i++) {
			// get upper byte
			result[i << 1] = (byte) ((password[i] & '\uFF00') >> 8);
			// get lower byte
			result[(i << 1) + 1] = (byte) (password[i] & '\u00FF');
			// set password to 0
			password[i] = 0;
		}
		return result;
	}

	/**
	 * Creates a packet and sends it to the server
	 * 
	 * @param rawMessage
	 *            String message for the packet. The String also has the type of
	 *            command at the beginning, which needs to be parsed out.
	 * @return True if message is successfully sent, false otherwise.
	 * @throws IOException
	 */
	public boolean sendPacketToServer(String rawMessage) throws IOException {

		network.Packet outgoingPacket = makePacket(rawMessage); // is null if
																// command type
																// is unknown

		if (outgoingPacket != null && clientoos != null) {
			clientoos.writeObject(outgoingPacket);
			clientoos.flush();
			return true;
		}

		return false;
	}

	/**
	 * Parse the raw message from the input and create packets of appropriate
	 * type
	 * 
	 * Might move this into the ClientGUI during future refactoring. And would
	 * probably be easier to use a Scanner too.
	 * 
	 * Does not handle shutdown packets yet
	 * 
	 * @param commandType
	 *            unformatted String from the clientGUI input field
	 * @return a Packet, or null if command was not recognized
	 */
	private network.Packet makePacket(String rawMessage) {
		String message;
		String commandType;
		network.Type type = null;

		// TODO: define for rest of packets

		if (rawMessage == null || rawMessage.equals(""))
			return null;

		int space = rawMessage.indexOf(" ");
		if (rawMessage.charAt(0) == '"') {
			if (rawMessage.lastIndexOf('"') == rawMessage.length() - 1) {
				// special case of say command nested in quotes
				return new network.Packet(network.Type.SAY,
						rawMessage.substring(1, rawMessage.length() - 1));
			} else {
				return null;
			}
		} else if (space != -1) { // not a unary command
			commandType = rawMessage.substring(0, space).toLowerCase();
			message = rawMessage.substring(space + 1);
		} else { // unary command
			commandType = rawMessage.toLowerCase();
			message = commandType;
		}

		try {
			type = Type.valueOf(commandType.toUpperCase());
		} catch (IllegalArgumentException iae) {
			// check command types that aren't Type names
			if (commandType.equals("up") || commandType.equals("down")
					|| commandType.equals("east") || commandType.equals("west")
					|| commandType.equals("north")
					|| commandType.equals("south") || commandType.equals("u")
					|| commandType.equals("d") || commandType.equals("e")
					|| commandType.equals("w") || commandType.equals("n")
					|| commandType.equals("s"))
				type = network.Type.MOVE;
			else if (commandType.equals("l"))
				type = network.Type.LOOK;
			else if (commandType.equals("t"))
				type = network.Type.TELL;
			else if (commandType.equals("quit"))
				type = network.Type.LOGOUT;
			else if (commandType.equals("emote"))
				type = network.Type.SOCIAL;
			else if (commandType.equals("p"))
				type = network.Type.PARTY;
			else if (commandType.equals("score"))
				type = network.Type.STATUS;
			else if (commandType.equals("i"))
				type = network.Type.INVENTORY;
			else if (commandType.equals("banbyip")
					|| commandType.equals("ipban"))
				type = network.Type.BAN_IP;
			else if (commandType.equals("inv"))
				type = network.Type.INVITE;
			else if (commandType.equals("leader"))
				type = network.Type.MAKE_LEADER;
			else if (commandType.equals("leave"))
				type = network.Type.LEAVE_PARTY;
			else if (commandType.equals("meta_data"))
				type = network.Type.META_DATA;

			else
				return null;
		}

		return new network.Packet(type, message);

	}
	/*
	 * public void setSafePlayer(model.creatures.SafePlayer s) { this.safePlayer
	 * = s; }
	 * 
	 * public SafePlayer getSafePlayer() { return safePlayer; }
	 */
}
