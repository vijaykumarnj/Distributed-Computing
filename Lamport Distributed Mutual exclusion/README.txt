-----------------------------------------EC1 Assignment 2:---------------------------------------------------
--------------------------------BITS IDNO: 2014HT12017: Vijaykumar NJ----------------------------------------

Leader election using Lamport DME:
The algorithm uses Lamport DME to find the leader, and then the leader is allowed to execute the CS:

Sample Input 
---------------
Following two site information is hard coded in the main function of DistributedSystem.java:

Node_1 - L,L,C
Node_2 - L,C;

Here L indicates it is Local event, C indicates a critical section event. For event C, the election process is triggered
Any number of nodes can be added and tested.

Sample Output
----------------
Node_1 is the Waiting for release message 
Node_2 is the Leader - Granting CS exection now: The queue info is: [Message [clockValue=1, nodeName=Node_2], Message [clockValue=2, nodeName=Node_1]]
---{ Received release message at Node_1
	Queue before remove:[Message [clockValue=1, nodeName=Node_2], Message [clockValue=2, nodeName=Node_1]]
	Queue after remove:[Message [clockValue=2, nodeName=Node_1]] }
Node_1 is the Leader - Granting CS exection now: The queue info is: [Message [clockValue=2, nodeName=Node_1]]
---{ Received release message at Node_2
	Queue before remove:[Message [clockValue=2, nodeName=Node_1]]
	Queue after remove:[] }