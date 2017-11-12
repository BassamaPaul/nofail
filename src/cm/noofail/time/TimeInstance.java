package cm.noofail.time;

public class TimeInstance {
	private static TimeInstance instance;
	private Time time;
	
	private TimeInstance() {
		this.time = new Time();
	}
	
	private TimeInstance(long time) {
		this.time = new Time(time);
	}
	
	public static TimeInstance get() {
		if (instance == null) {
			instance = new TimeInstance();
		}
		return instance;
	}
	
	public Time getTime() {
		return time;
	}
}