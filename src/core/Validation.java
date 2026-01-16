package core;

public class Validation {
	public static boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
}
