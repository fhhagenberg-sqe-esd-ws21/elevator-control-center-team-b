package at.fhhagenberg.sqe;

import java.io.IOException;

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
    	var root = (new EccLayout(null)).getLayout();
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}