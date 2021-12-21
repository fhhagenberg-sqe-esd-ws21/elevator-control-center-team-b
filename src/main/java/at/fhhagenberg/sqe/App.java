package at.fhhagenberg.sqe;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.*;
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
	IElevator iElevatorMock;

	private Timer timer;
	private TimerTask task;
	private long TIMER_PERIOD = 1000l; // milliseconds
	
	public ElevatorHardwareManager getHardwareConnection() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		if (iElevatorMock == null) {
			iElevatorMock = Mockito.mock(IElevator.class);
			Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
			Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(5);
			Mockito.when(iElevatorMock.getElevatorPosition(0)).thenReturn(1, 2, 3, 4);
			// TODO..
		}
		return new ElevatorHardwareManager(iElevatorMock);
	}

    @Override
    public void start(Stage stage) throws IOException, HardwareConnectionException {
    	//Parent root = FXMLLoader.load(getClass().getResource("/elevator_control_center.fxml"));
    	

		IElevatorManager manager = getHardwareConnection();
    	ElevatorModelFactory modelFactory = new ElevatorModelFactory(manager);
    	ElevatorModel model = modelFactory.createModel();
		ElevatorModelUpdater updater = new ElevatorModelUpdater(manager, model);
    	var root = (new EccLayout(updater, model)).getLayout();
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        stage.show();
        
        timer = new Timer();
        task = new TimerTask() {

			@Override
			public void run() {
				updater.update();
			}
        };
        
        timer.scheduleAtFixedRate(task, 0, TIMER_PERIOD);
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