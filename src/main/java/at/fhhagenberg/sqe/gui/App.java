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
import at.fhhagenberg.sqe.backend.IElevator;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
class App extends Application {

	ElevatorHardwareManager iElevatorHM;

	private Timer timer;
	private TimerTask task;
	private static final long TIMER_PERIOD = 1000L; // milliseconds
	
	protected ElevatorHardwareManager getHardwareConnection() throws IllegalArgumentException, HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		if (iElevatorHM == null) {
			IElevator controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
			iElevatorHM = new ElevatorHardwareManager(controller);
		}
		return iElevatorHM;
	}

	protected ElevatorModel createModel() throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		IElevatorManager manager = getHardwareConnection();
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}

	protected ElevatorModelUpdater createElevatorModelUpdater(ElevatorModel model) throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		return new ElevatorModelUpdater(getHardwareConnection(), model);
	}

	protected long getTimerPeriodMs() {
		return TIMER_PERIOD;
	}

    @Override
    public void start(Stage stage) throws IOException, HardwareConnectionException, NotBoundException {
    	// TODO handle exceptions and display in GUI
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
        
        timer.scheduleAtFixedRate(task, 0, getTimerPeriodMs());
		
		
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