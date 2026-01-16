package core;

public class Student {
	private final String id;
	private final String name;
	private final String tag;
	private final Pass pass;
	
	private int attendedCount;
	private int canceledCount;
	
	public Student(String id, String name, Pass pass) {
		this(id, name, pass, "");
	}
	
	public Student(String id, String name, Pass pass, String tag) {
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.tag = (tag == null) ? "" : tag;
		this.attendedCount = 0;
		this.canceledCount = 0;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTag() {
		return tag;
	}
	
	public Pass getPass() {
		return pass;
	}
	
	public int getCanceledCount() {
		return canceledCount;
	}
	
	public void onAttended() {
		attendedCount++;
	}
	
	public void onCanceled() {
		canceledCount++;
	}
}
