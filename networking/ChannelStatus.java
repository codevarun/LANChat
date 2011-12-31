
package networking;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ChannelStatus implements Message {
    // OVERVIEW: A ChannelUpdate is a packet containing a list of the handles
	// of all clients currently joined to the server

    // The binary format is:
    // int: Indicates the type of packet (PacketType.CHANNEL_UPDATE)
    // int: the number of clients
	// A list with entries like this:
	// 	int: length of the client handle string
    // 	[handleLength] bytes: The client's handle name

	// AF(c) = { c.clientHandles.get(i) | 0<=i<c.clientHandles.size() }
	// Rep Invariant is
	// clientHandles != null
    private final MessageType type = MessageType.CHANNEL_STATUS;
    public final Vector<String> clientHandles;

    // constructors
    public ChannelStatus(Vector<String> clientHandles) {
        // REQUIRES: clientHandles is not null and none of the strings in clientHandles are null
        // EFFECTS: Constructs a new ChannelStatus with the given list of client names
        this.clientHandles = clientHandles;
    }

    public ChannelStatus(DataInputStream stream) throws IOException {
        // REQUIRES: stream is not null
        // EFFECTS: Parses a new ChannelStatus from the given stream, or throws IOException
        // if there was a problem parsing the required fields
        int numClients = stream.readInt();
        clientHandles = new Vector<String>(numClients);
        for(int i=0; i<numClients; i++) {
        	clientHandles.add(MessageParser.readString(stream));
        }
    }

    public byte[] getBytes() throws IOException {
        // EFFECTS: Returns the binary representation of this ChannelStatus as a byte array
        ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byte_out);

        out.writeInt(type.id);
        out.writeInt(clientHandles.size());
        for(String handle : clientHandles) {
        	MessageParser.writeString(out, handle);
        }
        out.flush();

        return byte_out.toByteArray();
    }

    @Override
    public MessageType getType() {
        // EFFECTS: returns the type of this packet
        return type;
    }

	public boolean repOk() {
		return (type == MessageType.CHANNEL_STATUS && clientHandles != null);
	}

	public String toString() {
		return "[ CHANNEL_STATUS: "+this.clientHandles+" ]";
	}
}
