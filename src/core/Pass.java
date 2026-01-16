package core;

public class Pass {
	private final String id;
	private int remainingSlots;
	private boolean active;
	
	protected int maxSlotsPerLesson;
	protected int baseLessonMinutes;
	
	public Pass(String id, int remainingSlots) {
		this.id = id;
		this.remainingSlots = Math.max(0, remainingSlots);
		this.active = true;
		this.maxSlotsPerLesson = 2;
		this.baseLessonMinutes = 60;
	}
	
	public String getId() {
		return id;
	}
	
	public int getRemainingSlots() {
		return remainingSlots;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String displayName() {
		return "PASS";
	}
	
	public boolean canConsume(TimeSlot slot) {
		validateSlotBasics(slot);
		int need = requiredSlotsForLesson(slot);
		return active && remainingSlots >= need;
	}
	
	public boolean consume(TimeSlot slot) {
		return consume(slot, true);
	}
	
	public boolean consume(TimeSlot slot, boolean enforceMaxPerLesson) {
		validateSlotBasics(slot);
		int need = requiredSlotsForLesson(slot);
		if (enforceMaxPerLesson && need > maxSlotsPerLesson)
			return false;
		if (!active)
			return false;
		if (remainingSlots < need)
			return false;
		remainingSlots -= need;
		if (remainingSlots == 0)
			active = false;
		return true;
	}
	
	public void refund(TimeSlot slot) {
		validateSlotBasics(slot);
		int need = requiredSlotsForLesson(slot);
		remainingSlots += need;
		if (remainingSlots > 0)
			active = true;
	}
	
	protected int requiredSlotsForLesson(TimeSlot slot) {
		return 1;
	}
	
	protected final void validateSlotBasics(TimeSlot slot) {
		if (slot == null)
			throw new IllegalArgumentException("slot is null");
		if (slot.getDurationMinutes() <= 0)
			throw new IllegalArgumentException("duration invalid");
	}
	
	protected final void setMaxSlotsPerLesson(int maxSlotsPerLesson) {
		this.maxSlotsPerLesson = Math.max(1, maxSlotsPerLesson);
	}
	
	protected final void setBaseLessonMinutes(int baseLessonMinutes) {
		this.baseLessonMinutes = Math.max(30, baseLessonMinutes);
	}
}
