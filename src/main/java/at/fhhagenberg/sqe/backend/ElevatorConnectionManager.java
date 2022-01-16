package at.fhhagenberg.sqe.backend;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import sqelevator.IElevator;

public class ElevatorConnectionManager {

	public static ElevatorHardwareManager getElevatorConnection() throws MalformedURLException, RemoteException,
			NotBoundException, IllegalArgumentException, HardwareConnectionException {
		IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
		return new ElevatorHardwareManager(controller);
	}
}
