package at.fhhagenberg.sqe;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

class EccLayout {
    
	private final BorderPane fullLayout;

    private static final String STR_SEL_DATA = "selectedData: ";
    private static final String STR_FALSE = "false";
    private static final String STR_TRUE = "true";
    private static final String FX_FONT_SIZE_18 = "-fx-font-size: 18";

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

        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> upButtonFactory = new Callback<>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                return new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("Up");

                    {
                        btn.setOnAction(t -> {
                            ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                            System.out.println(STR_SEL_DATA + data);
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
            }
        };
        up.setCellFactory(upButtonFactory);
        
        
        TableColumn<ElevatorProperties, Void> down = new TableColumn<>("Down");

        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> downButtonFactory = new Callback<>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                return new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("Down");

                    {
                        btn.setOnAction(t -> {
                            ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                            System.out.println(STR_SEL_DATA + data);
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
            }
        };
        down.setCellFactory(downButtonFactory);
        
        TableColumn<ElevatorProperties, Void> stopPlanned = new TableColumn<>("Stop planned");
        Callback<TableColumn<ElevatorProperties, Void>, TableCell<ElevatorProperties, Void>> stopButtonFactory = new Callback<>() {
            @Override
            public TableCell<ElevatorProperties, Void> call(final TableColumn<ElevatorProperties, Void> param) {
                return new TableCell<ElevatorProperties, Void>() {

                    private final ToggleButton btn = new ToggleButton("stop");

                    {
                        btn.setOnAction(t -> {
                            ElevatorProperties data = getTableView().getItems().get(getIndex()); // gets the data of the selected row
                            System.out.println(STR_SEL_DATA + data);
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
            }
        };
        stopPlanned.setCellFactory(stopButtonFactory);
        
        elevatorPropertiesTable.getColumns().add(status);
        elevatorPropertiesTable.getColumns().add(floor);
		elevatorPropertiesTable.getColumns().add(up);
		elevatorPropertiesTable.getColumns().add(down);
		elevatorPropertiesTable.getColumns().add(stopPlanned);
        				
        ObservableList<ElevatorProperties> data = FXCollections.observableArrayList();

		data.add(new ElevatorProperties(0, 0, STR_FALSE, STR_FALSE, STR_TRUE));
		data.add(new ElevatorProperties(0, 1, STR_FALSE, STR_FALSE ,STR_FALSE));
		data.add(new ElevatorProperties(0, 2, STR_FALSE, STR_TRUE, STR_FALSE));
		
		elevatorPropertiesTable.setItems(data);    
		elevatorPropertiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		ToggleButton automatic = new ToggleButton("Automatic Mode");
	    Label goToLevel = new Label("Go to Level");
	    TextField levelToGo = new TextField();
	    Button go = new Button("Go");
	    
	    HBox controlPanel = new HBox();
	    controlPanel.setSpacing(12);
	    controlPanel.getChildren().addAll(automatic, goToLevel, levelToGo, go);
	    
	    VBox rightSide = new VBox(25);
	    rightSide.getChildren().addAll(elevatorPropertiesTable, controlPanel);
	    
	    
	    ListView<String> elevators = new ListView<>();
	    
	    elevators.getItems().add("Elevator 1");
	    elevators.getItems().add("Elevator 2");
	    elevators.getItems().add("Elevator 3");
	    elevators.getItems().add("Elevator 4");
	    
	    HBox topView = new HBox(15);
	    
	    topView.getChildren().addAll(elevators, rightSide);
	    
	    Label errBox = new Label("Error Box");
	    TextArea errorBox = new TextArea();
	    errorBox.setWrapText(true);
	    
	    
	    VBox topViewFull = new VBox(10);
	    
	    topViewFull.getChildren().addAll(topView, errBox, errorBox);
	    
	    // botom   view
	    Label position = new Label("Position: " + "50m");
	    Label direction = new Label("Direction: " + "Down");
	    Label payload = new Label("Payload: " + "100kg");

	    position.setStyle(FX_FONT_SIZE_18);
	    direction.setStyle(FX_FONT_SIZE_18);
	    payload.setStyle(FX_FONT_SIZE_18);
	    
	    
	    HBox botTopLine = new HBox(15);
	    
	    botTopLine.getChildren().addAll(position, direction, payload);
	    
	    Label speed = new Label("Speed: " + "6km/h");
	    Label doors = new Label("Doors: " + "Closed");
	    Label targetFloor = new Label("Target floor: " + "1");

        speed.setStyle(FX_FONT_SIZE_18);
	    doors.setStyle(FX_FONT_SIZE_18);
	    targetFloor.setStyle(FX_FONT_SIZE_18);
	    
	    
	    HBox botBotLine = new HBox(15);
	    
	    botBotLine.getChildren().addAll(speed, doors, targetFloor);
	    
	    
	    VBox bot = new VBox(20);
	    bot.getChildren().addAll(botTopLine, botBotLine);

	    
	    FlowPane botPane = new FlowPane();
	    botPane.setHgap(20);
	    botPane.setVgap(5);
	    botPane.getChildren().addAll(position, direction, payload, speed, doors, targetFloor);
	    
	    
	    fullLayout = new BorderPane();
	    fullLayout.setStyle("-fx-padding: 15; -fx-spacing: 15;");
	    fullLayout.setTop(title);  
        fullLayout.setCenter(topViewFull);
        fullLayout.setBottom(botPane);
		
	}
	
	public BorderPane getLayout()
	{
		return fullLayout;
	}


}
