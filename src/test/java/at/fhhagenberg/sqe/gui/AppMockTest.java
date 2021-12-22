package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


@ExtendWith(ApplicationExtension.class)
class AppMockTest {

	/**
	 * Will be called with {@code @Before} semantics, i. e. before each test method.
	 *
	 * @param stage - Will be injected by the test runner.
	 */
	@Start
	public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {

		var app = new App() {
			@Mock
			ElevatorHardwareManager iElevatorMock;

			public ElevatorHardwareManager getHardwareConnection() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
				if (iElevatorMock == null) {
					iElevatorMock = Mockito.mock(ElevatorHardwareManager.class);

					Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);
					Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
					Mockito.when(iElevatorMock.getElevatorPosition(0)).thenReturn(1, 2,3, 4);
					Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(ElevatorDirection.UP);
					Mockito.when(iElevatorMock.getCommittedDirection(1)).thenReturn(ElevatorDirection.DOWN);
					Mockito.when(iElevatorMock.getCommittedDirection(2)).thenReturn(ElevatorDirection.UNCOMMITTED);
					Mockito.when(iElevatorMock.getElevatorButton(0, 1)).thenReturn(true);
					Mockito.when(iElevatorMock.getElevatorButton(0, 0)).thenReturn(false);
					Mockito.when(iElevatorMock.getElevatorDoorStatus(0)).thenReturn(ElevatorDoorStatus.CLOSED);
					Mockito.when(iElevatorMock.getElevatorFloor(0)).thenReturn(0);
					Mockito.when(iElevatorMock.getFloorButtonDown(0)).thenReturn(true);
					Mockito.when(iElevatorMock.getFloorButtonDown(1)).thenReturn(false);
					Mockito.when(iElevatorMock.getFloorButtonUp(0)).thenReturn(false);
					Mockito.when(iElevatorMock.getFloorButtonUp(1)).thenReturn(true);
					Mockito.when(iElevatorMock.getServicesFloors(0, 0)).thenReturn(true);
					Mockito.when(iElevatorMock.getServicesFloors(0, 1)).thenReturn(false);
					Mockito.when(iElevatorMock.getTarget(0)).thenReturn(0);
					Mockito.when(iElevatorMock.getTarget(1)).thenReturn(0);
					Mockito.when(iElevatorMock.getTarget(2)).thenReturn(1);
				}
				return iElevatorMock;
			}
		};
		app.start(stage);
	}

	@Disabled
	@Test
	void runForeverTest(FxRobot robot) throws Throwable {
		// run forever..
		while(true) {
			WaitForAsyncUtils.checkException();
		}
	}
}