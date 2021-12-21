package at.fhhagenberg.sqe;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.*;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * JavaFX App
 */
public class App extends Application {

	@Mock
	ElevatorHardwareManager iElevatorMock;

	private Timer timer;
	private TimerTask task;
	private long TIMER_PERIOD = 1000l; // milliseconds
	
	public ElevatorHardwareManager getHardwareConnection() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		if (iElevatorMock == null) {
			iElevatorMock = Mockito.mock(ElevatorHardwareManager.class);
			Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);
			Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
			Mockito.when(iElevatorMock.getElevatorPosition(0)).thenReturn(1, 2,3, 4);
			Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(ElevatorDirection.Up);
			Mockito.when(iElevatorMock.getCommittedDirection(1)).thenReturn(ElevatorDirection.Down);
			Mockito.when(iElevatorMock.getCommittedDirection(2)).thenReturn(ElevatorDirection.Uncommitted);
			Mockito.when(iElevatorMock.getElevatorButton(0, 1)).thenReturn(true);
			Mockito.when(iElevatorMock.getElevatorButton(0, 0)).thenReturn(false);
			Mockito.when(iElevatorMock.getElevatorDoorStatus(0)).thenReturn(ElevatorDoorStatus.Closed);
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

			
			// TODO..
		}
		return iElevatorMock;
	}

	public ElevatorModel createModel() throws HardwareConnectionException, RemoteException {
		IElevatorManager manager = getHardwareConnection();
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}

	public ElevatorModelUpdater createElevatorModelUpdater(ElevatorModel model) throws HardwareConnectionException, RemoteException {
		return new ElevatorModelUpdater(getHardwareConnection(), model);
	}

    @Override
    public void start(Stage stage) throws IOException, HardwareConnectionException {
    	ElevatorModel model = createModel();
		ElevatorModelUpdater updater = createElevatorModelUpdater(model);
		updater.update();
		EccLayout gui = new EccLayout(updater, model);

		EccGuiUpdater guiObserver = new EccGuiUpdater(gui);
		model.addModelObserver(guiObserver);

		timer = new Timer();
        task = new TimerTask() {

			@Override
			public void run() {
				updater.update();
			}
        };
        
        timer.scheduleAtFixedRate(task, 0, TIMER_PERIOD);
		
		
    	var root = (gui).getLayout();

    	
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        stage.show();   
    }

    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void stop() throws Exception {
    	task.cancel();
    	timer.cancel();
    	super.stop();
    }

}