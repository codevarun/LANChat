package peer;

import gui.ClientWindow;

import java.io.IOException;
import java.net.InetSocketAddress;

import networking.ChannelStatus;
import networking.ChannelUpdate;
import networking.Join;
import networking.Message;
import networking.Refuse;
import networking.TextMessage;

public class Client extends Peer{
	//OVERVIEW: A client represents a client on a network in a server-client model.
	//			It can send and receive basic messages using DatagramSockets.

	// AF(c) = [Client c.getClientHandle on c.serverAddress:c.serverPort, password = c.password ]

	//Rep Invariant:
	// clientHandle != null, password != null, hasPassword != null, clientWindow != null

    private String clientHandle;
    private String password;
    private boolean hasPassword;
    private ClientWindow window;

    //Constructor
    public Client(String serverAddress, int serverPort, String clientHandle, String password) throws IOException{
	//EFFECTS: If serverPort is in use throw SocketException
    //         else initialize clientChat gui and instantiate client with serverAddress, serverPort, clientHandle, and password
    //			send join message to server
        super(serverAddress, serverPort);

        window = new ClientWindow(this);
        window.setVisible(true);

        this.clientHandle = clientHandle;
        this.password = password;

        System.out.println("Starting client on " + this.getLocalAddress() + " : " + this.getPort());
        Message join = new Join(clientHandle, password, this.getLocalAddress(), this.getPort());

        this.send(join);
    }

//Mutator
    public void hasPassword(boolean b){
    //MODIFIES: hasPassword
    //EFFECTS: set hasPassword to b
        hasPassword = b;
    }

//Observer
    public String getClientHandle(){
    //EFFECTS: returns client handle
        return clientHandle;
    }

    public boolean getHasPassword(){
    //EFFECTS: returns hasPassword
    	return hasPassword;
    }

//Client Send/Recv
    public void send(String message) {
    //EFFECTS: Creates a new TextMessage m with message and sends m to server
        Message m = new TextMessage(clientHandle, message, password);
        try {
			send(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void receive(Message message){
    //REQUIRES: message is CHANNEL_UPDATE
    //EFFECTS: parse message into string and update client chat gui
        String text = msgParse(message);
        window.addText(text);
    }

    private String msgParse(Message message){
    //REQUIRES: message is CHANNEL_UPDATE
    //EFFECTS: returns a string of data + " " + clientHandle + " " + message
        ChannelUpdate m = (ChannelUpdate)message;
        String s = ("[" + m.clientHandle + "]: " + m.message);
        return s;
    }


    @Override
    protected void handleMessage(Message message, InetSocketAddress source) {
    //REQUIRES: message and source are not null
    //EFFECTS: parses message:
    //			if message is CHANNEL_UPDATE receive(message)
    //			if message is CHANNEL_STATUS updateUserList with clientHandles in message
    //			if refuse prompt a dialog with the reason and terminate clientWindow

    	super.handleMessage(message, source);

        switch(message.getType()) {
            case TEXT_MESSAGE:
                break;
            case CHANNEL_UPDATE:
                try{
                    receive(message);
                } catch(Exception e){
                	System.out.println(e);
                }
                break;
          case CHANNEL_STATUS:
                ChannelStatus c = (ChannelStatus) message;
                window.updateUserList(c.clientHandles);
                break;
            case ANNOUNCE:
                break;
            case JOIN:
                break;
            case LEAVE:
                break;
            case REFUSE:
            	Refuse r = (Refuse) message;
            	if(r.reason.equalsIgnoreCase("Username already taken")){
            		window.Dialog("Username already in use");
            	}
            	else if(r.reason.equalsIgnoreCase("Invalid Password")){
            		window.Dialog("Incorrect Password");
            	}
            	else
            		window.Dialog("Unknown Error");
                window.dispose();
                this.interrupt();
                break;
            default:
                System.out.println("Peer: received a " + message.getType());
        }
    }

	@Override
	public String getPeerName() {
	//EFFECTS: returns client handle
		return this.clientHandle;
	}

	public boolean repOk() {
		return (clientHandle != null && password != null && window != null);
	}

	public String toString() {
		return "[Client " + getClientHandle()+" on "+serverAddress+":"+this.getPort()+", "+password+"]";
	}

}
