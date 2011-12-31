
package networking;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Leave implements Message {
	// OVERVIEW: A Leave is a packet containing the handle, password and address of a client, and
	// is sent to the server immediately after destroying a client

	// AF(c) = [ c.clientHandle, c.password, c.clientAddress, c.clientPort ]
	// Rep Invariant is
	// clientHandle != null, password != null, clientAddress != null
    private MessageType type = MessageType.LEAVE;
    public final String clientHandle;
    public final String password;
    public final String clientAddress;
    public final int clientPort;

    // constructors
    public Leave(String clientHandle, String password, String clientAddress, int clientPort) {
        // REQUIRES: clientHandle is not null, password is not null
        // EFFECTS: Constructs a new Join with the given data
        this.clientHandle = clientHandle;
        this.password = password;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
    }

    public Leave(DataInputStream stream) throws IOException {
        // REQUIRES: stream is not null
        // EFFECTS: Parses a new Join from the given stream, or throws IOException
        // if there was a problem parsing the required fields
        clientHandle = MessageParser.readString(stream);
        password = MessageParser.readString(stream);
        clientAddress = MessageParser.readString(stream);
        clientPort = Integer.parseInt(MessageParser.readString(stream));
    }

    public byte[] getBytes() throws IOException {
        // EFFECTS: Returns the binary representation of this Join as a byte array
        ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(byte_out);

        stream.writeInt(type.id);
        MessageParser.writeString(stream, clientHandle);
        MessageParser.writeString(stream, password);
        MessageParser.writeString(stream, clientAddress);
        MessageParser.writeString(stream, Integer.toString(clientPort));
        stream.flush();

        return byte_out.toByteArray();
    }

    @Override
    public MessageType getType() {
        // EFFECTS: returns the type of this packet
        return type;
    }

	public boolean repOk() {
		return (type == MessageType.LEAVE && clientHandle != null && password != null && clientAddress != null);
	}

	public String toString() {
		return "[ LEAVE: "+clientHandle+", "+password+", "+clientAddress+", "+clientPort+" ]";
	}
}
