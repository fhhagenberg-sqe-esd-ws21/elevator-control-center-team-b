package at.fhhagenberg.sqe.model;

import java.util.ArrayList;

public class ElevatorModel {
	
	private ArrayList<Elevator> elevators;
	private ArrayList<Floor> floors;
	private ArrayList<ModelObserver> observers;
	private String errorMessage;
	private boolean dataIsStale;
	
	public ElevatorModel(ArrayList<Elevator> elevators, ArrayList<Floor> floors) {
		this.elevators = elevators;
		this.floors = floors;
		observers = new ArrayList<ModelObserver>();
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
	
	public void setErrorMessage(String message) {
		if (this.errorMessage != null && this.errorMessage.equals(message)) {
			return;
		}
		
		this.errorMessage = message;
		observers.forEach((obs) -> obs.ErrorMessageUpdated(this));
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public boolean isDataIsStale() {
		return dataIsStale;
	}

	public void setDataIsStale(boolean dataIsStale) {
		if (this.dataIsStale == dataIsStale) {
			return;
		}
		
		this.dataIsStale = dataIsStale;
		observers.forEach((obs) -> obs.DataIsStaleUpdated(this));
	}
	
	public void addModelObserver(ModelObserver observer) {
		observers.add(observer);
		for (Elevator el : elevators) {
			el.addModelObserver(observer);
		}
		for (Floor fl : floors) {
			fl.addModelObserver(observer);
		}
	}

}
