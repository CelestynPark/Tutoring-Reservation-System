package core;

import java.time.LocalDateTime;

public class Clock {
	private LocalDateTime now;
	
	public Clock(LocalDateTime now) {
		this.now = now;
	}
	
	public LocalDateTime now() {
		return now;
	}
	
	public void setNow(LocalDateTime now) {
		this.now = now;
	}
}
