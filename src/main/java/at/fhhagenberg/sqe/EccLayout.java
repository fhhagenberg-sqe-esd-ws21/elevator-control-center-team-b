package at.fhhagenberg.sqe;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import javafx.scene.shape.Circle;
import javafx.util.Callback;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class EccLayout {
    
	private BorderPane fullLayout;
	private String fontSizeStyle = "-fx-font-size: 18";
	
	EccLayout()
	{


	    Label title = new Label("Elevator Control Center");
	    title.setStyle("-fx-font-size: 30");

	    
	    // table for displaying states
	    TableView<ElevatorProperties> elevatorPropertiesTable = new TableView<>();
        TableColumn<ElevatorProperties, Circle> status = new TableColumn<>("Position");
        status.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPosition()));

        TableColumn<ElevatorProperties, Integer> floor = new TableColumn<>("Floor");
        floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        
        TableColumn<ElevatorProperties, Void> up = new TableColumn<>("Up");
        up.setCellFactory((col) -> {
            final TableCell<ElevatorProperties, Void> cell = new TableCell<>() {

                private final ToggleButton btn = new ToggleButton("Up");

                {
                    btn.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent t) {
                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });
        
        
        TableColumn<ElevatorProperties, Void> down = new TableColumn<ElevatorProperties, Void>("Down");

        down.setCellFactory((col) -> {
            final TableCell<ElevatorProperties, Void> cell = new TableCell<>() {

                private final ToggleButton btn = new ToggleButton("Down");

                {
                    btn.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent t) {
                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });
        
        TableColumn<ElevatorProperties, Void> stopPlanned = new TableColumn<ElevatorProperties, Void>("Stop planned");
        stopPlanned.setCellFactory((col) -> {
            return new TableCell<>() {

                private final ToggleButton btn = new ToggleButton("stop");

                {
                    btn.setOnAction(new EventHandler<>(){
                        @Override
                        public void handle(ActionEvent t) {
                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                        }
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
        });
        
        elevatorPropertiesTable.getColumns().add(status);
        elevatorPropertiesTable.getColumns().add(floor);
		elevatorPropertiesTable.getColumns().add(up);
		elevatorPropertiesTable.getColumns().add(down);
		elevatorPropertiesTable.getColumns().add(stopPlanned);
        				
        ObservableList<ElevatorProperties> data = FXCollections.observableArrayList();

		elevatorPropertiesTable.setItems(data);    
		elevatorPropertiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		ToggleButton Automatic = new ToggleButton("Automatic Mode");
	    Label GoToLevel = new Label("Go to Level");
	    TextField LevelToGo = new TextField();
	    Button Go = new Button("Go");
	    
	    HBox controlPanel = new HBox();
	    controlPanel.setSpacing(12);
	    controlPanel.getChildren().addAll(Automatic, GoToLevel, LevelToGo, Go);
	    
	    VBox rightSide = new VBox(25);
	    rightSide.getChildren().addAll(elevatorPropertiesTable, controlPanel);
	    
	    
	    ListView<String> elevators = new ListView<String>();
	    
	    elevators.getItems().add("Elevator 1");
	    elevators.getItems().add("Elevator 2");
	    elevators.getItems().add("Elevator 3");
	    elevators.getItems().add("Elevator 4");
	    
	    HBox TopView = new HBox(15);
	    
	    TopView.getChildren().addAll(elevators, rightSide);
	    
	    Label ErrBox = new Label("Error Box");
	    TextArea ErrorBox = new TextArea();
	    ErrorBox.setWrapText(true);   
	    
	    
	    VBox TopViewFull = new VBox(10);
	    
	    TopViewFull.getChildren().addAll(TopView, ErrBox, ErrorBox);
	    
	    // botom   view
	    Label Position = new Label("Position: " + "50m");
	    Label Direction = new Label("Direction: " + "Down");
	    Label Payload = new Label("Payload: " + "100kg");
	   
	    Position.setStyle(fontSizeStyle);
	    Direction.setStyle(fontSizeStyle);
	    Payload.setStyle(fontSizeStyle);
	    
	    
	    HBox botTopLine = new HBox(15);
	    
	    botTopLine.getChildren().addAll(Position, Direction, Payload);
	    
	    Label Speed = new Label("Speed: " + "6km/h");
	    Label Doors = new Label("Doors: " + "Closed");
	    Label TargetFloor = new Label("Target floor: " + "1");

	    Speed.setStyle(fontSizeStyle);
	    Doors.setStyle(fontSizeStyle);
	    TargetFloor.setStyle(fontSizeStyle);
	    
	    
	    HBox botBotLine = new HBox(15);
	    
	    botBotLine.getChildren().addAll(Speed, Doors, TargetFloor);
	    
	    
	    VBox bot = new VBox(20);
	    bot.getChildren().addAll(botTopLine, botBotLine);

	    
	    FlowPane botPane = new FlowPane();
	    botPane.setHgap(20);
	    botPane.setVgap(5);
	    botPane.getChildren().addAll(Position, Direction, Payload, Speed, Doors, TargetFloor);
	    
	    
	    fullLayout = new BorderPane();
	    fullLayout.setStyle("-fx-padding: 15; -fx-spacing: 15;");
	    fullLayout.setTop(title);  
        fullLayout.setCenter(TopViewFull);
        fullLayout.setBottom(botPane);
		
	}
	
	public BorderPane getLayout()
	{
		return fullLayout;
	}


}
