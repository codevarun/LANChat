package gui;

import java.net.SocketAddress;


public class ServerVariable {
	
	private SocketAddress address;
	private String serverName;
	private int numMembers;
	private boolean needsPassword;
	
	public ServerVariable(SocketAddress address,String serverName,int numMembers, boolean needsPassword){
		this.serverName = serverName;
		this.address = address;
		this.numMembers = numMembers;
		this.needsPassword = needsPassword;
	}

	public SocketAddress getAddress() {
		return address;
	
	}

	public String getServerName() {
		return serverName;
	}


	public int getNumMembers() {
		return numMembers;
	}

	public void setNumMembers(int numMembers) {
		this.numMembers = numMembers;
	}
	
	public boolean isPasswordRequired(){
		return this.needsPassword;
	}
	
	public String toString(){
		String s = this.serverName + ", Users:" + this.numMembers;
		if(this.needsPassword = true){
			s += ", Require Password";
		}
		return s;
	}
}