package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

public class ElevatorHardwareManager implements IElevatorManager {
	
	private IElevator hwInterface;
	private int numElevators;
	private int numFloors;
	
	public ElevatorHardwareManager(IElevator hwInterface) throws IllegalArgumentException, HardwareConnectionException {
		if (hwInterface == null) {
			throw new IllegalArgumentException(Messages.getString("ElevatorLocalizedStrings.0")); //$NON-NLS-1$
		}
		
		this.hwInterface = hwInterface;
		
		// initialize local variables
		try {
			numElevators = hwInterface.getElevatorNum();
			numFloors = hwInterface.getFloorNum();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.1"), exc); //$NON-NLS-1$
		}
	}

	private void checkValidElevator(int elevatorNumber) throws IllegalArgumentException {
		if (elevatorNumber >= numElevators || elevatorNumber < 0) {
			throw new IllegalArgumentException(Messages.getString("ElevatorLocalizedStrings.2") + elevatorNumber); //$NON-NLS-1$
		}
	}
	
	private void checkValidFloor(int floor) throws IllegalArgumentException {
		if (floor >= numFloors || floor < 0) { 
			throw new IllegalArgumentException(Messages.getString("ElevatorLocalizedStrings.3") + floor); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.4"), exc); //$NON-NLS-1$
		}
		
		switch (dir) {
		case IElevator.ELEVATOR_DIRECTION_UP: return ElevatorDirection.UP;
		case IElevator.ELEVATOR_DIRECTION_DOWN: return ElevatorDirection.DOWN;
		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED: return ElevatorDirection.UNCOMMITTED;
		}
		
		throw new IllegalStateException(Messages.getString("ElevatorLocalizedStrings.5")); //$NON-NLS-1$
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorAccel(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.6"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.7"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.8"), exc); //$NON-NLS-1$
		}
		
		switch (doorStatus) {
		case IElevator.ELEVATOR_DOORS_OPEN: return ElevatorDoorStatus.OPEN;
		case IElevator.ELEVATOR_DOORS_CLOSED: return ElevatorDoorStatus.CLOSED;
		case IElevator.ELEVATOR_DOORS_OPENING: return ElevatorDoorStatus.OPENING;
		case IElevator.ELEVATOR_DOORS_CLOSING: return ElevatorDoorStatus.CLOSING;
		}
		
		throw new IllegalStateException(Messages.getString("ElevatorLocalizedStrings.9")); //$NON-NLS-1$
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorFloor(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.10"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.11"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorSpeed(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.12"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorWeight(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.13"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getElevatorCapacity(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.14"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws HardwareConnectionException {
		checkValidFloor(floor);
		
		try {
			return hwInterface.getFloorButtonDown(floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.15"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws HardwareConnectionException {
		checkValidFloor(floor);
		
		try {
			return hwInterface.getFloorButtonUp(floor);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.16"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public int getFloorHeight() throws HardwareConnectionException {
		try {
			return hwInterface.getFloorHeight();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.17"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.18"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public int getTarget(int elevatorNumber) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		try {
			return hwInterface.getTarget(elevatorNumber);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.19"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, ElevatorDirection direction) throws HardwareConnectionException {
		checkValidElevator(elevatorNumber);
		
		int dir = 0;
		switch (direction) {
		case UP: dir = IElevator.ELEVATOR_DIRECTION_UP;
			break;
		case DOWN: dir = IElevator.ELEVATOR_DIRECTION_DOWN;
			break;
		case UNCOMMITTED: dir = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			break;
		}
		
		try {			
			hwInterface.setCommittedDirection(elevatorNumber, dir);
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.20"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.21"), exc); //$NON-NLS-1$
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
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.22"), exc); //$NON-NLS-1$
		}
	}

	@Override
	public long getClockTick() throws HardwareConnectionException {
		try {
			return hwInterface.getClockTick();
		}
		catch (java.rmi.RemoteException exc) {
			throw new HardwareConnectionException(Messages.getString("ElevatorLocalizedStrings.23"), exc); //$NON-NLS-1$
		}
	}
	
	
	
}
