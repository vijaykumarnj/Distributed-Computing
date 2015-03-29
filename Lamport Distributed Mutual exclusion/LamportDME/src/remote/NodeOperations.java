package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import message.Message;

public interface NodeOperations extends Remote {
	public void sendMessage(Message message) throws RemoteException;

	public void sendReleaseMessage(Message message) throws RemoteException;
}
