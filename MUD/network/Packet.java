package network;

import java.io.Serializable;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Defines a network Packet for sending commands and receiving responses.
 * 
 * @author Daniel Eisenberg
 */
public class Packet implements Serializable {

	private static final long serialVersionUID = 1L;
	private Type type;
	private String message;

	public static final Packet INVALID_COMMAND = new Packet(Type.ERROR,
			"Invalid command.");
	public static final Packet NO_PERMISSION = new Packet(Type.ERROR,
			"You don't have permission for that.");
	public static final Packet SHUTDOWN_IN_PROGRESS = new Packet(
			Type.SERVER_MESSAGE,
			"Shutdown or restart command already in progress.");
	public static final Packet LOGIN_SUCCESSFUL = new Packet(
			Type.LOGIN_SUCCESS, "Login successful.");
	public static final Packet LOGIN_FAILED = new Packet(Type.ERROR,
			"Login failed.");
	public static final Packet LOGOUT_MESSAGE = new Packet(Type.SERVER_MESSAGE,
			"Now logging out.");
	public static final Packet POISON = new Packet(
			Type.OUTPUT_QUEUE_POISON_PILL, "");
	public static final Packet NOT_LEADER = new Packet(Type.ERROR,
			"You are not the party leader.");

	/**
	 * Constructs a packet with the specified type and message.
	 * 
	 * @param type
	 *            - the message type.
	 * @param message
	 *            - the message.
	 * 
	 */
	public Packet(Type type, String message) {
		this.type = type;
		this.message = message;
	}

	private Packet(Packet p) {
		this.type = p.getType();
		this.message = p.getMessage();
	}

	/**
	 * @return the Type associated with this Packet.
	 * 
	 * @see Type
	 * 
	 * @author Daniel Eisenberg
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the message for this Packet.
	 * 
	 * @author Daniel Eisenberg
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns a new Packet object with fields matching those of the argument
	 * packet. Used primarily to construct serializable versions of pre-defined
	 * static packets.
	 * 
	 * @param p
	 *            - the Packet to copy.
	 * @return a new Packet with the values of p.
	 */
	public static Packet valueOf(Packet p) {
		return new Packet(p);
	}

	/**
	 * Parses a Packet's message into a whitespace-delimited String[].
	 * 
	 * @param packet
	 *            - the Packet to parse.
	 * @return a String[] of the tokens in packet.
	 */
	public static String[] getArray(Packet packet) {
		int len = new StringTokenizer(packet.getMessage()).countTokens();
		Scanner msgs = new Scanner(packet.getMessage());
		String[] result = new String[len];
		for (int i = 0; i < len; i++)
			result[i] = msgs.next();
		return result;
	}

}
