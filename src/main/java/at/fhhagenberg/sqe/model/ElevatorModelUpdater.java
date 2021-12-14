package at.fhhagenberg.sqe.model;

public class ElevatorModelUpdater implements ElevatorObserver {

	private IElevatorManager manager;
	private ElevatorModel model;
	
	public ElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) {
		this.manager = manager;
	}

	public void update() {
		for (int i = 0; i < model.getNumElevators(); i++) {
			Elevator el = model.getElevator(i);
			
			
		}
	}
	
	@Override
	public void ElevatorFloorUpdated(Elevator elevator) {}

	@Override
	public void ElevatorDoorStatusUpdated(Elevator elevator) {}

	@Override
	public void ElevatorDirectionUpdated(Elevator elevator) {}

	@Override
	public void ElevatorAccelerationUpdated(Elevator elevator) {}

	@Override
	public void ElevatorFloorsPressedUpdated(Elevator elevator) {}

	@Override
	public void ElevatorPositionUpdated(Elevator elevator) {}

	@Override
	public void ElevatorSpeedUpdated(Elevator elevator) {}

	@Override
	public void ElevatorWeightUpdated(Elevator elevator) {}

	@Override
	public void ElevatorCapacityUpdated(Elevator elevator) {}

	@Override
	public void ElevatorFloorsToServiceUpdated(Elevator elevator) {}

	@Override
	public void ElevatorTargetFloorUpdated(Elevator elevator) {}
	
}
