package core;

public class IdGenerator {
	private int seq;
	
	public IdGenerator(int start) {
		this.seq = start;
	}
	
	public String next(String prefix) {
		seq++;
		return prefix + "-" + seq;
	}
}
