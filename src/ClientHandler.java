import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import server.EchoServer;

/**
 * Author : Zobayed Abedin
 */

public class ClientHandler implements Runnable {
	
	private ServerSocket server;
	
	Socket connection;
	
	@Override
	public void run() {
		
		try {
			
			connection = server.accept();
			
			handleClientConnection(connection);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}// end of the method run

	
	public void handleClientConnection(Socket connection){
		
		EchoServer serv = new EchoServer(8080);
		
		// create resources
		ObjectOutputStream output;
		
		ObjectInputStream input;
		
		String clientMessage;
		
		try {
			
			output = new ObjectOutputStream(connection.getOutputStream());
			
			input = new ObjectInputStream(connection.getInputStream());
			
			do { 
				
				try {
					
					// save the client messages as a string and display
					
					clientMessage = (String) input.readObject();
					
					System.out.println(serv.messageNum +" Output> " +clientMessage);
					
				} catch (EOFException | SocketException e) {
					
					clientMessage = null;
					
				}
				
				if (clientMessage != null) {
					
					output.writeObject(serv.messageNum +" FromServer> " +clientMessage);
					
					output.flush();
					
					++serv.messageNum;
					
				}
				
			} while (clientMessage != null);
			
			// close resources
			
			input.close();
			
			output.close();
			
			connection.close();
			
		} catch (IOException | ClassNotFoundException ex) {
			
			System.err.println("Error!!");
			
			ex.printStackTrace();			
		}
	} // end method handleClientConnection
	
	
}
