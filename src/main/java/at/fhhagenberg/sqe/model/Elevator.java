package at.fhhagenberg.sqe.model;

import java.util.ArrayList;

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
	
	private int elevatorNumber;
	private int floor;
	private ElevatorDoorStatus doorStatus;
	private ElevatorDirection direction;
	private int acceleration;
	private ArrayList<Boolean> floorsPressed;
	private int position; // in meters
	private int speed;
	private int weight;
	private int capacity;
	private ArrayList<Boolean> floorsToService;
	private int targetFloor;
	private ArrayList<ModelObserver> observers;
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		this.floorsPressed = new ArrayList<Boolean>();
		this.floorsToService = new ArrayList<Boolean>();
		this.observers = new ArrayList<ModelObserver>();
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		if (this.floor == floor) {
			return;
		}
		
		this.floor = floor;		
		observers.forEach((obs) -> obs.ElevatorFloorUpdated(this));
	}

	public ElevatorDoorStatus getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(ElevatorDoorStatus doorStatus) {
		if (this.doorStatus == doorStatus) {
			return;
		}
		
		this.doorStatus = doorStatus;
		observers.forEach((obs) -> obs.ElevatorDoorStatusUpdated(this));
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		if (this.direction == direction) {
			return;
		}
		
		this.direction = direction;
		observers.forEach((obs) -> obs.ElevatorDirectionUpdated(this));
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		if (this.acceleration == acceleration) {
			return;
		}
		
		this.acceleration = acceleration;
		observers.forEach((obs) -> obs.ElevatorAccelerationUpdated(this));
	}

	public boolean getFloorIsPressed(int floorNumber) {
		return floorsPressed.get(floorNumber);
	}

	public void setFloorIsPressed(int floorNumber, boolean pressed) {
		if (this.floorsPressed.get(floorNumber) == pressed) {
			return;
		}
		
		this.floorsPressed.set(floorNumber, pressed);
		observers.forEach((obs) -> obs.ElevatorFloorsPressedUpdated(this));
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		if (this.position == position) {
			return;
		}
		
		this.position = position;
		observers.forEach((obs) -> obs.ElevatorPositionUpdated(this));
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (this.speed == speed) {
			return;
		}
		
		this.speed = speed;
		observers.forEach((obs) -> obs.ElevatorSpeedUpdated(this));
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		if (this.weight == weight) {
			return;
		}
		
		this.weight = weight;
		observers.forEach((obs) -> obs.ElevatorWeightUpdated(this));
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		if (this.capacity == capacity) {
			return;
		}
		
		this.capacity = capacity;
		observers.forEach((obs) -> obs.ElevatorCapacityUpdated(this));
	}

	public boolean getFloorToService(int floorNumber) {
		return floorsToService.get(floorNumber);
	}

	public void setFloorToService(int floorNumber, boolean service) {
		if (this.floorsToService.get(floorNumber) == service) {
			return;
		}
		
		this.floorsToService.set(floorNumber, service);
		observers.forEach((obs) -> obs.ElevatorFloorsToServiceUpdated(this));
	}

	public int getTargetFloor() {
		return targetFloor;
	}

	public void setTargetFloor(int targetFloor) {
		if (this.targetFloor == targetFloor) {
			return;
		}
		
		this.targetFloor = targetFloor;
		observers.forEach((obs) -> obs.ElevatorTargetFloorUpdated(this));
	}

	public int getElevatorNumber() {
		return elevatorNumber;
	}
	
	public void addModelObserver(ModelObserver observer) {
		observers.add(observer);
	}	
}
