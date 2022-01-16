package mocks;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;
import sqelevator.IElevator;

public class ElevatorMock implements IElevator {

	private boolean connected;
	private int numElevators;
	private int numFloors;
	private int floorHeight;

	private List<Integer> committedDirections;
	private List<Integer> elevatorAccelerations;
	private List<Integer> doorStatus;
	private List<Integer> elevatorFloor;
	private List<Integer> elevatorPosition;
	private List<Integer> elevatorSpeed;
	private List<Integer> elevatorWeight;
	private List<Integer> elevatorCapacity;
	private List<Boolean> floorButtonDown;
	private List<Boolean> floorButtonUp;
	private List<Integer> elevatorTargets;
	private List<List<Boolean>> elevatorButtons;
	private List<List<Boolean>> servicesFloors;
	private int clockTick;

	public ElevatorMock(int numElevators, int numFloors) {
		this.numElevators = numElevators;
		this.numFloors = numFloors;

		committedDirections = new ArrayList<>(numElevators);
		elevatorAccelerations = new ArrayList<>(numElevators);
		doorStatus = new ArrayList<>(numElevators);
		elevatorFloor = new ArrayList<>(numElevators);
		elevatorPosition = new ArrayList<>(numElevators);
		elevatorSpeed = new ArrayList<>(numElevators);
		elevatorWeight = new ArrayList<>(numElevators);
		elevatorCapacity = new ArrayList<>(numElevators);
		floorButtonDown = new ArrayList<>(numFloors);
		floorButtonUp = new ArrayList<>(numFloors);
		elevatorTargets = new ArrayList<>(numElevators);
		elevatorButtons = new ArrayList<>(numElevators);
		servicesFloors = new ArrayList<>(numElevators);

		for (int i = 0; i < numFloors; i++) {
			floorButtonUp.add(false);
			floorButtonDown.add(false);
		}

		for (int i = 0; i < numElevators; i++) {
			committedDirections.add(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
			elevatorAccelerations.add(0);
			doorStatus.add(IElevator.ELEVATOR_DOORS_CLOSED);
			elevatorFloor.add(0);
			elevatorPosition.add(0);
			elevatorSpeed.add(0);
			elevatorWeight.add(0);
			elevatorCapacity.add(0);
			elevatorTargets.add(0);
			elevatorButtons.add(new ArrayList<>(numFloors));
			servicesFloors.add(new ArrayList<>(numFloors));
			for (int j = 0; j < numFloors; j++) {
				elevatorButtons.get(i).add(false);
				servicesFloors.get(i).add(false);
			}
		}
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) {
		return elevatorAccelerations.get(elevatorNumber);
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) {
		return elevatorButtons.get(elevatorNumber).get(floor);
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) {
		return elevatorFloor.get(elevatorNumber);
	}

	@Override
	public int getElevatorNum() {
		return numElevators;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) {
		return elevatorPosition.get(elevatorNumber);
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) {
		return elevatorSpeed.get(elevatorNumber);
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) {
		return elevatorWeight.get(elevatorNumber);
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) {
		return elevatorCapacity.get(elevatorNumber);
	}

	@Override
	public boolean getFloorButtonDown(int floor) {
		return floorButtonDown.get(floor);
	}

	@Override
	public boolean getFloorButtonUp(int floor) {
		return floorButtonUp.get(floor);
	}

	@Override
	public int getFloorHeight() {
		return floorHeight;
	}

	@Override
	public int getFloorNum() {
		return numFloors;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) {
		return servicesFloors.get(elevatorNumber).get(floor);
	}

	@Override
	public int getTarget(int elevatorNumber) {
		return elevatorTargets.get(elevatorNumber);
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
		servicesFloors.get(elevatorNumber).set(floor, service);
	}

	@Override
	public void setTarget(int elevatorNumber, int target) {
		elevatorTargets.set(elevatorNumber, target);
	}

	@Override
	public long getClockTick() {
		return clockTick;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setFloorHeight(int floorHeight) {
		this.floorHeight = floorHeight;
	}

	public void setCommittedDirections(int elevatorNumber, int direction) {
		committedDirections.set(elevatorNumber, direction);
	}

	public void setElevatorAccelerations(int elevatorNumber, int acceleration) {
		elevatorAccelerations.set(acceleration, acceleration);
	}

	public void setElevatorButtons(int elevatorNumber, int floorNumber, boolean set) {
		elevatorButtons.get(elevatorNumber).set(floorNumber, set);
	}

	public void setDoorStatus(int elevatorNumber, int status) {
		doorStatus.set(elevatorNumber, status);
	}

	public void setElevatorFloor(int elevatorNumber, int floor) {
		elevatorFloor.set(elevatorNumber, floor);
	}

	public void setElevatorPosition(int elevatorNumber, int position) {
		elevatorPosition.set(elevatorNumber, position);
	}

	public void setElevatorSpeed(int elevatorNumber, int speed) {
		elevatorSpeed.set(elevatorNumber, speed);
	}

	public void setElevatorWeight(int elevatorNumber, int weight) {
		elevatorWeight.set(elevatorNumber, weight);
	}

	public void setElevatorCapacity(int elevatorNumber, int capacity) {
		elevatorCapacity.set(elevatorNumber, capacity);
	}

	public void setFloorButtonDown(int floor, boolean down) {
		floorButtonDown.set(floor, down);
	}

	public void setFloorButtonUp(int floor, boolean up) {
		floorButtonUp.set(floor, up);
	}

	public void setClockTick(int clockTick) {
		this.clockTick = clockTick;
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) {
		return committedDirections.get(elevatorNumber);
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) {
		return doorStatus.get(elevatorNumber);
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) {
		committedDirections.set(elevatorNumber, direction);
	}
}
