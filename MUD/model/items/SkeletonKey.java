package model.items;

/**
 * Singleton key item allows passage through locked doors.
 * 
 * @author Daniel Eisenberg
 *
 */
public class SkeletonKey extends Item {
	private static SkeletonKey key;
	
	private SkeletonKey() {
		key = new SkeletonKey();
		key.setName("Skeleton Key");
	}
	
	public static SkeletonKey makeKey() {
		if (key == null)
			new SkeletonKey();
		return key;
	}
}
