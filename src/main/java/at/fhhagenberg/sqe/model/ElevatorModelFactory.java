package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;

import java.util.ArrayList;

public class ElevatorModelFactory {
	
	public IElevatorManager manager;
	
	public ElevatorModelFactory(IElevatorManager manager) throws IllegalArgumentException {
		if (manager == null) {
			throw new IllegalArgumentException("Elevator manager must not be null");
		}
		this.manager = manager;
	}
	
	public ElevatorModel createModel() throws HardwareConnectionException {

		int numElevators = manager.getElevatorNum();
		int numFloors = manager.getFloorNum();
		
		ArrayList<Elevator> elevators = new ArrayList<Elevator>(numElevators);
		ArrayList<Floor> floors = new ArrayList<Floor>(numFloors);
		
		for (int i = 0; i < numElevators; i++) {
			elevators.add(new Elevator(i, numFloors));
		}
		
		for (int i = 0; i < numFloors; i++) {
			floors.add(new Floor(i));
		}
		
		return new ElevatorModel(elevators, floors);
	}
}
