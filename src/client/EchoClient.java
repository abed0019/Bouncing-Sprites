package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author 
 * Modified by Zobayed Abedin
 */
public class EchoClient {

	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverName;
	public static final String DEFAULT_SERVER_NAME = "localhost";
	private int portNum;
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		switch (args.length) {
		case 2:
			(new EchoClient(args[0], Integer.parseInt(args[1]))).runClient();
			break;
		case 1:
			(new EchoClient(DEFAULT_SERVER_NAME, Integer.parseInt(args[0]))).runClient();
			break;
		default:
			(new EchoClient(DEFAULT_SERVER_NAME, server.EchoServer.DEFAULT_PORT)).runClient();
		}
	}

	public EchoClient(String serverName, int portNum) {
		this.serverName = serverName;
		this.portNum = portNum;
	}

	public void runClient() {
		try {
			connection = new Socket(InetAddress.getByName(serverName), portNum);
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			System.out.println("To Quit, enter EOF (^Z on Windows; ^D on Linux/Mac)");
			do {
				System.out.print("Input> ");
				message = keyboard.readLine();
				if (message != null){
					output.writeObject(message);
					output.flush();
					message = (String) input.readObject();
					System.out.println(message);
				}
			} while (message != null);
			input.close();
			output.close();
			connection.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}
}
