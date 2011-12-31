
package networking;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Refuse implements Message {
	// OVERVIEW: A Refuse is a packet sent to the client to indicate the server has
	// refused to let it join the channel. It contains a string representing the reason why
	// (e.g. wrong password, duplicate client handle)

	// AF(c) = [ c.reason ]
	// Rep Invariant is
	// reason != null
    private final MessageType type = MessageType.REFUSE;
    public final String reason;

    // constructors
    public Refuse(String reason) {
        // REQUIRES: clientHandle is not null, password is not null
        // EFFECTS: Constructs a new Refuse with the given data
        this.reason = reason;
    }

    public Refuse(DataInputStream stream) throws IOException {
        // REQUIRES: stream is not null
        // EFFECTS: Parses a new Refuse from the given stream, or throws IOException
        // if there was a problem parsing the required fields
        reason = MessageParser.readString(stream);
    }

    public byte[] getBytes() throws IOException {
        // EFFECTS: Returns the binary representation of this Refuse as a byte array
        ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(byte_out);

        stream.writeInt(type.id);
        MessageParser.writeString(stream, reason);
        stream.flush();

        return byte_out.toByteArray();
    }

    @Override
    public MessageType getType() {
        // EFFECTS: returns the type of this packet
        return type;
    }

	public boolean repOk() {
		return (type == MessageType.REFUSE && reason != null);
	}

	public String toString() {
		return "[ REFUSE: "+reason+" ]";
	}
}
