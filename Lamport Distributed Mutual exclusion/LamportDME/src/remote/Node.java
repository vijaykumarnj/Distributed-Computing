package remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import message.Message;
import system.DistributedSystem;
import clock.Clock;

public class Node extends UnicastRemoteObject implements NodeOperations,
		Runnable {
	private String nodeUrl;
	private Clock clock = new Clock();
	private static final long serialVersionUID = 1L;
	private Integer nodeNumber;
	private String nodeName;
	private Queue<String> taskQueue = new LinkedList<String>();
	private List<Message> messageQueue = new ArrayList<Message>();

	public Node(Integer nodeNumber, String nodeName) throws RemoteException {
		super();
		this.nodeNumber = nodeNumber;
		this.nodeName = nodeName;

	}

	public void startNode() {
		Thread localProcess = new Thread(this);
		localProcess.start();
	}

	public void addTaskToQueue(String event) {
		taskQueue.add(event);
	}

	@Override
	public void run() {
		try {
			while (!taskQueue.isEmpty()) {
				String event = taskQueue.remove();
				if (event.equals("L")) {
					clock.incrementTime();
					// Thread.sleep(50);
				} else if (event.equals("C")) {
					/*
					 * If critical section has to be accessed then, Lamport DME
					 * is applied. Every CS accessing node sends message to
					 * other CS accessing node and get reply.
					 */
					Message message = new Message(clock.getTimeValue(),
							nodeName);
					updateMessage(messageQueue, message);
					sendMessageToAll(message);
					synchronized (this) {
						while (messageQueue.size() > 0) {
							Message firstMessage = messageQueue.get(0);
							if (firstMessage.getNodeName().equals(nodeName)) {
								System.out
										.println(nodeName
												+ " is the Leader - Granting CS exection now: The queue info is: "
												+ messageQueue);
								messageQueue.remove(firstMessage);
								// Send release message to all other nodes
								sendReleaseMessageToAll(firstMessage);
								break;
							} else {
								try {
									System.out.println(nodeName
											+ " is the Waiting for release message ");
									this.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}

				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendReleaseMessage(Message message) throws RemoteException {
		synchronized (this) {
			System.out.println("---{ Received release message at " + nodeName);
			System.out.println("	Queue before remove:" + messageQueue);
			messageQueue.remove(message);
			System.out.println("	Queue after remove:" + messageQueue +" }");
			notify();
		}

	}

	private void sendMessageToAll(Message message) {
		// from other nodes.
		for (Node node : DistributedSystem.nodeList) {
			try {
				if (!node.getNodeUrl().equals(nodeUrl)) {
					NodeOperations remoteHandle = (NodeOperations) Naming
							.lookup(node.getNodeUrl());
					remoteHandle.sendMessage(message);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private void sendReleaseMessageToAll(Message message) {
		// from other nodes.
		for (Node node : DistributedSystem.nodeList) {
			try {
				if (!node.getNodeUrl().equals(nodeUrl)) {
					NodeOperations remoteHandle = (NodeOperations) Naming
							.lookup(node.getNodeUrl());
					remoteHandle.sendReleaseMessage(message);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendMessage(Message payload) {
		System.out.println("---Received send message at :" + nodeNumber);
		updateMessage(messageQueue, payload);
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	private static void updateMessage(List<Message> messageList, Message m) {
		boolean updated = false;
		for (Message message : messageList) {
			if (message.getNodeName().equals(m.getNodeName())) {
				message.setClockValue(m.getClockValue());
				updated = true;
				break;
			}
		}
		// add a new entry since the node is not present
		if (!updated) {
			messageList.add(m);
		}
		Collections.sort(messageList);
	}

}
