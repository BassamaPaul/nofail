package cm.noofail.membership.protocol.utils;

public class Process extends Thread {
	public Process(String name) {
		super(name);
	}
	
	public void tinyWait() {
		customWait(1);
	}
	
	public void customWait(long millis) {
		if (millis <= 0) {
			return;
		}
		try {
			sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}