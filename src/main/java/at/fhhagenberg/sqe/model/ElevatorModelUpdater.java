package at.fhhagenberg.sqe.model;

public class ElevatorModelUpdater {

	private IElevatorManager manager;
	private ElevatorModel model;
	
	public ElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) {
		this.manager = manager;
		this.model = model;
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

	public void updateElevatorTargetFloor(int elevatorNumber, int targetFloor) {
		try {
			manager.setTarget(elevatorNumber, targetFloor);
		}
		catch (Exception exc) {
			model.setErrorMessage(exc.getMessage());
		}
	}
	
}
