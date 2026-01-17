package services;

import core.Clock;
import core.Reservation;

public class AttendanceService {
	private final Clock clock;
	
	public AttendanceService(Clock clock) {
		this.clock = clock;
	}
	
	public void markAttended(Reservation r) {
		if (r == null)
			return;
		if (!r.isActive()) {
			System.out.println("ATTEND FAIL: not active");
			return;
		}
		r.attend();
		System.out.println("ATTENDED: " + r.getId() + " at " + clock.now());
	}
}
