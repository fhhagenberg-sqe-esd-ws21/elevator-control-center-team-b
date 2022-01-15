package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IModelObserver;
import javafx.application.Platform;

public class EccGuiUpdater implements IModelObserver {

	private EccLayout layout;

	EccGuiUpdater(EccLayout gui) {
		layout = gui;
	}

	@Override
	public void elevatorFloorUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setFloor(elevator) );
	}

	@Override
	public void elevatorDoorStatusUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setDoorState((elevator)) );
	}

	@Override
	public void elevatorDirectionUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setDirection((elevator)) );
	}

	@Override
	public void elevatorAccelerationUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setAcceleration((elevator)) );
	}

	public void elevatorPositionUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setPosition((elevator)) );
	}

	@Override
	public void elevatorSpeedUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setSpeed((elevator)) );
	}

	@Override
	public void elevatorWeightUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setWeight((elevator)) );
	}

	@Override
	public void elevatorCapacityUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setCapacity((elevator)) );
	}

	@Override
	public void elevatorFloorsToServiceUpdated(Elevator elevator) {
	}

	@Override
	public void elevatorTargetFloorUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setTargetFloor((elevator)) );
	}

	@Override
	public void errorMessageUpdated(ElevatorModel model) {
		Platform.runLater(() -> layout.appendErrorMessage(model) );
	}
	
	@Override
	public void floorButtonUpPressedUpdated(Floor floor) {
		Platform.runLater(() -> layout.setButtonUpPressed(floor) );

	}

	@Override
	public void floorButtonDownPressedUpdated(Floor floor) {
		Platform.runLater(() -> layout.setButtonDownPressed(floor) );
	}

	@Override
	public void floorHeightUpdated(Floor floor) {
		Platform.runLater(() -> layout.setHeight(floor) );

	}

	@Override
	public void elevatorFloorStopRequestedUpdated(Elevator elevator) {
		Platform.runLater(() -> layout.setFloorsToStop(elevator) );
	}

}
