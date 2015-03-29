package lamportclock.node;

import java.util.ArrayList;
import java.util.List;

import lamportclock.DistributedSystem;
import lamportclock.clock.LamportClock;
import lamportclock.event.Event;

public class NodeThread implements Runnable {
	/*
	 * initialize the clock for each node with default time being 0
	 */
	private LamportClock clock = new LamportClock();
	private List<Event> eventList = new ArrayList<Event>();

	public List<Event> getEventList() {
		return eventList;
	}

	private Integer nodeNumber;

	public NodeThread(Integer nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	@Override
	public void run() {
		/*
		 * The node/process logic. For local events the thread just continues
		 * normally. For Send event the thread notifies the waiting thread For
		 * Receive event it waits on the lock of the sending thread.
		 */
		for (int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			if (event.getEventType() == 0) {
				clock.incrementTime();
				printClock(event);
				NodeLock senderLock = DistributedSystem.nodelocks
						.get(nodeNumber);
				senderLock.setReceiveComplete(false);
				senderLock.setSendComplete(false);
			} else if (event.getEventType() == 1) {
				clock.incrementTime();
				NodeLock senderLock = DistributedSystem.nodelocks
						.get(nodeNumber);				
//				System.out.println("Node: "+nodeNumber+" From SEND processing node number is :" + nodeNumber);
				synchronized (senderLock) {
					senderLock.resetFlags();
					senderLock.setSenderNodeTime(clock.getTimeValue());
					try {
						// Wait until the receiver completes the task
						printClock(event);
						while (true) {
							senderLock.setSendComplete(true);
//							System.out
//									.println("----------------Notifying the receiver to continue");
							senderLock.notify();
							if (!senderLock.isReceiveComplete()) {
//								System.out
//										.println("----------------Waiting for receiver to complete");
								senderLock.wait();
//								System.out
//										.println("----------------Notified from receiver ");
								break;
							} else {
								//break;
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else if (event.getEventType() == 2) {
//				System.out.println("Node: "+nodeNumber+"From RECEIVE processing Senders node number is :"
//						+ event.getSenderNodeNumber());
				NodeLock senderLock = DistributedSystem.nodelocks.get(event
						.getSenderNodeNumber());
				synchronized (senderLock) {
					while (true) {
						if (senderLock.isSendComplete()) {
							if (senderLock.getSenderNodeTime() > clock
									.getTimeValue()) {
								clock.setTimeValue(senderLock
										.getSenderNodeTime() + 1);
							} else {
								clock.incrementTime();
							}
							senderLock.notify();
							senderLock.setReceiveComplete(true);
							printClock(event);
							break;
						} else {
							try {
//								System.out
//										.println("----------------Waiting for Sender to finish");
								senderLock.wait();
//								System.out
//										.println("----------------Sender finished");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
					//clear the flags after the receive has complete for new send recieve
					//senderLock.setReceiveComplete(false);
					//senderLock.setSendComplete(false);
				}
			}

		}

	}

	private void printClock(Event event) {
		System.out.println("Node: " + nodeNumber + ", Event - "
				+ getEventName(event.getEventType()) + " , clock time - "
				+ clock.getTimeValue());

	}

	private String getEventName(int eventNum) {
		if (eventNum == 0) {
			return "LOCAL";
		} else if (eventNum == 1) {
			return "SEND";
		} else if (eventNum == 2) {
			return "RECEIVE";
		} else {
			return null;
		}
	}
}
