/* Abstract class to maintain inheritance
hierarchy for the Server and ClientManager
*/

public abstract class AbstractServerComponent {

	public abstract void handleMessagesFromClient(String msg, ClientManager client);
	public abstract void sendMessageToClient(String msg, ClientManager client);
	
}
