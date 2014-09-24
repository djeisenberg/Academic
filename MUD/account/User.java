package account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.creatures.Job;
import model.creatures.Player;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int LIMIT = 6;
	private static final String PATH = "users";
	private List<Player> characters;
	private String username;
	private byte[] password;
	private boolean moderator;

	public User(String name) {
		username = name;
		characters = new ArrayList<Player>(LIMIT);
	}

	/**
	 * Returns this user's username.
	 * 
	 * @return this user's username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets this users password to the specified password.
	 * 
	 * @param pw
	 *            - the new password
	 */
	public void setPassword(byte[] pw) {
		password = new byte[pw.length];
		for (int i = 0; i < pw.length; i++)
			password[i] = pw[i];
	}

	/**
	 * Tests the stored password against the argument password.
	 * 
	 * @param pw
	 *            - the password to be checked
	 * @return true if and only if the passwords match
	 */
	public boolean checkPassword(byte[] pw) {
		if (pw.length != password.length)
			return false;
		for (int i = 0; i < pw.length; i++)
			if (pw[i] != password[i])
				return false;
		return true;
	}

	/**
	 * Attempts to write the character to file. If retry is false, save will
	 * backup the original file, replacing any previous backup; if retry is
	 * true, this step will be skipped.
	 * 
	 * @param retry
	 *            - indicates whether this call was just made (unsuccessfully).
	 * @throws IOException
	 */
	public void save(boolean retry) throws IOException {
		// backup file
		File initial = new File(PATH, username + ".dat");
		if (!initial.isFile() && !retry)
			initial.createNewFile();
		if (retry) { // only create backup once
			initial.delete();
		} else if (initial.isFile()) {
			File backup = new File(PATH, username + ".dat.bak");
			if (backup.exists())
				backup.delete(); // remove old backup file
			initial.renameTo(backup);
		}

		FileOutputStream fos = new FileOutputStream(initial);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		if (oos != null && fos != null) {
			oos.writeObject(this);
		}
		oos.close();
		fos.close();
	}

	/**
	 * Attempts to load the user with the specified username, will attempt to
	 * use backup file in case of errors.
	 * 
	 * @param name
	 *            - the username
	 * @return the User with name matching username
	 */
	public static User load(String name) {
		FileInputStream fis;
		ObjectInputStream ois;
		User result = null;
		try {
			fis = new FileInputStream(new File(PATH, name + ".dat"));
			ois = new ObjectInputStream(fis);
			if (ois != null && fis != null) {
				result = (User) ois.readObject();
				ois.close();
				fis.close();
			}
		} catch (FileNotFoundException fnfe) {
			try {
				fis = new FileInputStream(new File(PATH, name + ".dat.bak"));
				ois = new ObjectInputStream(fis);
				if (ois != null) {
					result = (User) ois.readObject();
					ois.close();
					fis.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Creates a new Player character registered to this user.
	 * 
	 * @param name
	 *            - the character's name
	 * @param job
	 *            - the character class of the new character
	 * @return the new Player object, or null if unsuccessful.
	 */
	public Player createNewCharacter(String name, Job job) {
		if (characters.size() == LIMIT)
			return null;
		Player result = Player.getNew(name, job, this);
		if (result != null)
			characters.add(result);
		return result;
	}

	/**
	 * Attempts to permanently delete the specified character.
	 * 
	 * @param toDelete
	 *            - the character to be deleted.
	 * @return true if the character was deleted, false if the character was not
	 *         present.
	 */
	public boolean deleteCharacter(Player toDelete) {
		return characters.remove(toDelete);
	}

	/**
	 * Returns a List of this user's characters.
	 * 
	 * @return a List of this user's characters.
	 */
	public List<Player> listCharacters() {
		return characters;
	}

	/**
	 * Sets moderator status to b.
	 * 
	 * @param b
	 *            - the value that isModerator will take.
	 */
	public void setModerator(boolean b) {
		moderator = b;
	}

	/**
	 * Check if this user is a moderator.
	 * 
	 * @return true if and only if this user has moderator status.
	 */
	public boolean isModerator() {
		return moderator;
	}
}
