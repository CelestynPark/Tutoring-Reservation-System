package core;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Policy {
	private final int startHour;
	private final int endHourExclusive;
	private final int lunchStartHour;
	private final int lunchEndHourExclusive;
	private final int cancelLimitHours;
	
	public Policy() {
		this(10, 19, 13, 14, 24);
	}
	
	public Policy(int startHour, int endHourExclusive, int lunchStartHour, int lunchEndHourExclusive,
			int cancelLimitHours) {
		this.startHour = startHour;
		this.endHourExclusive = endHourExclusive;
		this.lunchStartHour = lunchStartHour;
		this.lunchEndHourExclusive = lunchEndHourExclusive;
		this.cancelLimitHours = cancelLimitHours;
	}
	
	public boolean isLessonDay(TimeSlot slot) {
		DayOfWeek d = slot.getDate().getDayOfWeek();
		return d == DayOfWeek.THURSDAY || d == DayOfWeek.FRIDAY || d == DayOfWeek.SATURDAY;
	}
	
	public boolean isLessonHour(TimeSlot slot) {
		int h = slot.getHour();
		if (h < startHour || h >= endHourExclusive)
			return false;
		if (h >= lunchStartHour && h < lunchEndHourExclusive)
			return false;
		return true;
	}
	
	public boolean isReservationOpen(LocalDateTime now) {
		DayOfWeek d = now.getDayOfWeek();
		if (d != DayOfWeek.MONDAY)
			return false;
		if (now.getHour() > 10)
			return true;
		if (now.getHour() < 10)
			return false;
		return now.getMinute() >= 0;
	}
	
	public boolean canCancel(LocalDateTime now, TimeSlot slot) {
		LocalDateTime lessonStart = slot.toStartDateTime();
		long hours = java.time.Duration.between(now, lessonStart).toHours();
		return hours >= cancelLimitHours;
	}
}
