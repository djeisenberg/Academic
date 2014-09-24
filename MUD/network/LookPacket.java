package network;

import java.io.File;

public class LookPacket extends Packet {
	private static final long serialVersionUID = 1L;
	private String filepath;
	
	public LookPacket(Type type, String message, String pathOfFile) {
		super(type, message);
		this.filepath = pathOfFile;
		
		if (this.filepath == null) //this should never happen
			this.filepath = "icons" + File.separator + "background.png";
		
	}

	public String getFilePath() {
		return filepath;
	}

}
