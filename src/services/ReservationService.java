package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.Clock;
import core.IdGenerator;
import core.Pass;
import core.Policy;
import core.Reservation;
import core.Student;
import core.TimeSlot;
import core.Validation;

public class ReservationService {
	private final Policy policy;
	private final Clock clock;
	private final IdGenerator ids;
	
	private final List<Reservation> all;
	private final Map<String, Reservation> byId;
	private final Map<String, Reservation> bySlotKey;
	
	public ReservationService(Policy policy, Clock clock) {
		this.policy = policy;
		this.clock = clock;
		this.ids = new IdGenerator(5000);
		this.all = new ArrayList<>();
		this.byId = new HashMap<>();
		this.bySlotKey = new HashMap<>();
	}
	
	public Reservation reserve(Student student, LocalDate date, int hour) {
		return reserve(student, new TimeSlot(date, hour, 60));
	}
	
	public Reservation reserve(Student student, String yyyyMmDd, int hour) {
		if (Validation.isBlank(yyyyMmDd)) return null;
		LocalDate date = LocalDate.parse(yyyyMmDd.trim());
		return reserve(student, date, hour);
	}
	
	public Reservation reserve(Student student, TimeSlot slot) {
		if (student == null || slot == null) return null;
		
		if (!policy.isReservationOpen(clock.now())) {
			System.out.println("RESERVE FAIL: 예약 오픈 전");
			return null;
		}
		if (!policy.isLessonDay(slot) | !policy.isLessonHour(slot)) {
			System.out.println("RESERVE FAIL: 시간 규칙 위반");
			return null;
		}
		
		Reservation existing = bySlotKey.get(slot.key());
		if (existing != null && existing.isActive()) {
			System.out.println("RESERVE FAIL: 이미 예약됨");
			return null;
		}
		
		Pass pass = student.getPass();
		if (!pass.canConsume(slot)) {
			System.out.println("RESERVED FAIL: 잔여 횟수 부족 (" + pass.displayName() + ", remain=" + pass.getRemainingSlots() + ")");
			return null;
		}
		
		int required = previewRequiredSlots(pass, slot);
		boolean consumed = pass.consume(slot);
		if (!consumed) {
			System.out.println("RESERVE FAIL: 소진 처리 실패");
			return null;
		}
		
		Reservation r = new Reservation(ids.next("R"), student, slot, required, clock.now());
		all.add(r);
		byId.put(r.getId(), r);
		bySlotKey.put(slot.key(), r);
		
		System.out.println("RESERVED: " + r.getId() + " " + r.getStudentName() + " " + slot.key() + "need=" + r.getRequiredSlots() + " remain=" + pass.getRemainingSlots());
		return r;
	}
	
	public boolean cancel(String reservationId) {
		return cancel(reservationId, "");
	}
	
	public boolean cancel(String reservationId, String reason) {
		Reservation r = byId.get(reservationId);
		if (r == null) {
			System.out.println("CANCEL FAIL: not found");
			return false;
		}
		if (!r.isActive()) {
			System.out.println("CANCEL FAIL: not active");
			return false;
		}
		if (!policy.canCancel(clock.now(), r.getSlot())) {
			System.out.println("CANCEL FAIL: 24시간 규칙 위반");
			return false;
		}
		
		Pass pass = findStudentPassForRefund(r);
		r.cancel(reason);
		if (pass != null) {
			pass.refund(r.getSlot());
		}
		System.out.println("CANCELED: " + r.getId() + " reason=" + (reason == null ? "" : reason));
		return true;
	}
	
	public List<Reservation> getAll() {
		return new ArrayList<>(all);
	}
	
	public Reservation findById(String id) {
		return byId.get(id);
	}
	
	private int previewRequiredSlots(Pass pass, TimeSlot slot) {
		int before = pass.getRemainingSlots();
		boolean ok = pass.consume(slot, false);
		if (!ok) return 9999;
		int after = pass.getRemainingSlots();
		pass.refund(slot);
		int restored = pass.getRemainingSlots();
		if (restored != before) {
			pass.refund(slot);
		}
		return Math.max(1, before - after);
	}
	
	private Pass findStudentPassForRefund(Reservation r) {
		return null;
	}
}
