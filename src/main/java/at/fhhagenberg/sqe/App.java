package at.fhhagenberg.sqe;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.ElevatorHardwareManager;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.ElevatorModelFactory;
import at.fhhagenberg.sqe.model.HardwareConnectionException;
import at.fhhagenberg.sqe.model.IElevator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private Timer timer;
	private TimerTask task;
	private long TIMER_PERIOD = 1000l; // milliseconds
	
	public ElevatorHardwareManager getHardwareConnection() throws IllegalArgumentException, HardwareConnectionException
	{
    	IElevator elevInterface = null;
    	ElevatorHardwareManager hwManager = new ElevatorHardwareManager(elevInterface);
    	return hwManager;
	}

    @Override
    public void start(Stage stage) throws IOException, HardwareConnectionException {
    	//Parent root = FXMLLoader.load(getClass().getResource("/elevator_control_center.fxml"));
    	
    	//todo:  thread updater
    	
    	//ElevatorModelFactory modelFactory = new ElevatorModelFactory(getHardwareConnection());    	
    	//ElevatorModel model = modelFactory.createModel();    	
    	var root = (new EccLayout(null ,null)).getLayout();
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        stage.show();
        
        timer = new Timer();
        task = new TimerTask() {

			@Override
			public void run() {
				//model.update();
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