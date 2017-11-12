package cm.noofail.test;

import org.junit.Test;

import static org.junit.Assert.*;

import cm.noofail.time.TimeControl;

public class TimeControlTest {
	
	@Test
	public void testUpdate() {
		TimeControl.update(1);
		assertEquals(TimeControl.getTime(), 2);
		TimeControl.update(2);
		assertEquals(TimeControl.getTime(), 3);
		TimeControl.update(5);
		assertEquals(TimeControl.getTime(), 6);
	}
}