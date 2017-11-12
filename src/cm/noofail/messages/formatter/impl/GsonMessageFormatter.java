package cm.noofail.messages.formatter.impl;

import com.google.gson.Gson;

import cm.noofail.messages.formatter.MessageFormatter;
import cm.noofail.models.Message;

public class GsonMessageFormatter implements MessageFormatter {
	private Gson gson = new Gson();
	
	public String format(Message message) {
		return gson.toJson(message);
	}
	
	public Message parse(String message) {
		return gson.fromJson(message, Message.class);
	}
}