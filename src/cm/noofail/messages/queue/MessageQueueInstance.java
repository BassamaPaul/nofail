package cm.noofail.messages.queue;

import cm.noofail.models.Message;

public class MessageQueueInstance {
	private static MessageQueueInstance instance;
	
	private Queue<Message> queue = new Queue<>();
	
	private MessageQueueInstance() {}
	
	public static MessageQueueInstance getInstance() {
		if (instance == null) {
			instance = new MessageQueueInstance();
		}
		return instance;
	}
	
	public Queue<Message> getQueue() {
		return queue;
	}
}