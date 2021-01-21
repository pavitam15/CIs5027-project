


public abstract class AbstractServerComponent {

	public abstract void handleMessagesFromClient(String msg, ClientManager client);
	public abstract void sendMessageToClient(String msg, ClientManager client);
	
}
