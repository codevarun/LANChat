
package networking;

import java.io.DataInputStream;
import java.io.IOException;

public class UnknownMessage implements Message {
	// OVERVIEW: Represents a message that cannot be parsed, either because of an unknown
	// ID field or errors in parsing the contained data

	// AF(c) = An UNKNOWN Message

    // constructors
    public UnknownMessage(DataInputStream stream) throws IOException {

    }

    public byte[] getBytes() throws IOException {
        throw new IOException("Can't construct this packet");
    }

    public MessageType getType() {
        // EFFECTS: returns the type of this packet
        return MessageType.UNKNOWN;
    }

    public boolean repOk() {
		return true;
	}

	public String toString() {
		return "[ UNKNOWN Message ]";
	}
}
