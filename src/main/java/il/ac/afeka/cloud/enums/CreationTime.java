package il.ac.afeka.cloud.enums;

public enum CreationTime {
	lastDay(1000 * 60 * 60 * 24),
	lastWeek(1000 * 60 * 60 * 24 * 7),
	lastMonth((long)(1000) * 60 * 60 * 24 * 30);
	
	private final long millisecs;
	
	private CreationTime(long millisecs) {
		this.millisecs = millisecs;
	}
	
	public long millisecs() {
		return this.millisecs;
	}
}
