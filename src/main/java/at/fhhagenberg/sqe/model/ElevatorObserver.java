package at.fhhagenberg.sqe.model;

import java.util.ArrayList;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

public interface ElevatorObserver {
	
	public void ElevatorFloorUpdated(Elevator elevator);

	public void ElevatorDoorStatusUpdated(Elevator elevator);
	
	public void ElevatorDirectionUpdated(Elevator elevator);
	
	public void ElevatorAccelerationUpdated(Elevator elevator);
	
	public void ElevatorFloorsPressedUpdated(Elevator elevator);
	
	public void ElevatorPositionUpdated(Elevator elevator);
	
	public void ElevatorSpeedUpdated(Elevator elevator);
	
	public void ElevatorWeightUpdated(Elevator elevator);
	
	public void ElevatorCapacityUpdated(Elevator elevator);
	
	public void ElevatorFloorsToServiceUpdated(Elevator elevator);
	
	public void ElevatorTargetFloorUpdated(Elevator elevator);
	
}
