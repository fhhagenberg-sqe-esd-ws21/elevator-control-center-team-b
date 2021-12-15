package at.fhhagenberg.sqe.model;

public class HardwareConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public HardwareConnectionException(String message, java.rmi.RemoteException cause) {
		super(message, cause);
	}
}
