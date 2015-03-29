package system;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import remote.Node;

public class DistributedSystem {
	public static List<Node> nodeList = new ArrayList<Node>();

	public DistributedSystem() {

	}

	public static void main(String[] args) {
		DistributedSystem.nodeList.size();

		try {
			// Create node 1
			Node node = new Node(1, "Node_1");
			node.setNodeUrl("rmi://localhost/Node_1");
			node.addTaskToQueue("L");
			node.addTaskToQueue("L");
			node.addTaskToQueue("C");
			Naming.bind("Node_1", node);
			nodeList.add(node);

			// Create node 2
			Node node2 = new Node(2, "Node_2");
			node2.addTaskToQueue("L");
			node2.addTaskToQueue("C");
			node2.setNodeUrl("rmi://localhost/Node_2");
			Naming.bind("Node_2", node2);
			nodeList.add(node2);

	/*		// Create node 3
			Node node3 = new Node(3, "Node_3");
			node3.addTaskToQueue("L");
			node3.addTaskToQueue("C");
			node3.addTaskToQueue("C");
			node3.setNodeUrl("rmi://localhost/Node_3");
			Naming.bind("Node_3", node3);
			nodeList.add(node3);

			// Create node 3
			Node node4 = new Node(4, "Node_4");
			node4.addTaskToQueue("L");
			node4.addTaskToQueue("C");
			node4.addTaskToQueue("L");			
			node4.addTaskToQueue("C");
			node4.setNodeUrl("rmi://localhost/Node_4");
			Naming.bind("Node_4", node4);
			nodeList.add(node4);

			Node node5 = new Node(5, "Node_5");
			node5.addTaskToQueue("L");
			node5.addTaskToQueue("C");
			node5.addTaskToQueue("L");
			node5.addTaskToQueue("C");
			node5.setNodeUrl("rmi://localhost/Node_5");
			Naming.bind("Node_5", node5);
			nodeList.add(node5);
			
			Node node6 = new Node(6, "Node_6");
			node6.addTaskToQueue("L");
			node6.addTaskToQueue("C");
			node6.addTaskToQueue("L");
			node6.addTaskToQueue("C");
			node6.setNodeUrl("rmi://localhost/Node_6");
			Naming.bind("Node_6", node6);
			nodeList.add(node6); */
			
			for (Node site : nodeList) {
				site.startNode();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}

	}
}
