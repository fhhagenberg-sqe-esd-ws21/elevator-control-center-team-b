package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

import javafx.scene.Node;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppEnd2EndTest {
    private static final long TIMER_PERIOD = 10L; // milliseconds

    App app;

    @Mock
    ElevatorHardwareManager ehmMock;

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

        ehmMock = Mockito.mock(ElevatorHardwareManager.class);

        // build up default mock
        when(ehmMock.getFloorNum()).thenReturn(nrOfFloors);
        when(ehmMock.getElevatorNum()).thenReturn(nrOfElevators);
        when(ehmMock.getFloorHeight()).thenReturn(floorHeight);
        when(ehmMock.isConnected()).thenReturn(true);

        for (int i = 0; i < nrOfFloors; i++) {
            when(ehmMock.getFloorButtonDown(i)).thenReturn(false);
            when(ehmMock.getFloorButtonUp(i)).thenReturn(false);
        }

        for (int i = 0; i < nrOfElevators; i++) {
            when(ehmMock.getCommittedDirection(i)).thenReturn(ElevatorDirection.UNCOMMITTED);
            when(ehmMock.getElevatorDoorStatus(i)).thenReturn(ElevatorDoorStatus.CLOSED);

            when(ehmMock.getTarget(i)).thenReturn(nrOfFloors-(i+1));
            when(ehmMock.getElevatorPosition(i)).thenReturn(i*floorHeight);
            when(ehmMock.getElevatorFloor(i)).thenReturn(i);

            for (int j = 0; j < nrOfFloors; j++) {
                when(ehmMock.getElevatorButton(i, j)).thenReturn(false);
                when(ehmMock.getServicesFloors(i, j)).thenReturn(false);
            }
        }

        // getElevatorAccel, getElevatorSpeed, getElevatorWeight, getElevatorCapacity

        app = new App() {
            @Override
            public ElevatorHardwareManager getHardwareConnection() {
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
        when(ehmMock.getTarget(0)).thenReturn(1);
        waitForUpdate();

        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
    }

    @Test
    void testDoorState(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSED"));

        doReturn(ElevatorDoorStatus.OPENING).when(ehmMock).getElevatorDoorStatus(0);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPENING"));

        doReturn(ElevatorDoorStatus.OPEN).when(ehmMock).getElevatorDoorStatus(0);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPEN"));

        doReturn(ElevatorDoorStatus.CLOSING).when(ehmMock).getElevatorDoorStatus(0);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));

        doReturn(ElevatorDoorStatus.CLOSING).when(ehmMock).getElevatorDoorStatus(1);
        waitForUpdate();

        Node el1 = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el1);
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));
    }

    @Test
    void testElevatorDirection(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));

        doReturn(ElevatorDirection.UP).when(ehmMock).getCommittedDirection(0);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("UP"));

        doReturn(ElevatorDirection.DOWN).when(ehmMock).getCommittedDirection(0);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("DOWN"));

        doReturn(ElevatorDirection.UP).when(ehmMock).getCommittedDirection(1);
        doReturn(ElevatorDirection.DOWN).when(ehmMock).getCommittedDirection(2);
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
                doReturn(j * floorHeight).when(ehmMock).getElevatorPosition(i);
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