package at.fhhagenberg.sqe.model;

public class ElevatorModelUpdater implements ModelObserver {

	private IElevatorManager manager;
	private ElevatorModel model;
	
	public ElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) {
		this.manager = manager;
		this.model = model;
		
		model.addModelObserver(this);
	}
	
	public void update() {
		
		model.setDataIsStale(false);
		try {
			
			for (int elevatorNumber = 0; elevatorNumber < model.getNumElevators(); elevatorNumber++) {
				Elevator el = model.getElevator(elevatorNumber);
					el.setFloor(manager.getElevatorFloor(elevatorNumber));
					el.setDoorStatus(manager.getElevatorDoorStatus(elevatorNumber));
					el.setDirection(manager.getCommittedDirection(elevatorNumber));
					el.setAcceleration(manager.getElevatorAccel(elevatorNumber));
					el.setPosition(manager.getElevatorPosition(elevatorNumber));
					el.setSpeed(manager.getElevatorSpeed(elevatorNumber));
					el.setWeight(manager.getElevatorWeight(elevatorNumber));
					el.setCapacity(manager.getElevatorCapacity(elevatorNumber));
					el.setTargetFloor(manager.getTarget(elevatorNumber));
					
					for (int floorNumber = 0; floorNumber < model.getNumFloors(); floorNumber++) {
						el.setFloorIsPressed(floorNumber, manager.getElevatorButton(elevatorNumber, floorNumber));
						el.setFloorToService(floorNumber, manager.getServicesFloors(elevatorNumber, floorNumber));
					}		
			}
			
			for (int floorNumber = 0; floorNumber < model.getNumFloors(); floorNumber++) {
				Floor floor = model.getFloor(floorNumber);
				
					floor.setButtonUpPressed(manager.getFloorButtonUp(floorNumber));
					floor.setButtonDownPressed(manager.getFloorButtonDown(floorNumber));
					floor.setFloorHeight(manager.getFloorHeight());	
				
			}
		}
		catch (Exception exc) {
			model.setErrorMessage(exc.getMessage());
			model.setDataIsStale(true);
		}
	}
	
	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorFloorUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorDoorStatusUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorDirectionUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorAccelerationUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorFloorsPressedUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorPositionUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorSpeedUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorWeightUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorCapacityUpdated(Elevator elevator) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ElevatorFloorsToServiceUpdated(Elevator elevator) {}

	@Override
	public void ElevatorTargetFloorUpdated(Elevator elevator) {
		try {
			manager.setTarget(elevator.getElevatorNumber(), elevator.getTargetFloor());
		}
		catch (Exception exc) {
			model.setErrorMessage(exc.getMessage());
		}
	}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void ErrorMessageUpdated(ElevatorModel model) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void DataIsStaleUpdated(ElevatorModel model) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void FloorButtonUpPressedUpdated(Floor floor) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void FloorButtonDownPressedUpdated(Floor floor) {}

	/**
	 * Unnecessary for the Model Updater
	 */
	@Override
	public void FloorHeightUpdated(Floor floor) {}
	
}
