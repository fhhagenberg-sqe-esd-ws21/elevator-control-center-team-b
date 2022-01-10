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
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private ElevatorModelUpdater updater;
	private Parent normalUI;
	private Parent errorUI;
	private Timer timer;
	
	private static final long TIMER_PERIOD = 100L; // milliseconds

	public ElevatorHardwareManager getHardwareConnection() throws MalformedURLException, RemoteException, IllegalArgumentException, NotBoundException, HardwareConnectionException {		
		return ElevatorConnectionManager.getElevatorConnection();
	}
	
	protected ElevatorModel createModel(ElevatorHardwareManager manager) throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {		
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}


	protected ElevatorModelUpdater createElevatorModelUpdater(ElevatorHardwareManager manager, ElevatorModel model) throws HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		return new ElevatorModelUpdater(manager, model);
	}

	protected long getTimerPeriodMs() {
		return TIMER_PERIOD;
	}

	protected Parent getBackupUI() {
		VBox root = new VBox();
		Label errorLabel = new Label("Could not establish connection. Please contact your administrator.");
		errorLabel.setWrapText(true);
		errorLabel.setStyle("-fx-font-size: 30; -fx-padding: 15");
		Label retryLabel = new Label("Retrying ...");
		retryLabel.setStyle("-fx-font-size: 24");
		retryLabel.setMaxWidth(Double.MAX_VALUE);
		retryLabel.setAlignment(Pos.CENTER);
		root.getChildren().addAll(errorLabel, retryLabel);
		return root;
	}
	
	protected Parent getNormalUI() throws MalformedURLException, RemoteException, IllegalArgumentException, NotBoundException, HardwareConnectionException {
		ElevatorHardwareManager manager = getHardwareConnection();
		ElevatorModel model = createModel(manager);
		updater = createElevatorModelUpdater(manager, model);
		updater.update();
		EccLayout gui = new EccLayout(updater, model);
		
		EccGuiUpdater guiObserver = new EccGuiUpdater(gui);
		model.addModelObserver(guiObserver);    		
        
        return gui.getLayout();
	}
	
	@Override
    public void start(Stage stage) {

    	timer = new Timer();  
    	
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				try {
					if (normalUI == null) {
						normalUI = getNormalUI();
					}
					
					updater.update();
										
					Platform.runLater(() -> {
						if (!stage.getScene().getRoot().equals(normalUI)) {
							stage.getScene().setRoot(normalUI);
						}
					});					
				}
				catch (Exception exc) {
					if (errorUI == null) {
						errorUI = getBackupUI();
					}
										
					Platform.runLater(() -> {
						if (!stage.getScene().getRoot().equals(errorUI)) {	
							stage.getScene().setRoot(errorUI);
						}
					});												
				}
			}
        };      
    	
        
        var scene = new Scene(new VBox(), 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        
        timer.scheduleAtFixedRate(task, getTimerPeriodMs(), getTimerPeriodMs());
        
        task.run();
        
        stage.getScene().setRoot(normalUI != null ? normalUI : errorUI);
        stage.show();   
    }

    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void stop() throws Exception {
    	if (timer != null) {
        	timer.cancel();
    	}
    	
    	super.stop();
    }

}