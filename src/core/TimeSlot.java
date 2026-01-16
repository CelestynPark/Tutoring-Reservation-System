package core;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeSlot {
	private final LocalDate date;
	private final int hour;
	private final int durationMinutes;
	
	public TimeSlot(LocalDate date, int hour, int durationMinutes) {
		this.date = date;
		this.hour = hour;
		this.durationMinutes = durationMinutes;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getDurationMinutes() {
		return durationMinutes;
	}
	
	public LocalDateTime toStartDateTime() {
		return date.atTime(hour, 0);
	}
	
	public String key() {
		return date.toString() + "@" + hour;
	}
}
