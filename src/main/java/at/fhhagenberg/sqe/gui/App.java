package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.*;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sqelevator.IElevator;

import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * JavaFX App
 */
public class App extends Application {

	private Timer timer;
	private TimerTask task;
	private long TIMER_PERIOD = 1000l; // milliseconds
	
	public ElevatorHardwareManager getHardwareConnection() {
		try {
			IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
			return new ElevatorHardwareManager(controller);
		} catch (Exception e) {
			return null;
		}		
	}

	public ElevatorModel createModel() throws HardwareConnectionException {
		IElevatorManager manager = getHardwareConnection();
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}

	public ElevatorModelUpdater createElevatorModelUpdater(ElevatorModel model) {
		return new ElevatorModelUpdater(getHardwareConnection(), model);
	}

    @Override
    public void start(Stage stage) throws HardwareConnectionException {
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