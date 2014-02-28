package ss.week7.chatbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import ss.week7.cmdline.Peer;

/**
 * P2 prac wk4. <br>
 * Client.
 * 
 * @author Theo Ruys
 * @version 2005.02.21
 */
public class Client extends Thread {

	private static final String USAGE = "Usage: name server-IP port";
	private String clientName;
	private MessageUI mui;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;

	/**
	 * Constructs a Client-object and tries to make a socket connection
	 */
	public Client(String name, InetAddress host, int port, MessageUI muiArg)
			throws IOException {
		clientName = name;
		sock = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		mui = muiArg;
	}

	/**
	 * Reads the messages in the socket connection. Each message will be
	 * forwarded to the MessageUI
	 */
	public void run() {
		Scanner input = new Scanner(System.in);
		while (true) {
			String message = input.nextLine();
			if (message.equals(".")) {
				break;
			}
			sendMessage(message);
		}
	}

	/** send a message to a ClientHandler. */
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/** close the socket connection. */
	public void shutdown() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/** returns the client name */
	public String getClientName() {
		return clientName;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println(USAGE);
			System.exit(0);
		}
		String name = args[0];
		InetAddress addr = null;
		int port = 0;
		Socket sock = null;
		// check args[1] - the IP-adress
		try {
			addr = InetAddress.getByName(args[1]);
		} catch (UnknownHostException e) {
			System.out.println(USAGE);
			System.out.println("ERROR: host " + args[1] + "	unknown");
			System.exit(0);
		}
		// parse args[2] - the port
		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			System.out.println(USAGE);
			System.out.println("ERROR: port	" + args[2] + "	is not an integer");
			System.exit(0);
		}
		// try to open a Socket to the server
		try {
			sock = new Socket(addr, port);
		} catch (IOException e) {
			System.out.println("ERROR: could not create	a socket on " + addr
					+ " and port " + port);
		}
		// create Peer object and start the two-way communication
		try {
			Peer client = new Peer(name, sock);
			Thread streamInputHandler = new Thread(client);
			streamInputHandler.start();
			System.out.println("You can write a message! close this program by entering \".\" and pressing Enter.\n");
			client.handleTerminalInput();
			System.out.println("Shutting down program...");
			client.shutDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} // end of class Client
