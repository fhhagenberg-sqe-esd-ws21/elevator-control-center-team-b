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
	private ArrayList<Integer> floorsToService;
	private int targetFloor;
	private ArrayList<ElevatorObserver> observers;
	
	public Elevator(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
		this.floorsPressed = new ArrayList<Boolean>();
		this.floorsToService = new ArrayList<Integer>();
		this.observers = new ArrayList<ElevatorObserver>();
	}
	
}
