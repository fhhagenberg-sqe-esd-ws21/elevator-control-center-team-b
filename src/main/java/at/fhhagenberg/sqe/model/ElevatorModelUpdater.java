package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.backend.ElevatorConnectionManager;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;

public class ElevatorModelUpdater {

	private IElevatorManager manager;
	private ElevatorModel model;

	public ElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) {
		this.manager = manager;
		this.model = model;
	}

	public void update() {
		try {
			if (!manager.isConnected()) {
				manager = ElevatorConnectionManager.getElevatorConnection();
			}

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
					el.setFloorStopRequested(floorNumber, manager.getElevatorButton(elevatorNumber, floorNumber));
				}
			}

			for (int floorNumber = 0; floorNumber < model.getNumFloors(); floorNumber++) {
				Floor floor = model.getFloor(floorNumber);

				floor.setButtonUpPressed(manager.getFloorButtonUp(floorNumber));
				floor.setButtonDownPressed(manager.getFloorButtonDown(floorNumber));
				floor.setFloorHeight(manager.getFloorHeight());
			}
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
			model.setErrorMessage(exc.getMessage());
		}
	}

	public void updateElevatorTargetFloor(int elevatorNumber, int targetFloor) {
		try {
			manager.setTarget(elevatorNumber, targetFloor);
		} catch (Exception exc) {
			model.setErrorMessage(exc.getMessage());
		}
	}

	public void updateElevatorDirection(int elevatorNumber, ElevatorDirection dir) {
		try {
			manager.setCommittedDirection(elevatorNumber, dir);
		} catch (Exception exc) {
			model.setErrorMessage(exc.getMessage());
		}
	}

}
