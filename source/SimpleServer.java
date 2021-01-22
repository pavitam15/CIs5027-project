

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class represents a Server component. 
 * 
 * @author thanuja
 * @version 20.11.2019
 */
public class SimpleServer extends AbstractServerComponent implements Runnable {

	// reference variable for server socket. 
	private ServerSocket 			serverSocket;

	// reference variable for ClientHandler for the server. 
	private ClientManager 			clientHandler;

	// boolean flag to indicate the server stop. 
	private boolean 				stopServer;

	// reference variabale for the Thread
	private Thread 					serverListenerThread;

	// reference variable for ThreadGroup when handling multiple clients
	private ThreadGroup 			clientThreadGroup;

	// variable to store server's port number
	int port;
	
	// will be set to true when a new message is received.
	private boolean					changed;
	
	// stores the received messages from client
	private String					receivedMessage;

	private boolean tempproj = false;

	private boolean lightproj = false;
	

	/**
	 * Constructor.
	 * 
	 */
	public SimpleServer() {
		
		this.stopServer = false;
		
		/**
		 * Initializes the ThreadGroup. 
		 * Use of a ThreadGroup is easier when handling multiple clients, although it is not a must. 
		 */
		this.clientThreadGroup = new ThreadGroup("ClientManager threads");
		
	}
	
	/**
	 * Initializes the server. Takes port number, creates a new serversocket instance. 
	 * Starts the server's listening thread. 
	 * @param port
	 * @throws IOException
	 */
	public void initializeServer(int port) throws IOException {

		this.port = port;
		if (serverSocket == null) {
			serverSocket = new ServerSocket(port);
		}

		stopServer = false;
		serverListenerThread = new Thread(this);
		serverListenerThread.start();

	}
	
	/**
	 * handles messages from each client. In this case messages are simply displayed. 
	 * Modified to prepare a response and send back to the same client. Simply changes the input text to upper case. 
	 * This is a shared resource among all client threads, so it has to be synchronized.
	 * 
	 * 
	 * @param msg
	 * @param client
	 */
	public synchronized void handleMessagesFromClient(String msg, ClientManager client) {
		
		// format the client message before displaying in server's terminal output. 
        String formattedMessage = String.format("[client %d] : %s", client.getClientID(), msg); 
		
        this.receivedMessage = formattedMessage;
        this.changed = true;
               
        //this.serverui.getReceiverPanel().updateReceiveWindow(formattedMessage);

        if(msg == "temp"){
        	this.tempproj = true;
		}

		if(msg == "light"){
			this.lightproj = true;
		}
        
        //prepare a response for the client. 
//		String response = "[server says]: " + msg.toUpperCase();					
//		sendMessageToClient(response, client);
		
	}
	
	/**
	 * Handles displaying of messages received from each client. 
	 * Called from handleMessagesFromClient()
	 * @param
	 */
	public void display() {
		System.out.println("display:");
	}
	
	
	public synchronized void sendMessageToClient(String msg) throws IOException {
		
		
		Thread[] clientThreadList = getClientConnections();
		for (int i = 0; i < clientThreadList.length; i++) {
			((ClientManager) clientThreadList[i]).sendMessageToClient(msg);
		}
		
	}
	
	
	/**
	 * Handles, sending a message to client. In this case, it is a string. 
	 * Each client will be calling this to send a message to the client, so it is made synchronized. 
	 * However, this can be handled separately within the ClientManager.
	 * 
	 * @param msg		Message
	 * @param client	Client to be sent
	 */
	public synchronized void sendMessageToClient(String msg, ClientManager client) {
		CsvReaderTemp csvreadtemp = new CsvReaderTemp("sensor_data.csv");
		try {
			csvreadtemp.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return list of Thread[] pertaining to the clients connected to the server
	 */
	public Thread[] getClientConnections() {
		
		Thread[] clientThreadList = new Thread[clientThreadGroup.activeCount()];
		clientThreadGroup.enumerate(clientThreadList);

		return clientThreadList;
	}
	
	/**
	 * Close the server and associated connections. 
	 */
	public void close() {
		
		if (this.serverSocket == null)
			return;

		try {
			this.stopServer = true;
			this.serverSocket.close();

		} catch (IOException e) {
			System.err.println("[server: ] Error in closing server connection...");
		} finally {

			// Close the client sockets of the already connected clients
			Thread[] clientThreadList = getClientConnections();
			for (int i = 0; i < clientThreadList.length; i++) {
				try {
					((ClientManager) clientThreadList[i]).closeAll();
				}
				// Ignore all exceptions when closing clients.
				catch (Exception ex) {
					
				}
			}
			this.serverSocket = null;
			
		}

	}
	
	/**
	 * handles user inputs from the terminal. 
	 * This should run as a separate thread. In this case, main thread. 
	 * 
	 */

	public void runServer() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message = null;

			CsvReaderTemp csvreadtemp = new CsvReaderTemp("sensor_data.csv");

			CsvReaderLight csvreadlight = new CsvReaderLight("sensor_data.csv");

			while (true) {
				message = fromConsole.readLine();
				handleUserInput(message);

				//sendMessageToClient(message);

				if(message.equals("STOP"))
					break;

				if(message.equals("temp")){
					this.tempproj = true;
					while(true){
						try {
							csvreadtemp.read();
							ArrayList<TempController> sensorlistt = (ArrayList<TempController>) csvreadtemp.getData();
							sensorlistt.forEach((t) -> System.out.println(t));
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				if(message.equals("light")){
					this.lightproj = true;
					try {
						csvreadlight.read();
						ArrayList<LightController> sensorlistl = (ArrayList<LightController>) csvreadlight.getData();
						sensorlistl.forEach((l) -> System.out.println(l));
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
			}

			System.out.println("[client: ] stopping client...");
			this.stopServer = true;
			fromConsole.close();
			//close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Can perform any pre-processing or checking of the user input before sending it to server. 
	 * 
	 * @param userResponse
	 */
	public void handleUserInput(String userResponse) {

		if (!this.stopServer) {
			try {
				sendMessageToClient(userResponse);
			} catch (IOException e) {
				System.err.println("[client: ] error when sending message to server: " + e.toString());
				close();
			}
		}
	}
	
	/**
	 * Represents the thread that listens to the port, and creates client connections. 
	 * Here, each connection is treated as a separate thread, and each client is associated with the ThreadGroup. 
	 * 
	 */
	@Override
	public void run() {
		
		System.out.println("[server: ] starting server: listening @ port: " + port);

		// increments when a client connects. 
		int clientCount = 0;

		// loops until stopserver flag is set to true. 
		while (!this.stopServer) {

			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e1) {
				System.err.println("[server: ] Error when handling client connections on port " + port);
			}

			ClientManager cm = new ClientManager(this.clientThreadGroup, clientSocket, clientCount, this);

			// new ClientManager(clientSocket, this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("[server: ] server listner thread interruped..");
			}

			clientCount++;

		}		
	}

	/**
	 * Main() to start the SimpleServer. 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleServer server = new SimpleServer();
		// port number to listen
		int port = 7777;

		try {
			server.initializeServer(port);

		} catch (IOException e) {
			System.err.println("[server: ] Error in initializing the server on port " + port);
		}
		// Main thread continues...
		server.runServer();

	}






}
