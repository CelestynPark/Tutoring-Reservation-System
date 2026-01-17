package services;

import java.util.List;

import core.Pass;
import core.Reservation;
import core.Student;

public class ReportService {
	public void printAllReservation(List<Reservation> all) {
		System.out.println("RESERVATION LIST");
		if (all == null)
			return;
		for (Reservation r : all) {
			System.out.println(r.getId() + " " + r.getStudentName() + " " + r.getSlot().key() + " status="
					+ statusLabel(r.getStatus()) + " need=" + r.getRequiredSlots());
		}
	}
	
	public void printStudentSummary(Student s) {
		if (s == null)
			return;
		Pass p = s.getPass();
		System.out.println("STUDENT SUMMARY: " + s.getId() + " " + s.getName()
				+ (s.getTag().isEmpty() ? "" : " [" + s.getTag() + "]"));
		System.out.println(" pass   : " + p.displayName() + " (" + p.getId() + ")");
		System.out.println(" remain : " + p.getRemainingSlots());
	}
	
	private String statusLabel(int st) {
		if (st == Reservation.STATUS_RESERVED)
			return "RESERVED";
		if (st == Reservation.STATUS_CANCELED)
			return "CANCELED";
		if (st == Reservation.STATUS_ATTENDED)
			return "ATTENDED";
		return "UNKNOWN";
	}
}
