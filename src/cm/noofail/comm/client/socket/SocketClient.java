package cm.noofail.comm.client.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import cm.noofail.logs.Logs;

public class SocketClient {
	Logs logs = new Logs(getClass());
	
    private Socket socket;

    public SocketClient(InetAddress ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
        	logs.error("Failed when creating socket client.");
        }
    }
    
    public void println(String message) {
        try {
        	if (socket == null) {
        		return;
        	}
        	PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void write(byte[] message) {
        try {
            PrintStream writer = new PrintStream(socket.getOutputStream());
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public String read() {
        try {
        	byte[] data = new byte[2048];
            InputStream inputStream = socket.getInputStream();
            int count = inputStream.read(data);
            return new String(data, 0, count);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public void close() {
        try {
            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}