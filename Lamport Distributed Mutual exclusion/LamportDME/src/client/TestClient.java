package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import remote.NodeOperations;

public class TestClient {
	public static void main(String[] args) {
		try {
			NodeOperations node1 = (NodeOperations) Naming
					.lookup("rmi://localhost/Node_1");
			NodeOperations node2 = (NodeOperations) Naming
					.lookup("rmi://localhost/Node_2");
			NodeOperations node3 = (NodeOperations) Naming
					.lookup("rmi://localhost/Node_3");


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

}
