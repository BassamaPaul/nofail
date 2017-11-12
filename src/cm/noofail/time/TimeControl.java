package cm.noofail.time;

public class TimeControl {
	public static void update(long time) {
		TimeInstance.get().getTime().update(time);
	}
	
	public static long getTime() {
		return TimeInstance.get().getTime().getTime();
	}
	
	public static void increment() {
		TimeInstance.get().getTime().increment();
	}
}