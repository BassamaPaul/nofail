package cm.noofail.time;

public class Time {
	private long time = 0L;
	
	public Time() {}
	
	public Time(long time) {
		this.time = time;
	}
	
	public synchronized void update(long time) {
		this.time = Math.max(this.time, time) + 1;
	}
	
	public synchronized void increment() {
		time++;
	}
	
	public synchronized void increase(long time) {
		this.time += time;
	}
	
	public synchronized long getTime() {
		return time;
	}
}