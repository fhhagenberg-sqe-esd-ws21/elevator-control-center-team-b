package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.stage.Stage;
import mocks.ElevatorMock;
import sqelevator.IElevator;

import org.testfx.matcher.control.ListViewMatchers;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class AppEnd2EndTest {
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
		assert (nrOfFloors > nrOfElevators); // we need more floors than elevators for this tests.

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

		// getElevatorAccel, getElevatorSpeed, getElevatorWeight, getElevatorCapacity

		app = new App() {
			@Override
			public IElevatorManager getHardwareConnection() {
				try {
					return new ElevatorHardwareManager(ehmMock);
				} catch (IllegalArgumentException | HardwareConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

    private void goToTarget(FxRobot robot, int elevator, int floor) {
		Node el = robot.lookup("#elevatorsList .list-cell").nth(elevator).query();
		robot.clickOn(el);
		robot.clickOn("#FloorToGo");
		robot.write(Integer.toString(floor));
		robot.clickOn("#GoButton");
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
        for (int i = 0; i < nrOfElevators; i++) {
            verifyThat("#Position", LabeledMatchers.hasText(i * floorHeight + "m"));
            verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));
            verifyThat("#Payload", LabeledMatchers.hasText("0kg"));
            verifyThat("#Speed", LabeledMatchers.hasText("0m/s"));
            verifyThat("#Doors", LabeledMatchers.hasText("CLOSED"));
            verifyThat("#TargetFloor", LabeledMatchers.hasText(Integer.toString(i)));

            Node el = robot.lookup("#elevatorsList .list-cell").nth(i+1).query();
            robot.clickOn(el);
            waitForUpdate();
        }
    }


    @Test
    void testChangingState(FxRobot robot) throws HardwareConnectionException, RemoteException {
    	ehmMock.setTarget(0, 1);
        waitForUpdate();

        verifyThat("#TargetFloor", LabeledMatchers.hasText("1"));
    }

    @Test
    void testDoorState(FxRobot robot) throws HardwareConnectionException {
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSED"));
        
        ehmMock.setDoorStatus(0, IElevator.ELEVATOR_DOORS_OPENING);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPENING"));
        
        ehmMock.setDoorStatus(0, IElevator.ELEVATOR_DOORS_OPEN);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("OPEN"));
        
        ehmMock.setDoorStatus(0, IElevator.ELEVATOR_DOORS_CLOSING);
        waitForUpdate();
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));
        
        ehmMock.setDoorStatus(1, IElevator.ELEVATOR_DOORS_CLOSING);
        waitForUpdate();

        Node el1 = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el1);
        verifyThat("#Doors", LabeledMatchers.hasText("CLOSING"));
    }

    @Test
    void testElevatorDirection(FxRobot robot) throws HardwareConnectionException, RemoteException {
    	ehmMock.setTarget(0, 0);
    	waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));
        
        ehmMock.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("UP"));
        
        ehmMock.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("DOWN"));

        ehmMock.setCommittedDirection(1, IElevator.ELEVATOR_DIRECTION_UP);
        ehmMock.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_DOWN);
        waitForUpdate();

        Node el1 = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el1);
        verifyThat("#Direction", LabeledMatchers.hasText("UNCOMMITTED"));
        
        ehmMock.setTarget(2, 0);        
        Node el2 = robot.lookup("#elevatorsList .list-cell").nth(2).query();
        robot.clickOn(el2);
        waitForUpdate();
        verifyThat("#Direction", LabeledMatchers.hasText("DOWN"));
    }

    @Test
    void testElevatorPosition(FxRobot robot) throws HardwareConnectionException {
    	waitForUpdate();
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
    
    @Test
    void testInvalidFloorTargetDoesNotCrashApp(FxRobot robot) {    
        assertDoesNotThrow(() ->goToTarget(robot, 0, 100));
    }
    
    @Test 
    void testStringForFloorTargetDoesNotCrashApp(FxRobot robot) {
        assertDoesNotThrow( () -> robot.clickOn("#FloorToGo"));
        assertDoesNotThrow( () -> robot.write("hello"));
        assertDoesNotThrow( () -> robot.clickOn("#GoButton"));
    }
    
    @Test
    void testSettingInvalidTargetDoesNotUpdateRemote(FxRobot robot) throws RemoteException {
    	ehmMock.setTarget(0, 0);
    	
    	goToTarget(robot, 0, 1000);
    	
    	assertEquals(0, ehmMock.getTarget(0));
    }
    
    @Test
    void testSettingTargetFloorMovesElevator(FxRobot robot) throws RemoteException {
        goToTarget(robot, 0, 1);
        
        waitForUpdate();
        
        assertEquals(1, ehmMock.getTarget(0));
    }
    
    @Test
    void testCheckThatAutomaticButtonIsDisabled(FxRobot robot) {
    	Node button = robot.lookup("#AutomaticButton").query();
    	assertTrue(button.isDisabled());
    }
    
    @Test 
    void testSpeedIsUpdated(FxRobot robot) {
    	verifyThat("#Speed", LabeledMatchers.hasText("0m/s"));
    	ehmMock.setElevatorSpeed(0, 10);
    	waitForUpdate();
    	verifyThat("#Speed", LabeledMatchers.hasText("10m/s"));
    }
    
    @Test
    void testElevatorWeightIsUpdated(FxRobot robot) {
    	verifyThat("#Payload", LabeledMatchers.hasText("0kg"));
    	ehmMock.setElevatorWeight(0, 100);
    	waitForUpdate();
    	verifyThat("#Payload", LabeledMatchers.hasText("100kg"));
    }
    
    @Test 
    void testFloorsToStopAreUpdated(FxRobot robot) {
    	Node el = robot.lookup("#elevatorsList .list-cell").nth(1).query();
        robot.clickOn(el);
    	TableView<ElevatorProperties> tv = robot.lookup("#FloorTable").queryTableView();
    	
        for (int i = 0; i < nrOfFloors; i++) {
        	Circle c = (Circle)tv.getColumns().get(4).getCellData(i);
        	assertEquals(Color.GRAY, c.getFill());
        }
        
        ehmMock.setElevatorButtons(1, nrOfFloors-1, true);
        waitForUpdate();
        
        // elevators are sorted top down, so if elevator nrOfFloors-1 is set to
        // green, we need to access it via index 0
        for (int i = 1; i < nrOfFloors; i++) {
        	Circle c = (Circle)tv.getColumns().get(4).getCellData(i);
        	assertEquals(Color.GRAY, c.getFill());
        }
        
        Circle c = (Circle)tv.getColumns().get(4).getCellData(0);
    	assertEquals(Color.GREEN, c.getFill());
    }
    
    @Test
    void testEnteringNonNumbersForElevatorTargetDoesNotWork(FxRobot robot) {
    	TextField tf = robot.lookup("#FloorToGo").query();
    	robot.clickOn("#FloorToGo");
    	assertEquals("", tf.getText());
    	robot.write("hello");
    	assertEquals("", tf.getText());
    }
    
    @Test
    void testGoButtonIsInitiallyDisabled(FxRobot robot) {
    	Button goButton = robot.lookup("#GoButton").queryButton();    	
    	assertTrue(goButton.isDisabled());	
    }
    
    @Test 
    void testGoButtonEnablesWhenValidFloorIsEntered(FxRobot robot) {
    	Button goButton = robot.lookup("#GoButton").queryButton();
    	robot.clickOn("#FloorToGo");
    	robot.write("1");
    	waitForUpdate();
    	assertFalse(goButton.isDisabled());    		
    }

    @Test 
    void testGoButtonIsDisabledWhenInvalidFloorIsEntered(FxRobot robot) {
    	Button goButton = robot.lookup("#GoButton").queryButton();
    	robot.clickOn("#FloorToGo");
        robot.write("1000");
    	waitForUpdate();
    	assertTrue(goButton.isDisabled());
    }
    
    @Test
    void testTextFieldIsHighlightedRedWhenInvalidFloorIsEntered(FxRobot robot) {
    	TextField tf = robot.lookup("#FloorToGo").query();
    	robot.clickOn("#FloorToGo");
        robot.write("1");
    	waitForUpdate();
    	assertEquals(Color.BLACK, tf.getBorder().getStrokes().get(0).getTopStroke());
    	
    	robot.write("000");
    	waitForUpdate();
    	assertEquals(Color.RED, tf.getBorder().getStrokes().get(0).getTopStroke());    
    }
    
}