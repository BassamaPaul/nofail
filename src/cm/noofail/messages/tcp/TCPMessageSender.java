package cm.noofail.messages.tcp;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cm.noofail.comm.client.socket.SocketClient;
import cm.noofail.messages.MessageSender;
import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.models.Member;
import cm.noofail.models.Message;
import cm.noofail.models.Node;

public class TCPMessageSender implements MessageSender {
	private MessageFormatter formatter;
	
	public TCPMessageSender(MessageFormatter formatter) {
		this.formatter = formatter;
	}
	
	public void send(String request, Node node) {
		if (request == null || node == null) {
			throw new IllegalArgumentException();
		}
		SocketClient socketClient;
		try {
			socketClient = new SocketClient(InetAddress.getByName(node.getIpAddress()), node.getPort());
			socketClient.println(request);
			socketClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void send(Message message, Node node) {
		send(formatter.format(message), node);
	}
	
	public void send(Message message, Member member) {
		send(formatter.format(message), member.getNode());
	}
}