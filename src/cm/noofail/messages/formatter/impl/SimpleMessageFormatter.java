package cm.noofail.messages.formatter.impl;

import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.models.Message;

public class SimpleMessageFormatter implements MessageFormatter {
	@Override
	public String format(Message message) {
		StringBuilder message_ = new StringBuilder();
		message_.append(message.getMessageType());
		message_.append("::");
		message_.append(message.getMember().getNode().getIpAddress());
		message_.append("::");
		message_.append(message.getMember().getNode().getPort());
		
		if (message.getMembershipList() != null) {
			message_.append("::");
		}
		
		return message_.toString();
	}
	
	@Override
	public Message parse(String message) {
		return null;
	}
}