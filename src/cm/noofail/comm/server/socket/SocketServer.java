package cm.noofail.comm.server.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cm.noofail.logs.Logs;
import cm.noofail.membership.protocol.utils.Process;
import cm.noofail.messages.MessageReceiver;
import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.messages.queue.MessageQueue;
import cm.noofail.models.Node;

public class SocketServer extends Process implements MessageReceiver {
	private static final String name = "SocketServer";
	
	Logs logs = new Logs(this.getClass());
	private static final int BUFFER_SIZE = 4096;
	
	private Node node;
	private ServerSocket serverSocket;
	private boolean isStopped;
	
	private MessageFormatter formatter;
	
	public SocketServer(Node node, MessageFormatter formatter) {
		super(name);
		if (node == null) {
			throw new IllegalArgumentException();
		}
		this.node = node;
		this.formatter = formatter;
	}
	
	@SuppressWarnings("unused")
	private Thread runningThread = null;
	
	public void run() {
        synchronized(this) {
            runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(!isStopped()) {
            try {
                String message = process(serverSocket.accept());
                MessageQueue.queue(message, formatter);
            } catch (IOException e) {
                if (isStopped()) {
                	logs.info("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error when accepting client connection.", e);
            }
            tinyWait();
        }
        logs.info("Server Stopped.");
    }
	
	private String process(Socket clientSocket) throws IOException {
		InputStream input = clientSocket.getInputStream();
		String request = null;
        try {
            byte[] data = new byte[BUFFER_SIZE];
            int count = input.read(data);
            request = new String(data, 0, count);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	input.close();
            clientSocket.close();
		}
        return request;
	}
	
	private synchronized boolean isStopped() {
        return isStopped;
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(node.getPort());
            logs.info("Socket server opened");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port: " + node.getPort(), e);
        }
    }
}