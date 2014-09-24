package network;

import java.io.Serializable;

/**
 * LoginPacket handles client login and new account creation.
 * 
 * @author Daniel Eisenberg
 */
public class LoginPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	private Type type;
	private String user;
	private byte[] password;

	/**
	 * 
	 * 
	 * @param newAccount
	 *            - a boolean representing whether this is a new account request
	 *            (true) or a login request (false)
	 * @param username
	 *            - the username to login under, or the desired username for a
	 *            new user
	 * @param password
	 *            - the user password
	 */
	public LoginPacket(boolean newAccount, String username, byte[] password) {
		type = (newAccount) ? Type.NEW_ACCOUNT : Type.LOGIN;
		user = username.toLowerCase();
		this.password = password;
	}

	public Type getType() {
		return type;
	}

	public String getUsername() {
		return user;
	}

	public byte[] getPassword() {
		return password;
	}

}
