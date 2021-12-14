package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

public class ElevatorHardwareManager implements IElevatorManager {
	
	private IElevator hwInterface;
	private int numElevators;
	private int numFloors;
	
	public ElevatorHardwareManager(IElevator hwInterface) throws IllegalArgumentException, HardwareConnectionException {
		if (hwInterface == null) {
			throw new IllegalArgumentException("Hardware interface must not be null");
		}
		
		this.hwInterface = hwInterface;
		
		// initialize local variables
		try {
			numElevators = hwInterface.getElevatorNum();
			numFloors = hwInterface.getFloorNum();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	private void checkValidElevator(int elevatorNumber) throws IllegalArgumentException {
		if (elevatorNumber >= numElevators || elevatorNumber < 0) {
			throw new IllegalArgumentException("Invalid elevator number " + elevatorNumber);
		}
	}
	
	private void checkValidFloor(int floor) throws IllegalArgumentException {
		if (floor >= numFloors || floor < 0) { 
			throw new IllegalArgumentException("Invalid floor number " + floor);
		}
	}
	
	@Override
	public boolean isConnected() {
		try {
			hwInterface.getElevatorNum();
			return true;
		}
		catch (Exception exc) {
			return false;
		}
	}
	
	@Override
	public ElevatorDirection getCommittedDirection(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException, IllegalStateException {
		checkValidElevator(elevatorNumber);
		
		int dir = 0;
		try {
			dir = hwInterface.getCommittedDirection(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
		
		switch (dir) {
		case IElevator.ELEVATOR_DIRECTION_UP: return ElevatorDirection.Up;
		case IElevator.ELEVATOR_DIRECTION_DOWN: return ElevatorDirection.Down;
		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED: return ElevatorDirection.Uncommitted;
		default: throw new IllegalStateException("Illegal Elevator direction");
		}
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorAccel(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		checkValidFloor(floor);
		
		try {
			return hwInterface.getElevatorButton(elevatorNumber, floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public ElevatorDoorStatus getElevatorDoorStatus(int elevatorNumber) throws HardwareConnectionException, IllegalStateException {
		checkValidElevator(elevatorNumber);
		
		int doorStatus = 0;
		try {
			doorStatus = hwInterface.getElevatorDoorStatus(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
		
		switch (doorStatus) {
		case IElevator.ELEVATOR_DOORS_OPEN: return ElevatorDoorStatus.Open;
		case IElevator.ELEVATOR_DOORS_CLOSED: return ElevatorDoorStatus.Closed;
		case IElevator.ELEVATOR_DOORS_OPENING: return ElevatorDoorStatus.Opening;
		case IElevator.ELEVATOR_DOORS_CLOSING: return ElevatorDoorStatus.Closing;
		default: throw new IllegalStateException("Illegal Elevator door status");
		}
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorFloor(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getElevatorNum() {
		return numElevators;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorPosition(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorSpeed(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorWeight(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorCapacity(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws HardwareConnectionException {
		checkValidFloor(floor);
		
		try {
			return hwInterface.getFloorButtonDown(floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws HardwareConnectionException {
		checkValidFloor(floor);
		
		try {
			return hwInterface.getFloorButtonUp(floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getFloorHeight() throws HardwareConnectionException {
		try {
			return hwInterface.getFloorHeight();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getFloorNum() {
		return numFloors;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		checkValidFloor(floor);
		
		try {
			return hwInterface.getServicesFloors(elevatorNumber, floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public int getTarget(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getTarget(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, ElevatorDirection direction) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		int dir = 0;
		switch (direction) {
		case Up: dir = IElevator.ELEVATOR_DIRECTION_UP;
			break;
		case Down: dir = IElevator.ELEVATOR_DIRECTION_DOWN;
			break;
		default: dir = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			break;
		}

		try {			
			hwInterface.setCommittedDirection(elevatorNumber, dir);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
		
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		checkValidFloor(floor);
		
		try {
			hwInterface.setServicesFloors(elevatorNumber, floor, service);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public void setTarget(int elevatorNumber, int targetFloor) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		checkValidFloor(targetFloor);
		
		try {
			hwInterface.setTarget(elevatorNumber, targetFloor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}

	@Override
	public long getClockTick() throws HardwareConnectionException {
		try {
			return hwInterface.getClockTick();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException("Hardware connection lost", exc);
		}
	}
	
	
	
}
