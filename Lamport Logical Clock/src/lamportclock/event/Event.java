package lamportclock.event;

public class Event {
	private int senderNode;
	private int receiverNode;
	private int eventType;

	public int getEventType() {
		return eventType;
	}

	public int getReceiver() {
		return receiverNode;
	}

	public Event(int type, int senderNodeNumber, int receiverNode) {
		this.senderNode = senderNodeNumber;
		this.receiverNode = receiverNode;
		this.eventType = type;
	}

	public int getSenderNodeNumber() {
		return senderNode;
	}

	public int getReceiverNodeNumber() {
		return receiverNode;
	}
}