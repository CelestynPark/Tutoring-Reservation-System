import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import core.Clock;
import core.Policy;
import core.Reservation;
import core.Student;
import core.TimeSlot;
import passes.AdvancedPass;
import passes.BeginnerPass;
import passes.IntermediatePass;
import services.AttendanceService;
import services.ReportService;
import services.ReservationService;

public class Main {
	public static void main(String[] args) {
		Policy policy = new Policy();
		Clock clock = new Clock(LocalDateTime.of(2026,  1, 12, 9, 30));
		ReservationService reservationService = new ReservationService(policy, clock);
		AttendanceService attendanceService = new AttendanceService(clock);
		ReportService reportService = new ReportService();
		
		Student s1 = new Student("S-3001", "Kim", new BeginnerPass("P-9001"));
		Student s2 = new Student("S-3002", "Park", new IntermediatePass("P-9002"));
		Student s3 = new Student("S-3003", "Lee", new AdvancedPass("P-9003"), "VIP");
		
		clock.setNow(LocalDateTime.of(2026, 1, 12, 9, 55));
		reservationService.reserve(s1, LocalDate.of(2026, 1, 15), 10);
		
		clock.setNow(LocalDateTime.of(2026,  1, 12, 10, 0));
		Reservation r1 = reservationService.reserve(s1, LocalDate.of(2026, 1, 15), 10);
		Reservation r2 = reservationService.reserve(s2,  "2026-01-16", 11);
		Reservation r3 = reservationService.reserve(s3, LocalDate.of(2026, 1, 17), 16);
		
		reservationService.reserve(s1, LocalDate.of(2026, 1, 15), 13);
		
		reservationService.cancel(r2.getId(), "학생 사정");
		Reservation r4 = reservationService.reserve(s2, new TimeSlot(LocalDate.of(2026, 1, 16), 12, 60));
		
		clock.setNow(LocalDateTime.of(2026, 1, 15, 9, 30));
		reservationService.cancel(r1.getId());
		
		clock.setNow(LocalDateTime.of(2026, 1, 15, 10, 5));
		attendanceService.markAttended(r1);
		
		clock.setNow(LocalDateTime.of(2026, 1, 17, 16, 5));
		attendanceService.markAttended((r3));
		
		List<Reservation> all = reservationService.getAll();
		reportService.printAllReservation(all);
		System.out.println("");
		reportService.printStudentSummary(s1);
		reportService.printStudentSummary(s2);
		reportService.printStudentSummary(s3);
		
		System.out.println("");
		System.out.println("NOTE: r4 is " + (r4 == null ? "null (예약 실패)" : "created"));
	}
}
