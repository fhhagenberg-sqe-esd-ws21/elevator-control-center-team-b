package at.fhhagenberg.sqe.model;

public interface ModelObserver {
	
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
	
	public void ErrorMessageUpdated(ElevatorModel model);
	
	public void DataIsStaleUpdated(ElevatorModel model);
	
	public void FloorButtonUpPressedUpdated(Floor floor);
	
	public void FloorButtonDownPressedUpdated(Floor floor);

	public void FloorHeightUpdated(Floor floor);
}
