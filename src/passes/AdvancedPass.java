package passes;

import core.Pass;
import core.TimeSlot;

public class AdvancedPass extends Pass {
	public AdvancedPass(String id) {
		super(id, 8);
		super.setMaxSlotsPerLesson(2);
		super.setBaseLessonMinutes(120);
	}
	
	@Override
	public String displayName() {
		return "ADVANCED(4x120)";
	}
	
	@Override
	protected int requiredSlotsForLesson(TimeSlot slot) {
		int base = super.requiredSlotsForLesson(slot);
		if (slot.getDurationMinutes() >= 120)
			return base + 1;
		return base;
	}
}
