package at.fhhagenberg.sqe;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import at.fhhagenberg.sqe.eccLayout;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
    	//Parent root = FXMLLoader.load(getClass().getResource("/elevator_control_center.fxml"));
    	var root = (new eccLayout()).getLayout();
        var scene = new Scene(root, 640, 480);
        stage.setTitle("Elevator Control Center");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}