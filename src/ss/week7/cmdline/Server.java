
package ss.week7.cmdline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ss.week7.chatbox.MessageUI;

/**
 * Server. 
 * @author  Theo Ruys
 * @version 2005.02.21
 */
public class Server {
	private static final String USAGE = "usage: java week4.cmdline.Server <name> <port>";
	private String serverName;
	private MessageUI mui;
	private ServerSocket server;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	
	
	public Server(String name, InetAddress host, int port, MessageUI muiArg) {
		serverName = name;
		mui = muiArg;
		try {
			server = new ServerSocket(port, 5, host);
			sock = server.accept();
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Start een Server-applicatie op. */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(USAGE);
			System.exit(0);
		}

		String name = args[0];
		InetAddress addr = null;
		int port = 0;
		ServerSocket server = null;
		Socket sock = null;

		// parse args[1] - the port
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			System.out.println("ERROR: port " + args[2] + " is not an integer");
			System.exit(0);
		}

		// try to open a Socket to the server
		try {
			server = new ServerSocket(port);
			sock = server.accept();
		} catch (IOException e) {
			System.out.println("ERROR: could not create a serversocket on port " + port);
		}

		// create Peer object and start the two-way communication
		try {
			Peer client = new Peer(name, sock);
			Thread streamInputHandler = new Thread(client);
			streamInputHandler.start();
			client.handleTerminalInput();
			client.shutDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

} // end of class Server
