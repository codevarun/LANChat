
package peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import networking.FileServer;
import networking.Message;
import networking.MessageParser;

public abstract class Peer extends Thread {
    // OVERVIEW: A peer represents a client or server on the network. It can
    // send and receive basic messages using DatagramSockets.

	// Socket to use for network communication
	private DatagramSocket socket;

    // Address of the server
    public InetSocketAddress serverAddress;

    // File Server
    public FileServer fileserver;

    // constructors

    public Peer() throws IOException {
        // EFFECTS: Initializes this with a new socket and sets the server address
        // to it's own address
    	super();
        socket = new DatagramSocket();
        this.serverAddress = (InetSocketAddress)socket.getLocalSocketAddress();
    }

	public Peer(String serverAddress, int serverPort)
		throws IOException {
        // REQUIRES: serverAddress is a valid host on the network, and serverPort
        // is a valid UDP port
        // EFFECTS: Initializes this with a new socket and destination server address

		socket = new DatagramSocket();
        this.serverAddress = new InetSocketAddress(serverAddress, serverPort);

		if(!this.serverAddress.getAddress().isReachable(10)) {
			System.err.println("Warning: server is not reachable");
		}
	}

	public String getLocalAddress() {
		// EFFECTS: Returns the address of localhost as a String if available, else returns the
		// local address of socket
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return socket.getLocalAddress().getHostAddress();
		}
	}

	public int getPort() {
		// EFFECTS: Returns the port socket is currently bound to
		return socket.getLocalPort();
	}

	public SocketAddress getLocalSocketAddress() {
		// EFFECTS: returns the local socket address (host:port) the socket is
		// currently bound to
		return socket.getLocalSocketAddress();
	}

	public void send(Message message)
		throws IOException {
        // REQUIRES: message is not null
        // EFFECTS: Encapsulates message in a datagram and sends to the server
        System.out.println("[" + getPeerName() + "] Sending " + message.getType() + " from port " + socket.getLocalPort() + " to " + this.serverAddress);
        this.sendTo(message, this.serverAddress);
	}

	public void sendTo(Message message, SocketAddress destination)
		throws IOException {
        // REQUIRES: data is not null
        // EFFECTS: Encapsulates data in a datagram and sends to the specified destination
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, destination);
		socket.send(packet);
	}

	private DatagramPacket receiveData()
		throws IOException {
        // EFFECTS: listens for incoming packets. Blocks until new data is available,
        // then returns the new data as a DatagramPacket
		DatagramPacket receivedPacket = new DatagramPacket(new byte[1024], 1024);
		socket.setSoTimeout(1000);
		socket.receive(receivedPacket);
		return receivedPacket;
	}

    @Override
    public void run() {
    	// EFFECTS: Listens for any new packets from other peers. When a packet is
    	// received, it is parsed into a Message and handleMessage is called with the
    	// new Message as the argument
        DatagramPacket packet;
        Message message;

        while(!this.isInterrupted()) {
            try {
                packet = this.receiveData();

            } catch (SocketTimeoutException ex) {
            	continue;
            } catch (IOException ex) {
                System.err.println("Peer: Error reading from socket:" + ex);
                continue;
            }

            try {

                message = MessageParser.parse(packet.getData());

            } catch (IOException ex) {
                System.err.println("Peer: Error parsing message: " + ex);
                continue;
            }

            handleMessage(message, (InetSocketAddress)packet.getSocketAddress());
        }
		System.out.println("[" + getPeerName() + "] shut down");
    }


    protected void handleMessage(Message message, InetSocketAddress source) {
    	// EFFECTS: A base handler for all messages. Just prints out the peer name and the
    	// type of message received.

    	// Print out type of packet
		System.out.println("[" + getPeerName() + "] Received a " + message.getType());
    }

    public String getPeerName() {
    	// EFFECTS: returns the name/handle of this Peer
    	return "Peer";
    }

    public String shareFile(String filename) throws IOException {
    	if(fileserver == null) {
    		fileserver = new FileServer();
    		fileserver.start();
    	}
    	fileserver.addFile(filename);
    	return fileserver.getURL(filename);
    }

}
