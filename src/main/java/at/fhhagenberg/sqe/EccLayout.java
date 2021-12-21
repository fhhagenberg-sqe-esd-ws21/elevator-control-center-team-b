package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.ModelObserver;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

class EccLayout {
    
	private final BorderPane fullLayout;

    private static final String STR_SEL_DATA = "selectedData: ";
    private static final String STR_FALSE = "false";
    private static final String STR_TRUE = "true";
    private static final String FX_FONT_SIZE_18 = "-fx-font-size: 18";
    
    
    private TableView<ElevatorProperties> elevatorPropertiesTable;
    private TableColumn<ElevatorProperties, Circle> status;
    private TableColumn<ElevatorProperties, Integer> floor;
    private TableColumn<ElevatorProperties, Circle> up;
    private TableColumn<ElevatorProperties, Circle> down;
    private TableColumn<ElevatorProperties, Circle> stopPlanned;
    
    ListView<Elevator> elevators;
    ObservableList<ElevatorProperties> floors;
    ToggleButton automatic;
    TextField levelToGo;
    Button go;
    TextArea errorBox;
    

    Label position;
    Label direction;
    Label payload;
    
    Label speed;
    Label doors;
    Label targetFloor;
    
    EccLayout()
	{


	    Label title = new Label("Elevator Control Center");
	    title.setStyle("-fx-font-size: 30");

	    
	    // table for displaying states
	    elevatorPropertiesTable = new TableView<>();
	    
        status = new TableColumn<>("Position");
        status.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPosition()));

        floor = new TableColumn<>("Floor");
        floor.setCellValueFactory(new PropertyValueFactory<>("floor"));

        up = new TableColumn<>("Up");
        up.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getUp()));
        
        down = new TableColumn<>("Down");
        down.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getDown()));
        

        stopPlanned = new TableColumn<>("Stop Planned");
        stopPlanned.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getStopPlanned()));

        
        
        elevatorPropertiesTable.getColumns().add(status);
        elevatorPropertiesTable.getColumns().add(floor);
		elevatorPropertiesTable.getColumns().add(up);
		elevatorPropertiesTable.getColumns().add(down);
		elevatorPropertiesTable.getColumns().add(stopPlanned);
		
		elevatorPropertiesTable.getColumns().get(0).getCellData(0);
        				
        floors = FXCollections.observableArrayList();

        floors.add(new ElevatorProperties(0, 0, false, false, true));
        floors.add(new ElevatorProperties(0, 1, false, false ,false));
        floors.add(new ElevatorProperties(0, 2, false, true, false));
		
		elevatorPropertiesTable.setItems(floors);    
		elevatorPropertiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		automatic = new ToggleButton("Automatic Mode");
		automatic.setOnAction(evt -> OnAutomaticMode());
	    Label goToLevel = new Label("Go to Level");
	    levelToGo = new TextField();
	    go = new Button("Go");
		go.setOnAction(evt -> OnGo());
	    
	    HBox controlPanel = new HBox();
	    controlPanel.setSpacing(12);
	    controlPanel.getChildren().addAll(automatic, goToLevel, levelToGo, go);
	    
	    VBox rightSide = new VBox(25);
	    rightSide.getChildren().addAll(elevatorPropertiesTable, controlPanel);
	    
	    
	    elevators = new ListView<Elevator>();
	    elevators.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Elevator>() {

			@Override
			public void changed(ObservableValue<? extends Elevator> observable, Elevator oldValue, Elevator newValue) {
	
				if(oldValue == null || !oldValue.equals(newValue))
				{
					OnElevatorSelect(newValue);
				}
				
			}
	    	
		});
	    
	    /*elevators.getItems().add("Elevator 1");
	    elevators.getItems().add("Elevator 2");
	    elevators.getItems().add("Elevator 3");
	    elevators.getItems().add("Elevator 4");*/


	    elevators.getItems().add(new Elevator(1,10));
	    elevators.getItems().add(new Elevator(2,10));
	    elevators.getItems().add(new Elevator(3,10));
	    
	    
	    HBox topView = new HBox(15);
	    
	    topView.getChildren().addAll(elevators, rightSide);
	    
	    Label errBox = new Label("Error Box");
	    errorBox = new TextArea();
	    errorBox.setWrapText(true);
	    
	    
	    VBox topViewFull = new VBox(10);
	    
	    topViewFull.getChildren().addAll(topView, errBox, errorBox);
	    
	    // botom   view
	    position = new Label("Position: " + "50m");
	    direction = new Label("Direction: " + "Down");
	    payload = new Label("Payload: " + "100kg");

	    position.setStyle(FX_FONT_SIZE_18);
	    direction.setStyle(FX_FONT_SIZE_18);
	    payload.setStyle(FX_FONT_SIZE_18);
	    
	    
	    HBox botTopLine = new HBox(15);
	    
	    botTopLine.getChildren().addAll(position, direction, payload);
	    
	    speed = new Label("Speed: " + "6km/h");
	    doors = new Label("Doors: " + "Closed");
	    targetFloor = new Label("Target floor: " + "1");

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

	
	private void OnElevatorSelect(Elevator newElevator)
	{
		errorBox.appendText("new Elevator selected\n");
	}
	
	private void OnGo()
	{
		errorBox.appendText("Go Button pressed\n");		
	}
	
	private void OnAutomaticMode()
	{
		errorBox.appendText("Automaic Mode Button pressed\n");	
		
	}
	

	public void setFloor(Elevator elevator) {
		
	}


	public void setDoorState(Elevator elevator) {
		
	}


	public void setDirection(Elevator elevator) {
		
	}



	public void setAcceleration(Elevator elevator) {
		
	}



	public void setFloorsPressed(Elevator elevator) {
		
	}



	public void setPosition(Elevator elevator) {
		
	}


	public void setSpeed(Elevator elevator) {
		
	}



	public void setWeight(Elevator elevator) {
		
	}


	public void setCapacity(Elevator elevator) {
		
	}



	public void setFloorsToService(Elevator elevator) {
		
	}


	public void setTargetFloors(Elevator elevator) {
		
	}


	public void appendErrorMessage(ElevatorModel model) {
		
	}



	public void setButtonUpPressed(Floor floor) {
		
	}


	public void setButtonDownPressed(Floor floor) {
		
	}


	public void setFloorHeight(Floor floor) {
		
	}


}
