package passes;

import core.Pass;
import core.TimeSlot;

public class BeginnerPass extends Pass {
	public BeginnerPass(String id) {
		super(id, 8);
		super.setMaxSlotsPerLesson(1);
		super.setBaseLessonMinutes(60);
	}
	
	@Override
	public String displayName() {
		return "BEGINNER(8x60)";
	}
	
	@Override
	protected int requiredSlotsForLesson(TimeSlot slot) {
		int base = super.requiredSlotsForLesson(slot);
		if (slot.getDurationMinutes() > 60)
			return base + 1;
		return base;
	}
}
