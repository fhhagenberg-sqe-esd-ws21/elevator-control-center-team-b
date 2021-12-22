package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.List;

public class ElevatorModel {
	
	private List<Elevator> elevators;
	private List<Floor> floors;
	private List<IModelObserver> observers;
	private String errorMessage;
	private boolean dataIsStale;
	
	
	public ElevatorModel(List<Elevator> elevators, List<Floor> floors) {
		this.elevators = elevators;
		this.floors = floors;
		observers = new ArrayList<>();
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
		observers.forEach((obs) -> obs.errorMessageUpdated(this));
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
		observers.forEach((obs) -> obs.dataIsStaleUpdated(this));
	}
	
	public void addModelObserver(IModelObserver observer) {
		observers.add(observer);
		for (Elevator el : elevators) {
			el.addModelObserver(observer);
		}
		for (Floor fl : floors) {
			fl.addModelObserver(observer);
		}
	}
	
	public List<Elevator> getElevators()
	{
		return elevators;
	}

}
