package cm.noofail.messages.formatter;

import cm.noofail.models.Message;

public interface MessageFormatter {
	public String format(Message message);
	public Message parse(String message);
}