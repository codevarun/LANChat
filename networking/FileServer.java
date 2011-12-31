package networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;


public class FileServer extends Thread {
	// OVERVIEW: Shares a group of files through HTTP. Returns a HTTP 404
	// when a request is recieved for an unregistered file.

	// AF(c) = A server that responds to requests to {c.getURL(f) | c.filenames.contains(f)}
	// Rep Invariant: server is not null, filenames is not null
	// Every filename in filenames should represent a valid path on the local filesystem

    private ServerSocket server;
    private Vector<String> filenames;

    // constructor
    public FileServer() throws IOException {
        server = new ServerSocket(0);
        filenames = new Vector<String>();
    }

    public void addFile(String filename) {
    	// EFFECTS: Adds a filename to the list of known filenames
    	// Prepends a forward slash so that Windows paths can be used
    	filenames.add(filename);
    }

    public String getURL(String filename) throws UnknownHostException {
    	// EFFECTS: Returns the URL at which a file can be accessed, assuming the file is already known by the server.
    	// Throws UnknownHostException if there is a problem getting the address of this machine.
    	return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + server.getLocalPort() + "/" + filename;
    }

    @Override
    public void run() {
    	// EFFECTS: accepts new connections from the socket and passes them off to a FileRequestHandler, which runs in
    	// it's own thread.
    	while(true) {
    		try {
    			Socket connected = server.accept();
    			FileRequestHandler handler = new FileRequestHandler(this, connected);
    			handler.start();

    		} catch(IOException e) {
    			System.err.println("Server connection error");
    		}
    	}
    }

    private class FileRequestHandler extends Thread {
    	// OVERVIEW: Handles a single HTTP GET request for a file, checking the given server's
    	// list of filenames before opening the file and sending the data.
    	FileServer server;
    	Socket connection;
    	BufferedReader in;
    	DataOutputStream out;

    	// constructors
    	public FileRequestHandler(FileServer server, Socket connection) throws IOException {
    		this.server = server;
    		this.connection = connection;
    		in = new BufferedReader(new InputStreamReader (connection.getInputStream()));
            out = new DataOutputStream(connection.getOutputStream());
    	}

		@Override
		public void run() {
			// EFFECTS: Decodes the URL request and sends the file to the client, if the file
			// is in server.filenames, else returns HTTP 404 error
			try {
				String request = in.readLine();

	            StringTokenizer tokenizer = new StringTokenizer(request);
	            String method = tokenizer.nextToken();
	            String query = tokenizer.nextToken();

	            // Decode the path and remove the preceding forward-slash
	            String path = URLDecoder.decode(query, "UTF-8").substring(1);

	            System.out.println("FileServer: received a request for " + path);

	            if(method.equals("GET") && server.filenames.contains(path)) {
	            	FileInputStream fin = new FileInputStream(path);
	            	out.writeBytes("HTTP/1.1 200 OK\r\n");
	            	out.writeBytes("Content-Length: " + fin.available() + "\r\n");
	                out.writeBytes("\r\n");

	                byte[] buffer = new byte[1024] ;
	                int bytesRead;

	                while ((bytesRead = fin.read(buffer)) != -1 ) {
	                    out.write(buffer, 0, bytesRead);
	                }
	                fin.close();

	            }
	            else {
	            	String s404 = "<h1>404: Not Found</h1>";
	            	out.writeBytes("HTTP/1.1 404 Not Found\r\n");
	            	out.writeBytes("Content-Length: " + s404.length() +"\r\n");
	            	out.writeBytes("Content-Type: text/html\r\n");
	            	out.writeBytes("\r\n");
	                out.writeBytes(s404);
	            }

				this.connection.close();

			} catch (IOException e) {
				System.err.println("FileServer: Error serving file request");
			}
		}
    }


	public boolean repOk() {
		return (server != null && filenames != null);
	}

	public String toString() {
		return "[ FileServer hosting: "+filenames+" ]";
	}

}

