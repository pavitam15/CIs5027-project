
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Class represents a handler for each Client for the Server. Each client to be treated as a separate thread. 
 * @author pavmohammed
 */
public class ClientManager extends Thread {

	// reference variable to store client socket
	private Socket 					clientSocket;
	
	// reference for the Server
	private AbstractServerComponent	server;
	
	// boolean flag to indicate whether to stop the connection
	private boolean					stopConnection;
	
	// Input Output streams to communicate with the client using Serialized objects
	private ObjectOutputStream 		out;
	private ObjectInputStream 		in;
	
	// store an incrementing ID for the client. 
	private int 					clientID;

	/**
	 * Constructor to be called, when handling multiple clients. Requires a ThreadGroup instance from the Server
	 * 
	 * @param threadgroup
	 * @param socket
	 * @param clientID
	 * @param server
	 */
	public ClientManager(ThreadGroup threadgroup, Socket socket, int clientID, AbstractServerComponent server) {
		super(threadgroup, (Runnable) null);

		// Set stopConnection to false until indicated by the client or an error occurs
		this.clientSocket = socket;
		this.server = server;
		this.stopConnection = false;
		this.clientID = clientID;

		// Printed to the server when a client successfully connects
		System.out.println("[ClientManager: ] new client request received, port " 
				+ socket.getPort());

		System.out.println("What is your client type? light or temp?");

		//Initialise IO Streams
		try {
			this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.in = new ObjectInputStream(this.clientSocket.getInputStream());			
		}
		catch(IOException e) {
			System.err.println("[ClientManager: ] error when establishing IO streams on client socket.");
			try {
				closeAll();
			} catch (IOException e1) {
				System.err.println("[ClientManager: ] error when closing connections..." + e1.toString());

			}
		}
		
		start();	// Start thread
	}
	
	/**
	 * Performs the function of sending a message from Server to remote Client#
	 * Uses ObjectOutputStream
	 * 
	 * @param
	 * @throws IOException
	 */
	public void sendMessageToClient(String msg) throws IOException {
		if (this.clientSocket == null || this.out == null)
			throw new SocketException("socket does not exist");
		
		this.out.writeObject(msg);
	}
	
	/**
	 * Closes all connections for the client. 
	 * @throws IOException
	 */
	public void closeAll() throws IOException {
		try {
			// Close the socket
			if (this.clientSocket != null)
				this.clientSocket.close();

			// Close the output stream
			if (this.out != null)
				this.out.close();

			// Close the input stream
			if (this.in != null)
				this.in.close();
		} finally {
			// Set the streams and the sockets to NULL no matter what.

			this.in = null;
			this.in = null;
			this.clientSocket = null;
			
		}
	}

	/**
	 * Receive messages (String) from the client, passes the message to Server's handleMessagesFromClient() method.
	 * Works in a loop until the boolean flag to stop connection is set to true. 
	 */
	@Override
	public void run() {

		// The message from the client
		String msg = "";

		try {
			//Create new instances of CSVReaders
			CsvReaderTemp csvreadtemp = new CsvReaderTemp("sensor_data.csv");

			CsvReaderLight csvreadlight = new CsvReaderLight("sensor_data.csv");

			while (!this.stopConnection) {
				/* This block waits until it reads a message from the client
				and then sends it for handling by the server,
				thread indefinitely waits at the following
				statement until something is received from the server
				 */
				
				msg = (String)this.in.readObject();
				this.server.handleMessagesFromClient(msg, this);

				//Stop the connection when the user types STOP
				if(msg.equals("STOP")) {
					this.stopConnection = true;					
				}

				/* if the user types "temp", the temperature values are displayed
				 with an n second delay between each row and the fan app is opened
				 */
				if(msg.equals("temp")){
					while(true){
						try {
							csvreadtemp.read();
							ArrayList<TempController> sensorlistt = (ArrayList<TempController>) csvreadtemp.getData();
							for(TempController t : sensorlistt){
								System.out.println(t);
								Thread.sleep(500);
								if(t.getTemperature() > 25){
									//speed up fan
								}
								if(t.getTemperature() < 22){
									//slow down fan
								}
								if(msg.equals("STOP")) {
									break; //break works but only with t.something not msg.equals so can't break when csv is running
								}
							}
						}
						catch (IOException | InterruptedException e) {
							System.err.println("Error: Unable to retrieve temperature values");
						}
					}
				}

				/* if the user types "light", the light level values are displayed
				 with an n second delay between each row and the bulb app is opened
				 */
				if(msg.equals("light")){
					while (true){
						try {
							csvreadlight.read();
							ArrayList<LightController> sensorlistl = (ArrayList<LightController>) csvreadlight.getData();
							for(LightController l : sensorlistl){
								System.out.println(l);
								Thread.sleep(500);
								if(msg.equals("STOP")) {
									break; //break works but only with t.something not msg.equals so can't break when csv is running
								}
							}
						}
						catch (IOException | InterruptedException e){
							System.err.println("Error: Unable to retrieve light level values");
						}
					}
				}
				else{
					System.out.println("Error: Please enter either 'light' or 'temp'");
				}
			}

			System.out.println("[ClientManager: ] stopping the client connection ID: " + this.clientID);
		} catch (Exception e) {
			System.err.println("[ClientManager: ] error when reading message from client.." + e.toString());
			/**
			 * If there is an error, while the connection is not stopped, close all.
			 */
			if (!this.stopConnection) {
				try {
					closeAll();
				}
				catch (Exception ex)
				{
					System.err.println("[ClientManager: ] error when closing the connections.." + ex.toString());
				}
			}
		}
		finally {
			if(this.stopConnection) {
				try {
					closeAll();
				} catch (IOException e) {
					System.err.println("[ClientManager: ] error when closing the connections.." + e.toString());
				}
			}
		}
		

					
	}
	
	/**
	 * @return a description of the client, including IP address and host name
	 */
	public String toString() {
		return this.clientSocket == null ? null : this.clientSocket.getInetAddress().getHostName() + " ("
				+ this.clientSocket.getInetAddress().getHostAddress() + ")";
	}
	
	
	//////// GETTERS AND SETTERS ////////////
	public int getClientID() {
		return this.clientID;
	}
	
}
