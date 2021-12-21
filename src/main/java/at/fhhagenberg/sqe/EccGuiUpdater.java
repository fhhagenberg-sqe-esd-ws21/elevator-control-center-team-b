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
	}

	@Override
	public void elevatorDirectionUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorAccelerationUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorFloorsPressedUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorPositionUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorSpeedUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorWeightUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorCapacityUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorFloorsToServiceUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void elevatorTargetFloorUpdated(Elevator elevator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorMessageUpdated(ElevatorModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataIsStaleUpdated(ElevatorModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void floorButtonUpPressedUpdated(Floor floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void floorButtonDownPressedUpdated(Floor floor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void floorHeightUpdated(Floor floor) {
		// TODO Auto-generated method stub

	}

}
