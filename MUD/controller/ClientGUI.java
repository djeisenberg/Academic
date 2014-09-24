package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import model.items.Equipable;
import model.items.Item;
import model.items.Usable;
import network.LookPacket;
import network.Packet;
import network.SafePlayer;
import network.Type;

/**
 * GUI for the client. For now it will only have the Chat and System windows
 * 
 * TODO: inventory, use/equip/look buttons, party window, monster window,
 * player/monster graphics
 * 
 * @author james_carpenter
 * 
 */
public class ClientGUI extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private final int FRAME_X = 1200;
	private final int FRAME_Y = 800;
	private final int PORTRAIT_X = 100;
	private final int PORTRAIT_Y = 100;

	private final String ICONS_FOLDER = "icons";
	private final String PLAYER_SPRITES_FOLDER = "PlayerSprites";

	private ClientModel clientModel;

	private JPanel mainPanel, directionPanel, chatPanel, dungeonGraphicsPanel,
			playerInfoPanel;
	private JTextArea chatWindow, systemWindow, playerStats;
	private JTextField messageInputField;
	private JButton messageSendButton, useOrEquipButton, unequipButton, dropButton;
	private JScrollPane chatScrollPane, systemScrollPane;
	private JLabel dungeonGraphic, dungeonMap, playerImage;
	private JList playerInventoryList;
	private DefaultListModel playerInventoryModel;

	private boolean connected;
	private String portraitFilePath;

	/**
	 * Initializes GUI.
	 */
	public ClientGUI() {

		initializeFrame();
		setupChatPanel();
		setupDirectionPanel();
		setupDungeonGraphicsPanel();
		setupPlayerInfoPanel();

		mainPanel.setVisible(true);
		this.setVisible(true);

		clientModel = new ClientModel();
		clientModel.addObserver(this);

		connected = connect();
		
		try {
			clientModel.sendPacketToServer("meta_data");
		} catch (IOException e) {
			connected = false;
		}
	}

	/**
	 * Attempts to connect to server and handle login.
	 * 
	 * @return true if login was successful, false otherwise
	 */
	private boolean connect() {
		String ip = JOptionPane.showInputDialog(this, "Enter IP");
		int port;
		try {
			port = Integer.parseInt(JOptionPane.showInputDialog(this,
					"Enter Port"));
		} catch (NumberFormatException e) {
			return false;
		}
		clientModel.connectToServer(ip, port);

		while (!makeLoginPacket())
			;
		if (!finishLogin()) {
			clientModel.disconnectFromServer();
			return false;
		} else {
			clientModel.startInputThread();
			return true;
		}
	}

	private void setupDungeonGraphicsPanel() {
		dungeonGraphicsPanel = new JPanel();
		dungeonGraphicsPanel.setLayout(null);
		dungeonGraphicsPanel.setSize(600, 200);
		dungeonGraphicsPanel.setVisible(true);
		dungeonGraphicsPanel.setLocation(500, 300);

		dungeonGraphic = new JLabel(new ImageIcon("icons" + File.separator + "default.png")); // Default image to display
		dungeonGraphic.setSize(300, 200);
		dungeonGraphic.setLocation(0, 0);

		dungeonMap = new JLabel(new ImageIcon(ICONS_FOLDER + File.separator
				+ "background.png")); // Default image to display
		dungeonMap.setSize(300, 200);
		dungeonMap.setLocation(300, 0);

		dungeonGraphicsPanel.add(dungeonGraphic);
		dungeonGraphicsPanel.add(dungeonMap);
		mainPanel.add(dungeonGraphicsPanel);
	}

	private void updateDungeonGraphicsPanel(String filePath) {
		// TODO: call this after moving
		ImageIcon graphic = new ImageIcon(filePath);
		Image temp = graphic.getImage().getScaledInstance(300, 200,
				Image.SCALE_SMOOTH);
		graphic.setImage(temp);

		dungeonGraphic.setIcon(graphic);
		dungeonGraphicsPanel.validate();
		this.validate();
		this.repaint();
	}

	private void initializeFrame() {
		this.setLayout(null);
		this.setTitle("MUD_CLIENT");
		this.setSize(FRAME_X, FRAME_Y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setSize(FRAME_X, FRAME_Y);

		this.add(mainPanel);
	}

	private void setupPlayerInfoPanel() {
		playerInfoPanel = new JPanel();
		playerInfoPanel.setLayout(null);
		playerInfoPanel.setSize(500, 300);
		playerInfoPanel.setLocation(10, 300);

		playerStats = new JTextArea("Status Window");
		playerStats.setSize(200, 200);
		playerStats.setLocation(100, 0);
		playerStats.setLineWrap(true);
		playerStats.setWrapStyleWord(true);
		playerStats.setEditable(false);
		playerStats.setLineWrap(true);
		playerStats.setBorder(new LineBorder(new Color(200, 200, 200)));
		playerStats.setVisible(true);

		portraitFilePath = "icons" + File.separator + "default.png";

		playerInventoryModel = new DefaultListModel();
		playerInventoryList = new JList(playerInventoryModel);
		playerInventoryList
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerInventoryList.setSize(new Dimension(200, 200));
		playerInventoryList.setLocation(300, 0);
		playerInventoryList.setBorder(new LineBorder(new Color(200, 200, 200)));
		playerInventoryList.setVisible(true);

		useOrEquipButton = new JButton("Use/Equip Item");
		useOrEquipButton.setSize(200, 30);
		useOrEquipButton.setLocation(300, 200);
		useOrEquipButton.setVisible(true);
		useOrEquipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = playerInventoryList.getSelectedIndex();
				if (index == -1)
					return;
				if (playerInventoryList.getSelectedValue() == "EMPTY")
					return;
				else {
					if (playerInventoryList.getSelectedValue() instanceof Usable) {
						try {
							clientModel.sendPacketToServer("use "
									+ ((Item) (playerInventoryList
											.getSelectedValue())).getName());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else if (playerInventoryList.getSelectedValue() instanceof Equipable) {
						try {
							clientModel.sendPacketToServer("equip "
									+ ((Item) (playerInventoryList
											.getSelectedValue())).getName());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		unequipButton = new JButton("Unequip Item");
		unequipButton.setSize(200, 30);
		unequipButton.setLocation(100, 200);
		unequipButton.setVisible(true);
		unequipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = playerInventoryList.getSelectedIndex();
				if (index == -1)
					return; // if they did not select an item to use or equip,
							// do nothing
				if (playerInventoryList.getSelectedValue() == "EMPTY")
					return; // if they have no inventory then do nothing

				else {
					if (playerInventoryList.getSelectedValue() instanceof Equipable) {
						try {
							clientModel.sendPacketToServer("unequip "
									+ ((Item) (playerInventoryList
											.getSelectedValue())).getName());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		dropButton = new JButton("Drop item");
		dropButton.setSize(200, 30);
		dropButton.setLocation(100, 225);
		dropButton.setVisible(true);
		dropButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = playerInventoryList.getSelectedIndex();
				if (index == -1)
					return; 
				if (playerInventoryList.getSelectedValue() == "EMPTY")
					return;

				else {
					if (playerInventoryList.getSelectedValue() instanceof Equipable) {
						try {
							clientModel.sendPacketToServer("drop "
									+ ((Item) (playerInventoryList
											.getSelectedValue())).getName());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		//TODO: get item buttons
		
		ImageIcon playerPortrait = new ImageIcon(portraitFilePath);
		Image temp = playerPortrait.getImage().getScaledInstance(PORTRAIT_X,
				PORTRAIT_Y, Image.SCALE_SMOOTH);
		playerPortrait.setImage(temp);

		playerImage = new JLabel(playerPortrait);
		playerImage.setLocation(0, 0);
		playerImage.setSize(PORTRAIT_X, PORTRAIT_Y);
		playerImage.setVisible(true);

		playerInfoPanel.add(playerImage);
		playerInfoPanel.add(playerStats);
		playerInfoPanel.add(playerInventoryList);
		playerInfoPanel.add(useOrEquipButton);
		playerInfoPanel.add(unequipButton);
		playerInfoPanel.add(dropButton);
		playerInfoPanel.setVisible(true);
		mainPanel.add(playerInfoPanel);
	}

	private void updatePlayerInfoPanel(SafePlayer sp) {
		if (sp == null) {
			System.out.println("Null safeplayer was passed");
			return;
		}
	
		System.out.println("attempting to refresh playerInfoPanel");
		
		playerStats.setText(sp.getName() + " the " + sp.getJob() + "\n"
				+ "Level: " + "\t" + sp.getLevel() + "\n" + "HP: " + "\t"
				+ sp.getHP() + "\n" + "MP:" + "\t" + sp.getMP() + "\n"
				+ "Attack" + "\t" + sp.getAttack() + "\n" + "Defense" + "\t"
				+ sp.getDefense() + "\n" + "Defense" + "\t" + sp.getMagic()
				+ "\n" + "MDefense" + "\t" + sp.getMDefense() + "\n"
				+ "Strength" + "\t" + sp.getStrength() + "\n" + "Stamina"
				+ "\t" + sp.getStamina() + "\n" + "Agility" + "\t"
				+ sp.getAgility() + "\n" + "Evasion" + "\t" + sp.getEvasion());
		
		//TODO: not displaying buffs from equipment
		
		// move below elsewhere later
		String job = sp.getJob().toLowerCase();
		if (job.equals("berserker"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "berserker.png";
		else if (job.equals("black mage"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "black_mage.png";
		else if (job.equals("knight"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "knight.png";
		else if (job.equals("magic knight"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "magic_knight.png";
		else if (job.equals("rogue"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "rogue.png";
		else if (job.equals("white mage"))
			portraitFilePath = PLAYER_SPRITES_FOLDER + File.separator + "white_mage.png";

		ImageIcon playerPortrait = new ImageIcon(portraitFilePath);
		Image temp = playerPortrait.getImage().getScaledInstance(PORTRAIT_X,
				PORTRAIT_Y, Image.SCALE_SMOOTH);
		playerPortrait.setImage(temp);
		playerImage.setIcon(playerPortrait);
		// move above elsewhere later

		//update player's inventory
		playerInventoryModel.clear();
		for (model.items.Item i : sp.itemList()) {
			playerInventoryModel.addElement(i);
		}
		if (playerInventoryModel.isEmpty())
			playerInventoryModel.addElement("EMPTY");
		
		playerInventoryList.validate();
	}

	private void setupChatPanel() {

		chatPanel = new JPanel();
		chatPanel.setLayout(null);
		chatPanel.setSize(610, 200);

		chatWindow = new JTextArea();
		chatWindow.setLineWrap(true);
		chatWindow.setWrapStyleWord(true);
		chatWindow.setEditable(false);
		chatWindow.setLineWrap(true);
		chatWindow.setVisible(true);

		chatScrollPane = new JScrollPane(chatWindow,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setSize(300, 125);
		chatScrollPane.setLocation(0, 0);
		chatScrollPane.setVisible(true);

		messageInputField = new JTextField();
		messageInputField.setSize(610, 25);
		messageInputField.setLocation(0, 135);
		messageInputField.addActionListener(new SendMessageToServerListener());

		messageSendButton = new JButton("Send Message");
		messageSendButton.setLocation(205, 170);
		messageSendButton.setSize(200, 25);
		messageSendButton.addActionListener(new SendMessageToServerListener());

		systemWindow = new JTextArea();
		systemWindow.setLineWrap(true);
		systemWindow.setWrapStyleWord(true);
		systemWindow.setEditable(false);
		systemWindow.setLineWrap(true);
		systemWindow.setVisible(true);

		systemScrollPane = new JScrollPane(systemWindow,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		systemScrollPane.setSize(300, 125);
		systemScrollPane.setLocation(310, 5);

		systemScrollPane = new JScrollPane(systemWindow);
		systemScrollPane.setSize(300, 125);
		systemScrollPane.setLocation(310, 0);
		systemScrollPane.setVisible(true);

		chatPanel.add(chatScrollPane);
		chatPanel.add(systemScrollPane);
		chatPanel.add(messageInputField);
		chatPanel.add(messageSendButton);

		chatPanel.setVisible(true);
		chatPanel.setLocation(10, 10);
		mainPanel.add(chatPanel);
	}

	private void setupDirectionPanel() {
		directionPanel = new JPanel(new GridLayout(3, 3));

		JButton downButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "downStairsIcon.png"));
		downButton.addActionListener(new ButtonListener("down"));

		JButton northButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "northIcon.png"));
		northButton.addActionListener(new ButtonListener("north"));

		JButton upButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "upStairsIcon.png"));
		upButton.addActionListener(new ButtonListener("up"));

		JButton westButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "westIcon.png"));
		westButton.addActionListener(new ButtonListener("west"));

		JButton eastButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "eastIcon.png"));
		eastButton.addActionListener(new ButtonListener("east"));

		JButton southButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "southIcon.png"));
		southButton.addActionListener(new ButtonListener("south"));

		JButton lookButton = new JButton(new ImageIcon(ICONS_FOLDER
				+ File.separator + "lookIcon.png"));
		lookButton.addActionListener(new ButtonListener("look"));

		directionPanel.add(downButton);
		directionPanel.add(northButton);
		directionPanel.add(upButton);
		directionPanel.add(westButton);
		directionPanel.add(southButton);
		directionPanel.add(eastButton);
		directionPanel.add(lookButton);

		directionPanel.setSize(200, 150);
		directionPanel.setVisible(true);
		directionPanel.setLocation(650, 10);

		mainPanel.add(directionPanel);
	}

	/**
	 * Creates a new login packet to send to the server.
	 * 
	 * @return true if account login succeeded.
	 */
	private boolean makeLoginPacket() {
		JPanel pw = new JPanel();
		pw.setLayout(new GridLayout(2, 2));
		pw.add(new JLabel("Username: "));
		JTextField nameField = new JTextField(10);
		pw.add(nameField);
		pw.add(new JLabel("Password: "));
		JPasswordField pwField = new JPasswordField(10);
		pw.add(pwField);

		String[] opt = { "Login", "New account" };
		// new account
		if (JOptionPane.showOptionDialog(this, pw,
				"Enter username and password", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, opt, opt[0]) == 1) {
			JPasswordField pwf2 = new JPasswordField(10);
			JOptionPane.showMessageDialog(this, pwf2, "Confirm password",
					JOptionPane.PLAIN_MESSAGE);
			if (Arrays.equals(pwField.getPassword(), pwf2.getPassword())) {
				boolean success = clientModel.logIn(true, nameField.getText(),
						pwField.getPassword());
				return success;
			} else {
				JOptionPane.showMessageDialog(this,
						"The passwords entered do not match.",
						"Confirm password", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else { // login (existing account)
			boolean success = clientModel.logIn(false, nameField.getText(),
					pwField.getPassword());
			return success;
		}
	}

	/**
	 * Helper method routes character creation, selection, and deletion
	 * requests.
	 * 
	 * @return true if a character is successfully loaded on the server.
	 */
	private boolean finishLogin() {
		network.Packet packet = clientModel.nextLoginPacket();
		if (packet.getType() != Type.CHARACTER_SELECT)
			packetPopup(packet);
		if (packet.getType() == Type.CHARACTER_SELECT)
			return selectCharacter(packet);
		else if (packet.getType() == Type.SERVER_MESSAGE) {
			while (!createCharacter())
				;
			return true;
		}
		return false;
	}

	/**
	 * Facilitate character selection from existing characters. Also handles
	 * character deletion requests.
	 * 
	 * @param packet
	 *            - A packet originating from the server consisting of a list of
	 *            existing characters for this account.
	 * @return true if a character has been selected.
	 */
	private boolean selectCharacter(Packet packet) {
		String[] characters = Packet.getArray(packet);
		String[] options = { "Select character", "Delete character",
				"Create character" };
		JComboBox box = new JComboBox(characters);
		int command = JOptionPane.showOptionDialog(this, box,
				"Select character", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		String choice = box.getSelectedItem().toString();
		if (command == 1) // delete
			if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete " + choice + "?"))
				return selectCharacter(packet); // canceled delete
			else {
				clientModel.sendAccountPacket(new Packet(Type.DELETE_CHARACTER,
						choice)); // confirmed delete
				Packet p = clientModel.nextLoginPacket();
				packetPopup(p);
				return finishLogin();
			}
		else if (command == 0) { // selected character
			clientModel.sendAccountPacket(new Packet(Type.CHARACTER_SELECT,
					choice));
			Packet p = clientModel.nextLoginPacket();
			packetPopup(p);
			return p.getType() != Type.ERROR;
		} else
			// create new character
			return createCharacter();
	}

	/**
	 * Facilitates creation of a new character.
	 * 
	 * @return true if a character was successfully created.
	 */
	private boolean createCharacter() {
		model.creatures.Job[] jobs = model.creatures.Job.values();
		JComboBox box = new JComboBox(jobs);
		String name = JOptionPane.showInputDialog(this, "Name Character");
		if (name == null)
			return false;
		JOptionPane.showMessageDialog(this, box, "Choose job",
				JOptionPane.PLAIN_MESSAGE);
		clientModel.sendAccountPacket(new Packet(Type.CREATE_CHARACTER, name
				+ " " + box.getSelectedItem()));
		Packet packet = clientModel.nextLoginPacket();
		packetPopup(packet);
		if (packet.getType() == Type.SERVER_MESSAGE)
			return true;
		return finishLogin();
	}

	/**
	 * Creates a pop-up frame based on a Packet.
	 * 
	 * @param p
	 *            - a Packet.
	 */
	public void packetPopup(Packet p) {
		boolean error = p.getType() == Type.ERROR;
		JOptionPane.showMessageDialog(this, p.getMessage(), error ? "Error"
				: "Login information", error ? JOptionPane.ERROR_MESSAGE
				: JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Creates a pop-up frame based on a Packet. This is a static alternative to
	 * packetPopup.
	 * 
	 * @param p
	 *            - a Packet.
	 */
	public static void packetDialog(Packet p) {
		boolean error = p.getType() == Type.ERROR;
		JOptionPane.showMessageDialog(null, p.getMessage(), error ? "Error"
				: "Login information", error ? JOptionPane.ERROR_MESSAGE
				: JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * ActionListener sends text from input field to server.
	 * 
	 */
	private class SendMessageToServerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String input = messageInputField.getText().trim();
				if (input.equalsIgnoreCase("help")
						|| input.equalsIgnoreCase("commands")) {
					messageInputField.setText(null);
					help(); // display list of commands
				} else if (connected == false
						&& (input.equalsIgnoreCase("connect") || input
								.equalsIgnoreCase("login"))) {
					messageInputField.setText(null);
					connected = connect(); // attempt to connect
				} else
					try {
						if (clientModel.sendPacketToServer(input)) {
							messageInputField.setText(null);

							if (input.equalsIgnoreCase("logout")) {
								connected = false;
								clientModel.disconnectFromServer(); // disconnect
							} else if (input.equalsIgnoreCase("quit")) {
								clientModel.disconnectFromServer(); // disconnect
								System.exit(0); // and exit program
							}

							return;
						} else
							messageInputField
									.setText("ERROR: unknown command entered. Type \"help\" for a list of commands.");
						messageInputField.selectAll();
					} catch (IOException ioe) {
						// else notify user of the failure to send packet
						packetPopup(new Packet(Type.ERROR,
								"ERROR: please check connection."));
					}
			} catch (NullPointerException npe) {
				// no action required when user clicks send and the text input
				// field is empty
			}
		}
	}

	/**
	 * Listener for the direction arrow buttons; creates packets using the same
	 * mechanism
	 */
	private class ButtonListener implements ActionListener {
		private String message;

		public ButtonListener(String message) {
			this.message = message;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				clientModel.sendPacketToServer(message);
			} catch (IOException e1) {
				packetPopup(new Packet(Type.ERROR,
						"ERROR: please check connection."));
			}
		}

	}

	/**
	 * When a packet is received, write the message to the appropriate window.
	 * 
	 * @param o
	 *            the clientModel
	 * @param arg
	 *            type network.Packet, received from the server
	 */
	@Override
	public void update(Observable o, Object arg) {
		network.Packet packet = (network.Packet) arg;
		String msg = packet.getMessage() + "\n";

		// TODO: get updated images when the player moves

		switch (packet.getType()) {
		case OOC:
		case PARTY:
		case SAY:
		case SOCIAL:
		case TELL:
			chatWindow.append(msg);
			chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
			break;
		case META_DATA:
			if (packet instanceof network.SafePlayerPacket) {
				updatePlayerInfoPanel(((network.SafePlayerPacket) packet).getSafePlayer());
			}
			// else; // TODO: for monsters
			break;

		 case LOOK:
			 systemWindow.append(msg);
			 systemWindow.setCaretPosition(systemWindow.getDocument().getLength());
			 if (packet instanceof LookPacket) {
				 System.out.println("is it a Lookpacket?" + (packet instanceof LookPacket));
				 System.out.println("Update dungeon graphic to be called");
				 String filepath = (((LookPacket)packet)).getFilePath(); //this is null! Why?!
				 System.out.println("we have the filepath: " + filepath);
				 if (filepath != null) {
					 updateDungeonGraphicsPanel(filepath);			 
				 	System.out.println("Update dungeon graphic just got called");
				 }
			 }
			 break;
		default:
			systemWindow.append(msg);
			systemWindow.setCaretPosition(systemWindow.getDocument()
					.getLength());
			break;
		}
	}

	/**
	 * Creates a frame containing a list and description of commands.
	 */
	public void help() {
		JFrame helpFrame = new JFrame("Help");

		JTextArea helpText = new JTextArea(// @formatter:off
				"help / commands \t"
						+ " -   Display this list of commands\n"

						+ "say [message] / \"[message]\" \t"
						+ " -   Send a chat message to all players in current room\n"

						+ "ooc [message] \t\t"
						+ " -   Send a chat message to all players currently online\n"

						+ "(t)ell [player] [message] \t"
						+ " -   Send a private chat message to [player], if online\n"

						+ "emote [message] \t"
						+ " -   Send an emote to all players in current room.\n"
						+ "\t\t"
						+ "       For example, if Bob types \"emote laughs.\", \n"
						+ "\t\t"
						+ "       the output would be \"Bob laughs.\"\n"

						+ "(n)orth, (s)outh, (e)ast, \n"
						+ "   (w)est, (u)p, (d)own \t"
						+ " -   Move in the specified direction\n"

						+ "enter [dungeon] \t"
						+ " -   Zone in to [dungeon]\n"

						+ "home \t\t"
						+ " -   Teleport to the world's spawn location\n"

						+ "(l)ook \t\t"
						+ " -   Display information about current room\n"

						+ "status / score \t\t"
						+ " -   Get information about this character\n"

						+ "who [player] \t\t"
						+ " -   Get information about [player], if online\n"

						+ "(i)nventory \t\t"
						+ " -   Display current inventory\n"

						+ "quit / logout \t\t"
						+ " -   Disconnect from server\n"

						+ "connect / login \t\t"
						+ " -   Connect to server if not already connected\n"

						+ "attack [creature] \t"
						+ " -   Attack [creature]\n"

						+ "cast [ability] [creature] \t"
						+ " -   Use [ability] on [creature]\n"

						+ "abilities \t\t"
						+ " -   Get list of abilities known\n"

						+ "get [item] \t\t"
						+ " -   Pick up [item], if present in current room\n"

						+ "give [item] [player] \t"
						+ " -   Give [item] to [player]. You must have the item\n"
						+ "\t\t"
						+ "       in your inventory and the recipient must be in\n"
						+ "\t\t"
						+ "       the same room\n"

						+ "use [item] \t\t"
						+ " -   Use [item] if that item has a use effect\n"

						+ "equip [item] \t\t"
						+ " -   Equip [item], previous equipment is unequipped\n"

						+ "unequip [item] \t\t"
						+ " -   Unequip [item] if currently equipped\n"

						+ "\nParty Commands:\n"

						+ "(p)arty [message] \t"
						+ " -   Send a chat message to all party members\n"

						+ "(inv)ite [player] \t\t"
						+ " -   Invite [player] to join your group. Only available\n"
						+ "\t\t"
						+ "       to party leader\n"

						+ "uninvte [player] \t\t"
						+ " -   Remove [player] from the party. Only available to\n"
						+ "\t\t"
						+ "       party leader\n"

						+ "leader [player] \t\t"
						+ " -   Make [player] the leader of current party. Only\n"
						+ "\t\t"
						+ "       available to party leader\n"

						+ "join [player] \t\t"
						+ " -   Accept a pending party invitation from [player]\n"

						+ "leave \t\t"
						+ " -   Leave current party\n"

						+ "\nModeration Commands (require moderator):\n"

						+ "kick [player] \t\t"
						+ " -   Remove [player] from the server\n"

						+ "ban [player] \t\t"
						+ " -   Kick [player] and prevent that user account from\n"
						+ "\t\t"
						+ "       logging in\n"

						+ "banbyip [player] \t\t"
						+ " -   Ban [player] and prevent the IP address of that\n"
						+ "\t\t"
						+ "       user from logging in again\n"

						+ "shutdown [minutes] \t\t"
						+ " -   Initiate server shutdown. Server will shutdown\n"
						+ "\t\t"
						+ "       after the specified time has elapsed\n"

						+ "restart [minutes] \t\t"
						+ " -   Server shutdown command with restart");
		// @formatter:on
		JScrollPane helpScroll = new JScrollPane(helpText,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		helpScroll.setPreferredSize(new Dimension(640, 480));
		helpFrame.add(helpScroll);
		helpText.setPreferredSize(new Dimension(640, 480));
		helpText.setEditable(false);
		helpFrame.setSize(new Dimension(640, 480));
		helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		helpFrame.setVisible(true);
	}
}
