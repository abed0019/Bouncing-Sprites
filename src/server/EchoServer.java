package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Todd
 * Modified by Zobayed Abedin
 */
public class EchoServer {
	
	private int portNum;			// Port Number

	public static final int DEFAULT_PORT = 8080; // Default port is set to 8080

	private static final Runnable ClientHandler = null;
	
	public int messageNum;			// Number of Messages


	/**
	 * Constructor
	 */
	
	
	public EchoServer(int portNum) {
		
		this.portNum = portNum;
		
	} 
	
	
	/**
	 * This void method sets up the connection so the clients can connect to the server. This method uses multithreading to allow multiple connections.
	 */
	
	public void runServer() {
		
		System.out.println("EchoServer has now started!");
		
		try {
			
			ServerSocket server = new ServerSocket(portNum);		//new server socket object
			
			while (true) { // server is always running because of this infinite loop
				
				new Thread(ClientHandler).start();
				
			}
			
		} catch(IOException ex) {
			
			System.err.println("Error!!");
			
			ex.printStackTrace();
		}
	} // end of method runServer
	

	
	public static void main(String[] args) {
		
		if (args.length > 0) {
			
			(new EchoServer(Integer.parseInt(args[0]))).runServer();
			
		} else {
			
			(new EchoServer(DEFAULT_PORT)).runServer();
		}
		
	} // end of main method
	
} // end of class EchoServer