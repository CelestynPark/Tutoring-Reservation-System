package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {
	private final String code;
	private final List<Student> students;
	
	public Department(String code) {
		this.code = code;
		this.students = new ArrayList<>();
	}
	
	public String getCode() {
		return code;
	}
	
	public void add(Student s) {
		if (s == null)
			return;
		students.add(s);
	}
	
	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}
}
