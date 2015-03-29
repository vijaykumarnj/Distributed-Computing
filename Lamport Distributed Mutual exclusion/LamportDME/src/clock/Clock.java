package clock;

public class Clock {
	private Integer timeValue = 0;

	public Integer getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(Integer timeValue) {
		this.timeValue = timeValue;
	}

	public void incrementTime() {
		this.timeValue++;
	}
}
