package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.ModelObserver;
import javafx.application.Platform;

public class EccGuiUpdater implements ModelObserver {

	private EccLayout layout;

	EccGuiUpdater(EccLayout gui) {
		layout = gui;
	}

	@Override
	public void elevatorFloorUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setFloor(elevator);} );
	}

	@Override
	public void elevatorDoorStatusUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setDoorState((elevator));} );
	}

	@Override
	public void elevatorDirectionUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setDirection((elevator));} );
	}

	@Override
	public void elevatorAccelerationUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setAcceleration((elevator));} );
	}

	@Override
	public void elevatorFloorsPressedUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setFloorsPressed((elevator));} );

	}

	@Override
	public void elevatorPositionUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setPosition((elevator));} );
	}

	@Override
	public void elevatorSpeedUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setSpeed((elevator));} );
	}

	@Override
	public void elevatorWeightUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setWeight((elevator));} );
	}

	@Override
	public void elevatorCapacityUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setCapacity((elevator));} );
	}

	@Override
	public void elevatorFloorsToServiceUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setFloorsToService((elevator));} );		
	}

	@Override
	public void elevatorTargetFloorUpdated(Elevator elevator) {
		Platform.runLater(() -> {layout.setTargetFloor((elevator));} );
	}

	@Override
	public void errorMessageUpdated(ElevatorModel model) {
		Platform.runLater(() -> {layout.appendErrorMessage(model);} );
	}

	@Override
	public void dataIsStaleUpdated(ElevatorModel model) {
	}

	@Override
	public void floorButtonUpPressedUpdated(Floor floor) {
		Platform.runLater(() -> {layout.setButtonUpPressed(floor);} );

	}

	@Override
	public void floorButtonDownPressedUpdated(Floor floor) {
		Platform.runLater(() -> {layout.setButtonDownPressed(floor);} );
	}

	@Override
	public void floorHeightUpdated(Floor floor) {
		Platform.runLater(() -> {layout.setHeight(floor);} );

	}

}
