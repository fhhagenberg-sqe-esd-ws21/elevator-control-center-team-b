package at.fhhagenberg.sqe.model;

public class HardwareConnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public java.rmi.RemoteException innerException;

	public HardwareConnectionException(String message, java.rmi.RemoteException innerExc) {
		super(message);
		this.innerException = innerExc;		
	}
}
