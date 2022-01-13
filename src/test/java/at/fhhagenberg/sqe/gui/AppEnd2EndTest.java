package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

import javafx.scene.Node;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import mocks.ElevatorManagerMock;

import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppEnd2EndTest {
    private static final long TIMER_PERIOD = 10L; // milliseconds

    App app;
    
    ElevatorManagerMock ehmMock;

    int nrOfFloors = 4;
    int nrOfElevators = 3;
    int floorHeight = 2;    // m

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {
        assert(nrOfFloors > nrOfElevators); // we need more floors than elevators for this tests.

        ehmMock = new ElevatorManagerMock(nrOfElevators, nrOfFloors);
        
        // build up default mock
        ehmMock.setFloorHeight(floorHeight);
        ehmMock.setConnected(true);

        for (int i = 0; i < nrOfFloors; i++) {
        	ehmMock.setFloorButtonDown(i, false);
        	ehmMock.setFloorButtonUp(i, false);
        }

        for (int i = 0; i < nrOfElevators; i++) {
        	ehmMock.setCommittedDirection(i, ElevatorDirection.UNCOMMITTED);
        	ehmMock.setDoorStatus(i, ElevatorDoorStatus.CLOSED);
        	
        	ehmMock.setTarget(i, nrOfFloors-(i+1));
        	ehmMock.setElevatorPosition(i, i*floorHeight);
        	ehmMock.setElevatorFloor(i, i);

            for (int j = 0; j < nrOfFloors; j++) {
            	ehmMock.setElevatorButtons(i, j, false);
            	ehmMock.setServicesFloors(i, j, false);
            }
        }

        // getElevatorAccel, getElevatorSpeed, getElevatorWeight, getElevatorCapacity

        app = new App() {
            @Override
            public IElevatorManager getHardwareConnection() {
                return ehmMock;
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

    void waitForUpdate() {
        WaitForAsyncUtils.sleep(2*TIMER_PERIOD, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();
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
    void testTexts(FxRobot robot) {
        verifyThat("#GoButton", LabeledMatchers.hasText("Go"));
        // TODO..
    }

    @Test
    void testInitialState(FxRobot robot) {
        //verifyThat("#ErrorBox", TextInputControlMatchers.hasText(""));

        for (int i = 0; i < nrOfElevators; i++) {
            verifyThat("#Position", LabeledMatchers.hasText(i * floorHeight + "m"));
            verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));
            verifyThat("#Payload", LabeledMatchers.hasText("0kg"));
            verifyThat("#Speed", LabeledMatchers.hasText("0m/s"));
            verifyThat("#Doors", LabeledMatchers.hasText("CLOSED"));
            verifyThat("#TargetFloor", LabeledMatchers.hasText(nrOfFloors-(i+1) + ""));

            Node el = robot.lookup("#elevatorsList .list-cell").nth(i+1).query();
            robot.clickOn(el);
        }

        //ListView list = robot.lookup("#elevatorsList").query();
        //assert(list.getItems().size() == nrOfElevators);
    }


    @Test
    void testChangingState(FxRobot robot) throws HardwareConnectionException {
    	ehmMock.setTarget(0, 1);
        waitForUpdate();

        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
    }

    @Test
    void testDoorState(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSED"));
        
        ehmMock.setDoorStatus(0, ElevatorDoorStatus.OPENING);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPENING"));
        
        ehmMock.setDoorStatus(0, ElevatorDoorStatus.OPEN);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPEN"));
        
        ehmMock.setDoorStatus(0, ElevatorDoorStatus.CLOSING);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));
        
        ehmMock.setDoorStatus(1, ElevatorDoorStatus.CLOSING);
        waitForUpdate();

        Node el1 = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el1);
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));
    }

    @Test
    void testElevatorDirection(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));
        
        ehmMock.setCommittedDirection(0, ElevatorDirection.UP);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("UP"));
        
        ehmMock.setCommittedDirection(0, ElevatorDirection.DOWN);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("DOWN"));

        ehmMock.setCommittedDirection(1, ElevatorDirection.UP);
        ehmMock.setCommittedDirection(2, ElevatorDirection.DOWN);
        waitForUpdate();

        Node el1 = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el1);
        verifyThat("#Direction", LabeledMatchers.hasText("UP"));

        Node el2 = robot.lookup("#elevatorsList .list-cell").nth(2).query();
        robot.clickOn(el2);
        verifyThat("#Direction", LabeledMatchers.hasText("DOWN"));
    }

    @Test
    void testElevatorPosition(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Position", LabeledMatchers.hasText("0m"));

        for (int i = 0; i < nrOfElevators; i++) {
            Node el = robot.lookup("#elevatorsList .list-cell").nth(i).query();
            robot.clickOn(el);
            for (int j = 0; j < nrOfFloors; j++) {
            	ehmMock.setElevatorPosition(i, j*floorHeight);
                waitForUpdate();
                verifyThat("#Position", LabeledMatchers.hasText(j * floorHeight + "m"));
            }
        }
    }

    @Disabled
    @Test
    void testButtonClick(FxRobot robot) throws TimeoutException {
        robot.clickOn("#FloorTable");
        //WaitForAsyncUtils.waitForFxEvents();

        // or (lookup by css class):
        //verifyThat(".button", LabeledMatchers.hasText("Go!"));

        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {

                return false;
            }
        });
    }
}