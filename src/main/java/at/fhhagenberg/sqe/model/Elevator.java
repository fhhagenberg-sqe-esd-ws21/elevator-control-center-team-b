package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
	/**
	 * The status of the elevator doors
	 * Open - The doors are currently open
	 * Closed - The doors are currently closed
	 * Opening - The door are transitioning from Closed to Open
	 * Closing - The door are transitioning from Open to Closed
	 */
	public enum ElevatorDoorStatus {
		Open, 
		Closed, 
		Opening,
		Closing
	}

	/**
	 * Direction of a single elevator
	 * Up - The elevator is currently moving upwards
	 * Down - The elevator is currently moving downwards
	 * Uncommitted - The elevator is currently not moving
	 */
	public enum ElevatorDirection {
		Up, 
		Down, 
		Uncommitted
	}
	
	private Integer elevatorNumber;
	private int floor;
	private ElevatorDoorStatus doorStatus = ElevatorDoorStatus.Closed;
	private ElevatorDirection direction = ElevatorDirection.Uncommitted;
	private int acceleration;
	private int position; // in meters
	private int speed;
	private int weight;
	private int capacity;
	private List<Boolean> floorsToService;
	private int targetFloor;
	private List<IModelObserver> observers;
	
	public Elevator(int elevatorNumber, int numFloors) throws IllegalArgumentException {
		if (elevatorNumber < 0) {
			throw new IllegalArgumentException("Invalid elevator number");
		}
		if (numFloors < 1) {
			throw new IllegalArgumentException("Invalid number of floors");
		}
		
		this.elevatorNumber = elevatorNumber;
		this.floorsToService = new ArrayList<>(numFloors);
		this.observers = new ArrayList<>();
		
		for (int i = 0; i < numFloors; i++) {
			floorsToService.add(false);
		}
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		if (this.floor == floor) {
			return;
		}
		
		this.floor = floor;		
		observers.forEach((obs) -> obs.elevatorFloorUpdated(this));
	}

	public ElevatorDoorStatus getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(ElevatorDoorStatus doorStatus) {
		if (this.doorStatus == doorStatus) {
			return;
		}
		
		this.doorStatus = doorStatus;
		observers.forEach((obs) -> obs.elevatorDoorStatusUpdated(this));
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		if (this.direction == direction) {
			return;
		}
		
		this.direction = direction;
		observers.forEach((obs) -> obs.elevatorDirectionUpdated(this));
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		if (this.acceleration == acceleration) {
			return;
		}
		
		this.acceleration = acceleration;
		observers.forEach((obs) -> obs.elevatorAccelerationUpdated(this));
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) throws IllegalArgumentException {
		if (this.position == position) {
			return;
		}
		if (position < 0) {
			throw new IllegalArgumentException("Invalid elevator position");
		}
		this.position = position;
		observers.forEach((obs) -> obs.elevatorPositionUpdated(this));
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (this.speed == speed) {
			return;
		}
		
		this.speed = speed;
		observers.forEach((obs) -> obs.elevatorSpeedUpdated(this));
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		if (this.weight == weight) {
			return;
		}
		
		this.weight = weight;
		observers.forEach((obs) -> obs.elevatorWeightUpdated(this));
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if (this.capacity == capacity) {
			return;
		}
		
		this.capacity = capacity;
		observers.forEach((obs) -> obs.elevatorCapacityUpdated(this));
	}

	public boolean getFloorToService(int floorNumber) {
		return floorsToService.get(floorNumber);
	}

	public void setFloorToService(int floorNumber, boolean service) {
		if (this.floorsToService.get(floorNumber) == service) {
			return;
		}
		
		this.floorsToService.set(floorNumber, service);
		observers.forEach((obs) -> obs.elevatorFloorsToServiceUpdated(this));
	}

	public int getTargetFloor() {
		return targetFloor;
	}

	public void setTargetFloor(int targetFloor) throws IllegalArgumentException {
		if (this.targetFloor == targetFloor) {
			return;
		}
		if (targetFloor < 0 || targetFloor >= getNumFloors()) {
			throw new IllegalArgumentException("Invalid floor number");
		}
		
		this.targetFloor = targetFloor;
		observers.forEach((obs) -> obs.elevatorTargetFloorUpdated(this));
	}

	public int getElevatorNumber() {
		return elevatorNumber;
	}
	
	public int getNumFloors() {
		return floorsToService.size();
	}
	
	public void addModelObserver(IModelObserver observer) {
		observers.add(observer);
	}	
	
	@Override
	public String toString()
	{
		return "Elevator " + elevatorNumber.toString();
	}
}
