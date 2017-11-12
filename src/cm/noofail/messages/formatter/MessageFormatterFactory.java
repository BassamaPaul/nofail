package cm.noofail.messages.formatter;

import java.util.HashMap;
import java.util.Map;

import cm.noofail.messages.formatter.impl.GsonMessageFormatter;
import cm.noofail.messages.formatter.impl.SimpleMessageFormatter;

public class MessageFormatterFactory {
	private static Map<String, MessageFormatter> formatters;
	
	static {
		formatters = new HashMap<>();
		formatters.put("json", new GsonMessageFormatter());
		formatters.put("simple", new SimpleMessageFormatter());
	}
	
	private static MessageFormatter getFormatter(String name) {
		return formatters.get(name);
	}
	
	public static MessageFormatter getGsonFormatter() {
		return getFormatter("gson");
	}
	
	public static MessageFormatter getSimpleFormatter() {
		return getFormatter("simple");
	}
}