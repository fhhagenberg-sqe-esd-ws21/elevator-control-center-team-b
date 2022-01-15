package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppModelMockTest {
	private static final long TIMER_PERIOD = 10L; // milliseconds
	
    int nrOfFloors = 4;
    int nrOfElevators = 3;
    int floorHeight = 2;

    ElevatorModel testModel;
    ArrayList<Elevator> elevators = new ArrayList<>(nrOfElevators);
    ArrayList<Floor> floors = new ArrayList<>(nrOfFloors);


    private ElevatorProperties getFloorInTableView(ObservableList<ElevatorProperties> rows, int floorNumber) {
    	for (int i = 0; i < rows.size(); i++) {
        	if (rows.get(i).getFloor() == floorNumber) {
        		return rows.get(i);
        	}
        }   
    	return null;
    }
    
    void waitForUpdate() {
        WaitForAsyncUtils.sleep(2*TIMER_PERIOD, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();
    }
    
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        assert(nrOfFloors > nrOfElevators); // we need more floors than elevators for this tests.

        elevators = new ArrayList<>(nrOfElevators);
        floors = new ArrayList<>(nrOfFloors);

        for (int i = 0; i < nrOfElevators; i++) {
            elevators.add(new Elevator(i, nrOfFloors));
        }

        for (int i = 0; i < nrOfFloors; i++) {
            floors.add(new Floor(i));
        }

        testModel = new ElevatorModel(elevators, floors);

        var app = new App() {
        	@Override
            public ElevatorHardwareManager getHardwareConnection() {
                return null;
            }
            @Override
            public ElevatorModel createModel(IElevatorManager manager) {
                return testModel;
            }
            
            @Override
            protected long getTimerPeriodMs() {
            	return TIMER_PERIOD;
            }
            
            @Override
            public ElevatorModelUpdater createElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) {
                return new ElevatorModelUpdater(null, model) {
                    @Override
                    public void update() {
                        // do nothing..
                    }
                };
            }
        };
        app.start(stage);
    }

    @Test
    void testElevatorListAmount(FxRobot robot) {
        verifyThat("#elevatorsList", ListViewMatchers.hasItems(nrOfElevators));
    }

    @Test
    void testFloorAmount(FxRobot robot) {
        verifyThat("#FloorTable", TableViewMatchers.hasNumRows(nrOfFloors));
    }

    @Test
    void testChangingState(FxRobot robot) {
        elevators.get(0).setTargetFloor(0);
        waitForUpdate();
        verifyThat("#TargetFloor", LabeledMatchers.hasText("0"));

        elevators.get(0).setTargetFloor(1);
        waitForUpdate();
        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
    }

    @Test
    void testElevatorMoving(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        elevators.get(0).setFloor(0);
        waitForUpdate();
        assertEquals(Color.GREEN, getFloorInTableView(rows, 0).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 1).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 2).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 3).getPosition().getFill());

        elevators.get(0).setFloor(1);
        waitForUpdate();
        assertEquals(Color.RED, getFloorInTableView(rows, 0).getPosition().getFill());
        assertEquals(Color.GREEN, getFloorInTableView(rows, 1).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 2).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 3).getPosition().getFill());

        elevators.get(0).setFloor(2);
        waitForUpdate();
        assertEquals(Color.RED, getFloorInTableView(rows, 0).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 1).getPosition().getFill());
        assertEquals(Color.GREEN, getFloorInTableView(rows, 2).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 3).getPosition().getFill());

        elevators.get(0).setFloor(3);
        waitForUpdate();
        assertEquals(Color.RED, getFloorInTableView(rows, 0).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 1).getPosition().getFill());
        assertEquals(Color.RED, getFloorInTableView(rows, 2).getPosition().getFill());
        assertEquals(Color.GREEN, getFloorInTableView(rows, 3).getPosition().getFill());
    }
    
    @Test
    void testElevatorCallingUp(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        floors.get(0).setButtonUpPressed(false);
        waitForUpdate();
        assertEquals(Color.GRAY, getFloorInTableView(rows, 0).getUp().getFill());

        floors.get(0).setButtonUpPressed(true);
        waitForUpdate();

		assertEquals(Color.GREEN, getFloorInTableView(rows, 0).getUp().getFill());
    }

    @Test
    void testElevatorCallingDown(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        floors.get(0).setButtonDownPressed(false);
        waitForUpdate();
        assertEquals(Color.GRAY, getFloorInTableView(rows, 0).getDown().getFill());

        floors.get(0).setButtonDownPressed(true);
        waitForUpdate();
        assertEquals(Color.GREEN, getFloorInTableView(rows, 0).getDown().getFill());
    }

    @Test
    void testElevatorStopPlanned(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        elevators.get(0).setFloorStopRequested(0, false);
        waitForUpdate();
        assertEquals(Color.GRAY, getFloorInTableView(rows, 0).getStopPlanned().getFill());

        elevators.get(0).setFloorStopRequested(2, true);
        waitForUpdate();
        assertEquals(Color.GREEN, getFloorInTableView(rows, 2).getStopPlanned().getFill());
    }
}