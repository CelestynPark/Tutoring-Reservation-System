package passes;

import core.Pass;
import core.TimeSlot;

public class IntermediatePass extends Pass{
	public IntermediatePass(String id) {
		super(id, 4);
		super.setMaxSlotsPerLesson(1);
		super.setBaseLessonMinutes(60);
	}
	
	@Override
	public String displayName() {
		return "INTERMEDIATE(4x60)";
	}
	
	@Override
	protected int requiredSlotsForLesson(TimeSlot slot) {
		return super.requiredSlotsForLesson(slot);
	}
}
