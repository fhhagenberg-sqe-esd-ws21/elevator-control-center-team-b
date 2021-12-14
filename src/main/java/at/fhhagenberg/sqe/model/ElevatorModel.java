package at.fhhagenberg.sqe.model;

import java.util.ArrayList;

public class ElevatorModel {
	
	private ArrayList<Elevator> elevators;
	private ArrayList<Floor> floors;
	
	public ElevatorModel() {
		elevators = new ArrayList<Elevator>();
		floors = new ArrayList<Floor>();
	}
	
	public int getNumElevators() {
		return elevators.size();
	}
	
	public Elevator getElevator(int elevatorNumber) throws IllegalArgumentException {
		if (elevatorNumber < 0 || elevatorNumber >= elevators.size()) {
			throw new IllegalArgumentException("Invalid elevator number");
		}
		return elevators.get(elevatorNumber);
	}
	
	public int getNumFloors() {
		return floors.size();
	}
	
	public Floor getFloor(int floor) throws IllegalArgumentException {
		if (floor < 0 || floor >= floors.size()) {
			throw new IllegalArgumentException("Invalid floor number");
		}
		return floors.get(floor);
	}
}
