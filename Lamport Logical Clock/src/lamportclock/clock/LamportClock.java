package lamportclock.clock;

public class LamportClock {
	private int timeValue = 0;

	public int getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(int timeValue) {
		this.timeValue = timeValue;
	}

	public void incrementTime() {
		this.timeValue++;
	}
}
