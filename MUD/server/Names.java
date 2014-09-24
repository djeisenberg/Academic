package server;

/**
 * Class provides static support methods related to character names to server
 * package.
 * 
 * @author Daniel Eisenberg
 * 
 */
public class Names {

	/**
	 * Formats a character name so first letter is upper case and rest is lower
	 * case.
	 */
	public static String formatName(String name) {
		if (name.equals(""))
			return name;
		return name.substring(0, 1).toUpperCase()
				+ name.substring(1).toLowerCase();
	}

	/**
	 * Checks a candidate name contains only alphabet characters.
	 * 
	 * @return true if the name only has alphabet characters.
	 */
	public static boolean validateName(String name) {
		if (name.equals("") || name.charAt(0) < 'A' || name.charAt(0) > 'Z')
			return false;
		for (int i = 1; i < name.length(); i++)
			if (name.charAt(i) < 'a' || name.charAt(i) > 'z')
				return false;
		return true;
	}
}
