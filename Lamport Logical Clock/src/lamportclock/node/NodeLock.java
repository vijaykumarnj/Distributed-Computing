package lamportclock.node;

/*
 * Class acts as the synchronizer for send receive operations
 */
public class NodeLock {
	/*
	 * The piggybacked sender's time value when sent from a node
	 */
	private int senderNodeTime;
	private boolean sendComplete;
	private boolean receiveComplete;

	public boolean isReceiveComplete() {
		return receiveComplete;
	}

	public void setReceiveComplete(boolean receiveComplete) {
		this.receiveComplete = receiveComplete;
	}

	public boolean isSendComplete() {
		return sendComplete;
	}

	public void setSendComplete(boolean sendComplete) {
		this.sendComplete = sendComplete;
	}

	public int getSenderNodeTime() {
		return senderNodeTime;
	}

	public void setSenderNodeTime(int senderNodeTime) {
		this.senderNodeTime = senderNodeTime;
	}

	/*
	 * reset the time after the receiver has received it
	 */
	public void resetTime() {
		senderNodeTime = 0;
	}

	public void resetFlags() {
		sendComplete = false;
		receiveComplete = false;
	}
}
