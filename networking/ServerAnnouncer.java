package networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import peer.Server;

public class ServerAnnouncer extends Thread {
	// OVERVIEW: A thread that periodically sends out an Announce message to anyone listening
	// on the predefined multicast address. Polls the server it's given to determine the fields
	// of the message each time.

	// AF(c) = A source of Announce messages for server c.server from c.address, every defaultDelay milliseconds
	// The Rep Invariant is
	// socket != null, address != null, server != null

    private MulticastSocket socket;
    private InetSocketAddress address;
    private int delay;
    private Server server;

    public static final String defaultAddress = "230.0.0.1";
    public static final int defaultPort = 45000;
    public static final int defaultDelay = 1000;

    // constructors
    public ServerAnnouncer(Server server) throws IOException {
        socket = new MulticastSocket(defaultPort);
        socket.joinGroup(Inet4Address.getByName(defaultAddress));
        socket.setTimeToLive(32);
        this.setDaemon(true);
        this.address = new InetSocketAddress(defaultAddress, defaultPort);
        this.server = server;
        this.delay = defaultDelay;
    }

    public ServerAnnouncer(String multicastAddress, int announcePort, int delay, Server server) throws IOException {
        socket = new MulticastSocket(announcePort);
        socket.joinGroup(Inet4Address.getByName(multicastAddress));
        socket.setTimeToLive(32);
        this.setDaemon(true);
        this.address = new InetSocketAddress(multicastAddress, announcePort);
        this.delay = delay;
        this.server = server;
    }

    @Override
    public void run() {
    	// EFFECTS: Periodically constructs and sends out an Announce message, first polling server for its
    	// name, address, port, number of members, and whether it needs a password, and putting the information
    	// into the outgoing message. Runs until thread is interrupted.
        while(!this.isInterrupted()) {
            try {
                Announce announce = new Announce(server.getServerName(), server.getLocalAddress(), server.getServerPort(), server.getNumMembers(), server.needsPassword);
                byte[] data = announce.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address);
                socket.send(packet);
            } catch (IOException ex) {
                System.out.println("ServerAnnouncer: failed sending announce: " + ex);
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public boolean repOk() {
		return (socket != null && address != null && server != null);
	}

	public String toString() {
		return "[ ServerAnnouncer: "+ server + " ]";
	}

}
