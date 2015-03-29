package message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Message implements Comparable<Message>, Serializable {
	private static final long serialVersionUID = 1L;

	public Message(Integer clockValue, String nodeName) {
		super();
		this.clockValue = clockValue;
		this.nodeName = nodeName;
	}

	private Integer clockValue;
	public Integer getClockValue() {
		return clockValue;
	}

	public void setClockValue(Integer clockValue) {
		this.clockValue = clockValue;
	}

	private String nodeName;

	@Override
	public int compareTo(Message obj) {
		if (this.clockValue > obj.clockValue) {
			return 1;
		} else if (this.clockValue < obj.clockValue) {
			return -1;
		} else {
			return 0;
		}
	}

	public String getNodeName() {
		return nodeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeName == null) ? 0 : nodeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		return true;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public static void main(String[] args) {
		Message m1 = new Message(2, "Node_1");
		Message m2 = new Message(3, "Node_2");
		Message m3 = new Message(3, "Node_1");
		Message m4 = new Message(2, "Node_3");
		List<Message> dmeList = new ArrayList<Message>();
		updateMessage(dmeList, m1);
		updateMessage(dmeList, m2);
		updateMessage(dmeList, m3);
		updateMessage(dmeList, m4);

		System.out.println("--Before remove--");
		System.out.println(dmeList);
		System.out.println("--After remove--");
		dmeList.remove(m4);
		System.out.println(dmeList);

	}

	private static void updateMessage(List<Message> dmeList, Message m) {
		boolean updated = false;
		for (Message message : dmeList) {
			if (message.getNodeName().equals(m.getNodeName())) {
				message.clockValue = m.clockValue;
				updated = true;
				break;
			}
		}
		// add a new entry since the node is not present
		if (!updated) {
			dmeList.add(m);
		}
		Collections.sort(dmeList);
	}

	private static void removeMessage(List<Message> dmeList, Message m) {
		dmeList.remove(m);
		Collections.sort(dmeList);
	}

	@Override
	public String toString() {
		return "Message [clockValue=" + clockValue + ", nodeName=" + nodeName
				+ "]";
	}
}
