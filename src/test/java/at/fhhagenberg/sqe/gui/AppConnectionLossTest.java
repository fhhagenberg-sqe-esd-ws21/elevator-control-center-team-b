package at.fhhagenberg.sqe.gui;

import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import javafx.stage.Stage;
import mocks.ElevatorMock;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
class AppConnectionLossTest {
	private static final long TIMER_PERIOD = 10L; // milliseconds

	App app;

	ElevatorMock ehmMock;

	int nrOfFloors = 4;
	int nrOfElevators = 3;
	int floorHeight = 2; // m

	/**
	 * Will be called with {@code @Before} semantics, i. e. before each test method.
	 *
	 * @param stage - Will be injected by the test runner.
	 */
	@Start
	public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {
		ehmMock = new ElevatorMock(nrOfElevators, nrOfFloors);

		// build up default mock
		ehmMock.setFloorHeight(floorHeight);
		ehmMock.setConnected(true);

		for (int i = 0; i < nrOfFloors; i++) {
			ehmMock.setFloorButtonDown(i, false);
			ehmMock.setFloorButtonUp(i, false);
		}

		for (int i = 0; i < nrOfElevators; i++) {
			ehmMock.setCommittedDirection(i, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
			ehmMock.setDoorStatus(i, IElevator.ELEVATOR_DOORS_CLOSED);

			ehmMock.setTarget(i, i);
			ehmMock.setElevatorPosition(i, i * floorHeight);
			ehmMock.setElevatorFloor(i, i);

			for (int j = 0; j < nrOfFloors; j++) {
				ehmMock.setElevatorButtons(i, j, false);
				ehmMock.setServicesFloors(i, j, false);
			}
		}
		ehmMock.setConnected(false);
		// getElevatorAccel, getElevatorSpeed, getElevatorWeight, getElevatorCapacity

		app = new App() {
			@Override
			public IElevatorManager getHardwareConnection() {
				try {
					return new ElevatorHardwareManager(ehmMock);
				} catch (IllegalArgumentException | HardwareConnectionException e) {
					return null;
				}
            }
            @Override
            protected long getTimerPeriodMs() {
                return TIMER_PERIOD;
            }
        };
        app.start(stage);
    }

    @Stop
    void stop() throws Exception {
        app.stop();
    }
    
    private void waitForUpdate() {
        WaitForAsyncUtils.sleep(2*TIMER_PERIOD, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(2*TIMER_PERIOD, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();
    }
    
    @Test
    void testGUIShowsErrorScreenIfNotConnected(FxRobot robot) {
    	waitForUpdate();    	
    	verifyThat("#BackupErrorLabel", LabeledMatchers.hasText("Could not establish connection. Please contact your administrator."));
    }
    
    @Test
    void testGUIAutoconnectsIfConnectionIsLaterEstablished(FxRobot robot) {
    	for (int i = 0; i < 10; i++) {
    		waitForUpdate();
    	}
    	ehmMock.setConnected(true);
    	
    	waitForUpdate();
    	
    	verifyThat("#elevatorsList", ListViewMatchers.hasItems(nrOfElevators));
    }
    
    @Test 
    void testGUIShowsErrorScreenIfConnectionIsLost(FxRobot robot) {
    	ehmMock.setConnected(true);
    	waitForUpdate();

    	verifyThat("#elevatorsList", ListViewMatchers.hasItems(nrOfElevators));
    	for (int i = 0; i < 10; i++) {
    		waitForUpdate();
    	}
    	
    	ehmMock.setConnected(false);
    	
    	waitForUpdate();
    	
    	verifyThat("#BackupErrorLabel", LabeledMatchers.hasText("Could not establish connection. Please contact your administrator."));        
    }
}
