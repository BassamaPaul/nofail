package cm.noofail.messages.queue;

import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.models.Message;

public class MessageQueue {
	public static void queue(String message, MessageFormatter formatter) {
		if (message == null || formatter == null) {
			return;
		}
		Message message_ = formatter.parse(message);
		MessageQueueInstance.getInstance().getQueue().queue(message_);
	}
	
	public static Message dequeue() {
		return MessageQueueInstance.getInstance().getQueue().dequeue();
	}
	
	public static boolean isEmpty() {
		return MessageQueueInstance.getInstance().getQueue().isEmpty();
	}
}