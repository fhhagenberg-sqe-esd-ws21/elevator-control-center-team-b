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
import javafx.scene.paint.Color; 
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import at.fhhagenberg.sqe.ElevatorProperties;

public class eccLayout {
    
	private BorderPane fullLayout;
	
	eccLayout()
	{


	    Label title = new Label("Elevator Control Center");
	    title.setStyle("-fx-font-size: 30");

	    
	    // table for displaying states
	    TableView<ElevatorProperties> elevatorPropertiesTable = new TableView<>();
        TableColumn<ElevatorProperties, Circle> status = new TableColumn<>("Position");
        status.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPosition()));

        TableColumn<ElevatorProperties, Integer> floor = new TableColumn<>("Floor");
        floor.setCellValueFactory(new PropertyValueFactory<ElevatorProperties, Integer>("floor"));
        
        TableColumn<ElevatorProperties, Void> up = new TableColumn("Up");

        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> upButtonFactory = new Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                final TableCell<ElevatorProperties, Void> cell = new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("Up");

                    {
                        btn.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent t) {
	                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
	                            System.out.println("selectedData: " + data);
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
            }
        };
        up.setCellFactory(upButtonFactory);
        
        
        TableColumn<ElevatorProperties, Void> down = new TableColumn("Down");

        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> downButtonFactory = new Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                final TableCell<ElevatorProperties, Void> cell = new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("Down");

                    {
                        btn.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent t) {
	                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
	                            System.out.println("selectedData: " + data);
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
            }
        };
        down.setCellFactory(downButtonFactory);
        
        TableColumn<ElevatorProperties, Void> stopPlanned = new TableColumn("Stop planned");
        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> stopButtonFactory = new Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                final TableCell<ElevatorProperties, Void> cell = new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("stop");

                    {
                        btn.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent t) {
	                        	ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
	                            System.out.println("selectedData: " + data);
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
            }
        };
        stopPlanned.setCellFactory(stopButtonFactory);
        
        
      
        
        
        elevatorPropertiesTable.getColumns().addAll(status, floor, up, down, stopPlanned);
        ObservableList<ElevatorProperties> data = FXCollections.observableArrayList();

		data.add(new ElevatorProperties(0, 0, "false", "false", "true"));
		data.add(new ElevatorProperties(0, 1, "false", "false" ,"false"));
		data.add(new ElevatorProperties(0, 2, "false", "true", "false"));
		
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
	   
	    Position.setStyle("-fx-font-size: 18");
	    Direction.setStyle("-fx-font-size: 18");
	    Payload.setStyle("-fx-font-size: 18");
	    
	    
	    HBox botTopLine = new HBox(15);
	    
	    botTopLine.getChildren().addAll(Position, Direction, Payload);
	    
	    Label Speed = new Label("Speed: " + "6km/h");
	    Label Doors = new Label("Doors: " + "Closed");
	    Label TargetFloor = new Label("Target floor: " + "1");

	    Speed.setStyle("-fx-font-size: 18");
	    Doors.setStyle("-fx-font-size: 18");
	    TargetFloor.setStyle("-fx-font-size: 18");
	    
	    
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
