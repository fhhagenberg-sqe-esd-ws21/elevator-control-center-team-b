package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.backend.HardwareConnectionException;
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

    int nrOfFloors = 4;
    int nrOfElevators = 3;
    int floorHeight = 2;

    ElevatorModel testModel;
    ArrayList<Elevator> elevators = new ArrayList<>(nrOfElevators);
    ArrayList<Floor> floors = new ArrayList<>(nrOfFloors);


    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {
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
            public ElevatorModel createModel() {
                return testModel;
            }
            @Override
            public ElevatorModelUpdater createElevatorModelUpdater(ElevatorModel model) {
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
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#TargetFloor", LabeledMatchers.hasText("0"));

        elevators.get(0).setTargetFloor(1);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
    }

    @Test
    void testElevatorMoving(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        elevators.get(0).setFloor(0);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GREEN, rows.get(0).getPosition().getFill());
        assertEquals(Color.RED, rows.get(1).getPosition().getFill());
        assertEquals(Color.RED, rows.get(2).getPosition().getFill());
        assertEquals(Color.RED, rows.get(3).getPosition().getFill());

        elevators.get(0).setFloor(1);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.RED, rows.get(0).getPosition().getFill());
        assertEquals(Color.GREEN, rows.get(1).getPosition().getFill());
        assertEquals(Color.RED, rows.get(2).getPosition().getFill());
        assertEquals(Color.RED, rows.get(3).getPosition().getFill());

        elevators.get(0).setFloor(2);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.RED, rows.get(0).getPosition().getFill());
        assertEquals(Color.RED, rows.get(1).getPosition().getFill());
        assertEquals(Color.GREEN, rows.get(2).getPosition().getFill());
        assertEquals(Color.RED, rows.get(3).getPosition().getFill());

        elevators.get(0).setFloor(3);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.RED, rows.get(0).getPosition().getFill());
        assertEquals(Color.RED, rows.get(1).getPosition().getFill());
        assertEquals(Color.RED, rows.get(2).getPosition().getFill());
        assertEquals(Color.GREEN, rows.get(3).getPosition().getFill());
    }

    @Test
    void testElevatorCallingUp(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        floors.get(0).setButtonUpPressed(false);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GRAY, rows.get(0).getUp().getFill());

        floors.get(0).setButtonUpPressed(true);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GREEN, rows.get(0).getUp().getFill());
    }

    @Test
    void testElevatorCallingDown(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        floors.get(0).setButtonDownPressed(false);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GRAY, rows.get(0).getDown().getFill());

        floors.get(0).setButtonDownPressed(true);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GREEN, rows.get(0).getDown().getFill());
    }

    @Test
    @Disabled
    void testElevatorStopPlanned(FxRobot robot) {
        TableView<ElevatorProperties> floorTable = robot.lookup("#FloorTable").nth(0).query();
        ObservableList<ElevatorProperties> rows = floorTable.getItems();

        elevators.get(0).setTargetFloor(0);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GRAY, rows.get(0).getStopPlanned().getFill());

        elevators.get(0).setTargetFloor(2);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(Color.GREEN, rows.get(2).getStopPlanned().getFill());
    }

    @Test
    void testGUI(FxRobot robot) {

        elevators.get(0).setTargetFloor(1);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        elevators.get(0).setFloor(1);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        elevators.get(0).setTargetFloor(2);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        elevators.get(0).setFloor(2);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        elevators.get(0).setTargetFloor(0);
        elevators.get(0).setFloor(0);
        WaitForAsyncUtils.sleep(5, TimeUnit.SECONDS);

        assertTrue(true);
    }
}