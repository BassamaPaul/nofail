package cm.noofail.messages;

import cm.noofail.models.Member;
import cm.noofail.models.Message;
import cm.noofail.models.Node;

public interface MessageSender {
	public void send(String message, Node node);
	public void send(Message message, Node node);
	public void send(Message message, Member member);
}