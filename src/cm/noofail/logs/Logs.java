package cm.noofail.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logs {
	private Logger logger;
	
	public Logs(Class<?> arg) {
		if (arg == null) {
			throw new NullPointerException();
		}
		logger = LoggerFactory.getLogger(arg.getCanonicalName());
	}
	
	public void info(String message) {
		logger.info(message);
	}
	
	public void error(String message) {
		logger.error(message);
	}
	
	public void warn(String message) {
		logger.warn(message);
	}
	
	public void info(String message, Throwable e) {
		logger.info(message, e);
	}
	
	public void error(String message, Throwable e) {
		logger.error(message, e);
	}
	
	public void warn(String message, Throwable e) {
		logger.warn(message, e);
	}
}