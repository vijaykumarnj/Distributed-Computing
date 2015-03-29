package lamportclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lamportclock.event.Event;
import lamportclock.node.NodeLock;
import lamportclock.node.NodeThread;

public class DistributedSystem {

	private static final String RECEIVE = "R";
	private static final String SEND = "S";
	public static final String LOCAL = "L";
	/*
	 * create node clocks for handling the send recieve functionality using
	 * inter-node communication by assigning a lock for each node
	 */
	public static Map<Integer, NodeLock> nodelocks = new HashMap<Integer, NodeLock>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Thread> nodeList = new ArrayList<Thread>();
		List<String[]> inputEventList = new ArrayList<String[]>();
		try {
			System.out
					.println("Please provide input in this format: L,L,L,S,R-2,S \n"
							+ "L: Local event\n"
							+ "S: Send event\n"
							+ "R: Receive event. R-2 indicates node 2 as the event sender");
			// prompt the user to enter their name

			boolean takeUserInput = true;
			String takeUserInputString = null;
			while (takeUserInput) {
				System.out
						.print("Do you want to enter the events for a node (Y/N):");
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				takeUserInputString = br.readLine();
				if (takeUserInputString.equalsIgnoreCase("Y")) {
					System.out.print("Enter the events for the node:");
					br = new BufferedReader(new InputStreamReader(System.in));
					String eventString = br.readLine();
					inputEventList.add(eventString.split(","));
				} else {
					break;
				}
			}
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your name!");
			System.exit(1);
		}
		/*
		 * Each node is represented as a thread here. Based on the input the
		 * nodes are created, then the node events are added to each node.
		 */

//		String[] node1Events = { LOCAL, SEND, "R-2",SEND}; //
//		String[] node2Events = { LOCAL, "R-1", LOCAL, SEND,"R-1" };// 6
		//String[] node3Events = { LOCAL, LOCAL, "R-2","R-1", };// 8

		 for (int j = 1; j <= inputEventList.size(); j++) {
		 createNode(j, inputEventList.get(j - 1), nodeList);
		 }

//		createNode(1, node1Events, nodeList);
//		createNode(2, node2Events, nodeList);
		//createNode(3, node3Events, nodeList);

		for (Thread thread : nodeList) {
			thread.start();
		}

	}

	private static void createNode(int nodeNumber, String[] node1Events,
			List<Thread> nodeList) {
		NodeThread node = new NodeThread(nodeNumber);
		Thread nodeThread = new Thread(node, "Node1Thread");
		nodeList.add(nodeThread);
		nodelocks.put(nodeNumber, new NodeLock());
		for (String event : node1Events) {
			if (event.equals(LOCAL)) {
				node.getEventList().add(new Event(0, -1, -1));
			} else if (event.startsWith(SEND)) {
				node.getEventList().add(new Event(1, nodeNumber, -1));
			} else if (event.startsWith(RECEIVE)) {
				Integer senderNodeNumber = Integer
						.parseInt(event.split("-")[1]);
				node.getEventList().add(new Event(2, senderNodeNumber, -1));
			}
		}
	}
}
