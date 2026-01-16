package core;

import java.time.LocalDateTime;

public class Reservation {
	public static final int STATUS_RESERVED = 1;
	public static final int STATUS_CANCELED = 2;
	public static final int STATUS_ATTENDED = 3;
	
	private final String id;
	private final String studentId;
	private final String studentName;
	private final TimeSlot slot;
	private final int requiredSlots;
	private final LocalDateTime createdAt;
	
	private int status;
	private String cancelReason;
	
	public Reservation(String id, Student student, TimeSlot slot, int requiredSlots, LocalDateTime createdAt) {
		this.id = id;
		this.studentId = student.getId();
		this.studentName = student.getName();
		this.slot = slot;
		this.requiredSlots = requiredSlots;
		this.createdAt = createdAt;
		this.status = STATUS_RESERVED;
		this.cancelReason = "";
	}
	
	public String getId() {
		return id;
	}
	
	public String getStudentId() {
		return studentId;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public TimeSlot getSlot() {
		return slot;
	}
	
	public int getRequiredSlots() {
		return requiredSlots;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}
	
	public LocalDateTime getCreateAt() {
		return createdAt;
	}
	
	public boolean isActive() {
		return status == STATUS_RESERVED;
	}
	
	public void cancel(String reason) {
		if (status != STATUS_RESERVED)
			return;
		status = STATUS_CANCELED;
		cancelReason = (reason == null) ? "" : reason;
	}
	
	public void attend() {
		if (status != STATUS_RESERVED)
			return;
		status = STATUS_ATTENDED;
	}
}
