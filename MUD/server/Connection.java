package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import server.Server.MobActionThread;

import model.creatures.Player;
import network.Packet;
import account.User;

/**
 * Package class provides a container for all information
 * regarding each client connection.
 * 
 * @author Daniel Eisenberg
 */
class Connection {
	User user;
	Player player;
	Socket socket;
	BlockingQueue<Packet> outputQueue;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	List<Player> pendingInvites;
	MobActionThread dungeonKey;

	/**
	 * 
	 * 
	 * @param s
	 *            - The Socket for this Connection.
	 * @throws IOException
	 * 
	 */
	Connection(Socket s) throws IOException {
		socket = s;
		outputQueue = new LinkedBlockingQueue<Packet>();
		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());
		pendingInvites = new CopyOnWriteArrayList<Player>();
	}
}