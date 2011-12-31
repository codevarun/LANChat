
package networking;

import java.util.Vector;

public enum MessageType {
	// OVERVIEW: Enumerates the ID fields of chat protocol messages

	// AF(c) = A message identifier of type c.id
	// Rep Invariant is
	// id is a known Message ID, or 0

    TEXT_MESSAGE(1),
    CHANNEL_UPDATE(2),
    ANNOUNCE(4),
    JOIN(5),
    REFUSE(6),
    LEAVE(7),
    CHANNEL_STATUS(8),
    UNKNOWN(0);

    public final int id;
    private MessageType(int id) {
        this.id = id;
    }
    public static MessageType get(int id) {
    	// EFFECTS: returns the MessageType corresponding to id if
    	// id is known, else returns MessageType.UNKNOWN
    	for(MessageType type : MessageType.values()) {
    		if(type.id == id) {
    			return type;
    		}
    	}
    	return UNKNOWN;
    }

    public boolean repOk() {
    	// Returns true if the ID field is a known ID or 0
    	for(MessageType type : MessageType.values()) {
    		if(this.id == type.id) {
    			return true;
    		}
    	}
    	return false;
    }
};

