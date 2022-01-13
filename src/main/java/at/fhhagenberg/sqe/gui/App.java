package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.backend.ElevatorConnectionManager;
import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class App extends Application {

	private Timer timer;
	private TimerTask task;
	private static final long TIMER_PERIOD = 1000L; // milliseconds

	public IElevatorManager getHardwareConnection() throws MalformedURLException, RemoteException, IllegalArgumentException, NotBoundException, HardwareConnectionException {		
		return ElevatorConnectionManager.getElevatorConnection();
	}
	
	protected ElevatorModel createModel(IElevatorManager manager) throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {		
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}


	protected ElevatorModelUpdater createElevatorModelUpdater(IElevatorManager manager, ElevatorModel model) throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		return new ElevatorModelUpdater(manager, model);
	}

	protected long getTimerPeriodMs() {
		return TIMER_PERIOD;
	}

    @Override
    public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {
    	// TODO handle exceptions and display in GUI
    	IElevatorManager manager = getHardwareConnection();
		ElevatorModel model = createModel(manager);
		ElevatorModelUpdater updater = createElevatorModelUpdater(manager, model);
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
        
        timer.scheduleAtFixedRate(task, 0, getTimerPeriodMs());
		
		
    	var root = gui.getLayout();

    	
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