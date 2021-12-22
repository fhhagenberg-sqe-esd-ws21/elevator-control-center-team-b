package at.fhhagenberg.sqe.gui;

import java.io.IOException;
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
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppEnd2EndTest {

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
    public void start(Stage stage) throws IOException, HardwareConnectionException {
        assert(nrOfFloors > nrOfElevators); // we need more floors than elevators for this tests.

        ehmMock = Mockito.mock(ElevatorHardwareManager.class);

        // build up default mock
        Mockito.when(ehmMock.getFloorNum()).thenReturn(nrOfFloors);
        Mockito.when(ehmMock.getElevatorNum()).thenReturn(nrOfElevators);
        Mockito.when(ehmMock.getFloorHeight()).thenReturn(floorHeight);

        for (int i = 0; i < nrOfFloors; i++) {
            Mockito.when(ehmMock.getFloorButtonDown(i)).thenReturn(false);
            Mockito.when(ehmMock.getFloorButtonUp(i)).thenReturn(false);
        }

        for (int i = 0; i < nrOfElevators; i++) {
            Mockito.when(ehmMock.getCommittedDirection(i)).thenReturn(ElevatorDirection.UNCOMMITTED);
            Mockito.when(ehmMock.getElevatorDoorStatus(i)).thenReturn(ElevatorDoorStatus.CLOSED);

            Mockito.when(ehmMock.getTarget(i)).thenReturn(nrOfFloors-(i+1));
            Mockito.when(ehmMock.getElevatorPosition(i)).thenReturn(i*floorHeight);
            Mockito.when(ehmMock.getElevatorFloor(i)).thenReturn(i);

            for (int j = 0; j < nrOfFloors; j++) {
                Mockito.when(ehmMock.getElevatorButton(i, j)).thenReturn(false);
                Mockito.when(ehmMock.getServicesFloors(i, j)).thenReturn(false);
            }
        }

        // getElevatorAccel, getElevatorSpeed, getElevatorWeight, getElevatorCapacity

        var app = new App() {
            public ElevatorHardwareManager getHardwareConnection() {
                return ehmMock;
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
    void testTexts(FxRobot robot) {
        verifyThat("#GoButton", LabeledMatchers.hasText("Go"));
        // TODO..
    }

    @Test
    void testInitialState(FxRobot robot) throws InterruptedException {
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
    void testChangingState(FxRobot robot) throws InterruptedException, HardwareConnectionException {

        Mockito.when(ehmMock.getTarget(0)).thenReturn(0);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);       // wait for UI to fetch new data

        verifyThat("#TargetFloor", LabeledMatchers.hasText("0"));

        Mockito.when(ehmMock.getTarget(0)).thenReturn(1);
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
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