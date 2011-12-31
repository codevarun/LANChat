package peer;

import java.net.InetSocketAddress;

public class ClientInfo {

//Rep Invariant:
    public String clientHandle;	//cannot be null
    public String clientAddress;	//cannot be null and must be a proper ip address (e.g. 192.168.111.1)
    public int clientPort;			// must be in the range of valid ports (e.g. 0 to 65535)
    public InetSocketAddress clientSocketAddress;	//cannot be null must be a valid socket address (e.g. 192.168.111.1:45000)
    public boolean hasFile;

    //Constructor
    public ClientInfo(String clientHandle, String clientAddress, int clientPort){
    //EFFECTS: creates a new ClientInfo with clientHandle, clientAddress, and clientPort in its field.
    //			set clientSocket as clientAddress + clientPort.
    //			set hasFile to false.
        super();
        this.clientHandle = clientHandle;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);
        hasFile = false;
    }

    public boolean same(ClientInfo other){
    //EFFECTS: returns true if this.clientSocket does not equal other.clientSocket else return false
        if(this.clientSocketAddress==other.clientSocketAddress
           ) return false;
        return true;
    }

	public boolean repOk() {
		return (clientHandle != null && clientAddress != null && clientSocketAddress != null);
	}

	public String toString() {
		return "[ClientInfo: " + clientHandle +" at "+clientSocketAddress+"]";
	}
}
