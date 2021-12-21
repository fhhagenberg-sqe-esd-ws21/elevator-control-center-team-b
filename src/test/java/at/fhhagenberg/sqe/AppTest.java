package at.fhhagenberg.sqe;

import java.io.IOException;

import at.fhhagenberg.sqe.model.ElevatorHardwareManager;
import at.fhhagenberg.sqe.model.HardwareConnectionException;
import at.fhhagenberg.sqe.model.IElevator;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;

import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppTest {

    @Mock
    IElevator iElevatorMock;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws IOException, HardwareConnectionException {

        iElevatorMock = Mockito.mock(IElevator.class);
        // build up the mock
        Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
        Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(5);
        Mockito.when(iElevatorMock.getElevatorPosition(0)).thenReturn(1, 2,3, 4);
		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
		Mockito.when(iElevatorMock.getElevatorButton(0, 1)).thenReturn(true);
		Mockito.when(iElevatorMock.getElevatorButton(0, 0)).thenReturn(false);
		Mockito.when(iElevatorMock.getElevatorDoorStatus(0)).thenReturn(IElevator.ELEVATOR_DOORS_CLOSING);
		Mockito.when(iElevatorMock.getElevatorFloor(0)).thenReturn(0);
		Mockito.when(iElevatorMock.getFloorButtonDown(0)).thenReturn(true);
		Mockito.when(iElevatorMock.getFloorButtonDown(1)).thenReturn(false);
		Mockito.when(iElevatorMock.getFloorButtonUp(0)).thenReturn(false);
		Mockito.when(iElevatorMock.getFloorButtonUp(1)).thenReturn(true);
		Mockito.when(iElevatorMock.getServicesFloors(0, 0)).thenReturn(true);
		Mockito.when(iElevatorMock.getServicesFloors(0, 1)).thenReturn(false);
		Mockito.when(iElevatorMock.getTarget(0)).thenReturn(0,1);

        ElevatorHardwareManager ehm = new ElevatorHardwareManager(iElevatorMock);

        var app = new App() {
            public ElevatorHardwareManager getHardwareConnection() {
                return ehm;
            }
        };
        app.start(stage);
    }

    @Test
    void testElevatorListAmount(FxRobot robot) {
        verifyThat("#elevatorsList", ListViewMatchers.hasItems(5));
    }

    @Test
    void testFloorAmount(FxRobot robot) {
        //robot.clickOn("#elevatorsList");

        verifyThat("#FloorTable", TableViewMatchers.hasNumRows(3));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Disabled
    @Test
    void testButtonClick(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // or (lookup by css class):
        verifyThat(".button", LabeledMatchers.hasText("Clicked!"));
    }
}