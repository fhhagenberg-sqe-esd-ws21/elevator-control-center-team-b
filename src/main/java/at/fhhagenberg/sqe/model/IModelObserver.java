package at.fhhagenberg.sqe.model;

public interface IModelObserver {
	
	void elevatorFloorUpdated(Elevator elevator);

	void elevatorDoorStatusUpdated(Elevator elevator);
	
	void elevatorDirectionUpdated(Elevator elevator);
	
	void elevatorAccelerationUpdated(Elevator elevator);
	
	void elevatorPositionUpdated(Elevator elevator);
	
	void elevatorSpeedUpdated(Elevator elevator);
	
	void elevatorWeightUpdated(Elevator elevator);
	
	void elevatorCapacityUpdated(Elevator elevator);
	
	void elevatorFloorsToServiceUpdated(Elevator elevator);
	
	void elevatorTargetFloorUpdated(Elevator elevator);
	
	void errorMessageUpdated(ElevatorModel model);
	
	void floorButtonUpPressedUpdated(Floor floor);
	
	void floorButtonDownPressedUpdated(Floor floor);

	void floorHeightUpdated(Floor floor);

	void elevatorFloorStopRequestedUpdated(Elevator elevator);
}
