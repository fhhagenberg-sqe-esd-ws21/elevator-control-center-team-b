package at.fhhagenberg.sqe.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.backend.ElevatorConnectionManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import at.fhhagenberg.sqe.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private ElevatorModelUpdater updater;
	private Parent normalUI;
	private Parent errorUI;
	private Timer timer;
	private VBox mainUIContainer;
	
	private static final long TIMER_PERIOD = 100L; // milliseconds

	public IElevatorManager getHardwareConnection() throws MalformedURLException, RemoteException,
			IllegalArgumentException, NotBoundException, HardwareConnectionException {
		return ElevatorConnectionManager.getElevatorConnection();
	}

	protected ElevatorModel createModel(IElevatorManager manager)
			throws HardwareConnectionException {
		ElevatorModelFactory factory = new ElevatorModelFactory(manager);
		return factory.createModel();
	}

	protected ElevatorModelUpdater createElevatorModelUpdater(IElevatorManager manager, ElevatorModel model){
		return new ElevatorModelUpdater(manager, model);
	}

	protected long getTimerPeriodMs() {
		return TIMER_PERIOD;
	}

	protected Parent getBackupUI() {
		VBox root = new VBox();
		Label errorLabel = new Label("Could not establish connection. Please contact your administrator.");
		errorLabel.setId("BackupErrorLabel");
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
		IElevatorManager manager = getHardwareConnection();
		ElevatorModel model = createModel(manager);
		updater = createElevatorModelUpdater(manager, model);
		updater.update();
		
		EccLayout gui = new EccLayout(updater, model);
		EccGuiUpdater guiObserver = new EccGuiUpdater(gui);
		model.addModelObserver(guiObserver);    		
		var x = gui.getLayout();    
        return x;
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
					if (!mainUIContainer.getChildren().contains(normalUI)) {			
						Platform.runLater(() -> {
							mainUIContainer.getChildren().clear();
							mainUIContainer.getChildren().add(normalUI);
						});
					}
				}
				catch (Exception exc) {
					if (errorUI == null) {
						errorUI = getBackupUI();
					}
						
					if (!mainUIContainer.getChildren().contains(errorUI)) {
						Platform.runLater(() -> {							
							mainUIContainer.getChildren().clear();
							mainUIContainer.getChildren().add(errorUI);							
						});	
					}
				}
			}
        };      
    	
        mainUIContainer = new VBox();
        var scene = new Scene(mainUIContainer, 660, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        task.run();
        timer.scheduleAtFixedRate(task, 0, getTimerPeriodMs());
        mainUIContainer.getChildren().add(normalUI != null ? normalUI : errorUI);
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