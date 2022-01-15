package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.ElevatorModelUpdater;
import at.fhhagenberg.sqe.model.Floor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

class EccLayout {
    
	private final VBox fullLayout;

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
    
    private ListView<Elevator> elevators;
    private ObservableList<ElevatorProperties> floors;
    private ToggleButton automatic;
    private TextField levelToGo;
    private Button go;
    private TextArea errorBox;
    

    private Label position;
    private Label positionValue;
    private Label direction;
    private Label directionValue;
    private Label payload;
    private Label payloadValue;

    private Label speed;
    private Label speedValue;
    private Label doors;
    private Label doorsValue;
    private Label targetFloor;
    private Label targetFloorValue;

    private ElevatorModelUpdater elevatorModelUpdater;
    private ElevatorModel elevatorModel;
    
    
    
    EccLayout(ElevatorModelUpdater elevModelUpdater ,ElevatorModel elevModel)
	{
    	elevatorModelUpdater = elevModelUpdater;
    	elevatorModel = elevModel;

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
		elevatorPropertiesTable.setItems(floors);    
		elevatorPropertiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		
		automatic = new ToggleButton("Automatic Mode");
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
	    
	    elevators.getItems().addAll(elevModel.getElevators());
	    
	    HBox topView = new HBox(15);
	    
	    
	    Label errBox = new Label("Error Box");
	    errorBox = new TextArea();
	    errorBox.setWrapText(true);
	       
	    // botom   view
	    position = new Label("Position:");
	    positionValue = new Label("5000m");
	    direction = new Label("Direction:");
	    directionValue = new Label("Down");
	    payload = new Label("Payload:");
	    payloadValue = new Label("100kg");

	    position.setStyle(FX_FONT_SIZE_18);
	    positionValue.setStyle(FX_FONT_SIZE_18);
	    direction.setStyle(FX_FONT_SIZE_18);
	    directionValue.setStyle(FX_FONT_SIZE_18);
	    payload.setStyle(FX_FONT_SIZE_18);
	    payloadValue.setStyle(FX_FONT_SIZE_18);
	    

	    speed = new Label("Speed:");
	    speedValue = new Label("6km/h");
	    doors = new Label("Doors:");
	    doorsValue = new Label("Closed");
	    targetFloor = new Label("Target floor:");
	    targetFloorValue = new Label("1");

        speed.setStyle(FX_FONT_SIZE_18);
        speedValue.setStyle(FX_FONT_SIZE_18);
	    doors.setStyle(FX_FONT_SIZE_18);
	    doorsValue.setStyle(FX_FONT_SIZE_18);
	    targetFloor.setStyle(FX_FONT_SIZE_18);
	    targetFloorValue.setStyle(FX_FONT_SIZE_18);


	    
	    FlowPane botPane = new FlowPane();
	    botPane.setHgap(20);
	    botPane.setVgap(5);  
	    botPane.getChildren().addAll(position, positionValue, direction, directionValue, payload, payloadValue, speed, speedValue, doors, doorsValue, targetFloor, targetFloorValue);
		  
	    
	    rightSide.getChildren().add(errBox);
	    rightSide.getChildren().add(errorBox);
	    rightSide.getChildren().add(botPane);
	    

	    topView.getChildren().addAll(elevators, rightSide);
	    topView.setHgrow(elevators, Priority.ALWAYS);
	    topView.setHgrow(rightSide, Priority.ALWAYS);
	    
	    
	    fullLayout = new VBox();
	    fullLayout.setStyle("-fx-padding: 15; -fx-spacing: 15;");
	    
	    fullLayout.getChildren().addAll(title, topView);
	    
	    // set labels for easier testing
	    elevators.setId("elevatorsList");
	    elevatorPropertiesTable.setId("FloorTable");
	    automatic.setId("AutomaticButton");
	    levelToGo.setId("FloorToGo");
	    go.setId("GoButton");
	    errorBox.setId("ErrorBox");

	    positionValue.setId("Position");
	    directionValue.setId("Direction");
	    payloadValue.setId("Payload");
	    speedValue.setId("Speed");
	    doorsValue.setId("Doors");
	    targetFloorValue.setId("TargetFloor");
	    automatic.setDisable(true);
	    
	    elevators.getSelectionModel().select(0);
		
	}
    
    
	public Parent getLayout()
	{
		return (fullLayout);
	}


	
	// private functions for gui interaction
	private void OnElevatorSelect(Elevator newElevator)
	{
		// update all
		int nrOfFloors = elevatorModel.getNumFloors();
		
		floors = FXCollections.observableArrayList();
		for(int i = 0; i < nrOfFloors; i++)
		{
			floors.add(new ElevatorProperties(newElevator.getFloor(), i, 
							elevatorModel.getFloor(i).isButtonUpPressed(), 
							elevatorModel.getFloor(i).isButtonDownPressed(), 
							newElevator.getFloorStopRequested(i)));
		}

		elevatorPropertiesTable.setItems(floors); 
		elevatorPropertiesTable.getSortOrder().add(floor);
		floor.setSortType(TableColumn.SortType.DESCENDING);
		elevatorPropertiesTable.sort();

		setPosition(newElevator);
		setDirection(newElevator);
		setWeight(newElevator);
		setSpeed(newElevator);
		setDoorState(newElevator);
		setTargetFloor(newElevator);
		
		errorBox.appendText(newElevator.toString() + " selected\n");
	}
	
	private void OnGo()
	{
		int targetFloor;
		try
		{
			targetFloor = Integer.parseInt(levelToGo.getText());
		}
		catch (NumberFormatException e) {
			errorBox.appendText("Invalid Flor Number: \n" + levelToGo.getText()+ "\n is not a valid Floor\n\n");
			return;
		}
		elevatorModelUpdater.updateElevatorTargetFloor(elevators.getSelectionModel().getSelectedItem().getElevatorNumber(), targetFloor);
		errorBox.appendText("Go Button pressed\n");		
	}
	
	
	// public functions to update gui
	public void setFloor(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			for(int i = 0; i < floors.size();i++)
			{
				floors.get(i).setPosition(elevator.getFloor());
			}
			elevatorPropertiesTable.refresh();
		}
	}


	public void setDoorState(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			doorsValue.setText(elevator.getDoorStatus().toString());
		}
	}


	public void setDirection(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			directionValue.setText(elevator.getDirection().toString());			
		}
	}



	public void setAcceleration(Elevator elevator) {
		// not shown in GUI
	}


	public void setPosition(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			positionValue.setText(String.valueOf(elevator.getPosition()) + "m");
		}
	}


	public void setSpeed(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			speedValue.setText(String.valueOf(elevator.getSpeed()) + "m/s");
		}
	}



	public void setWeight(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			payloadValue.setText(String.valueOf(elevator.getWeight()) + "kg");
		}
	}


	public void setCapacity(Elevator elevator) {
		// not shown in GUI
	}



	public void setFloorsToStop(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			for(int i = 0; i < floors.size();i++)
			{
				int floorNumber = floors.get(i).getFloor();
				floors.get(i).setStopPlanned(elevator.getFloorStopRequested(floorNumber));					
			}
			elevatorPropertiesTable.refresh();
		}
	}


	public void setTargetFloor(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if(elevators.getSelectionModel().getSelectedItem().equals(elevator))
		{
			targetFloorValue.setText(Integer.toString(elevator.getTargetFloor()));
		}
	}


	public void appendErrorMessage(ElevatorModel model) {
		errorBox.appendText(model.getErrorMessage()  + "\n\n");		
	}


	public void setButtonUpPressed(Floor floor) {
		for(int i = 0; i < floors.size(); i++)
		{
			if(floors.get(i).getFloor() == floor.getFloorNumber())
			{
				floors.get(i).setUp(floor.isButtonUpPressed());
			}
		}
		elevatorPropertiesTable.refresh();
	}

	public void setButtonDownPressed(Floor floor) {
		for(int i = 0; i < floors.size(); i++)
		{
			if(floors.get(i).getFloor() == floor.getFloorNumber())
			{
				floors.get(i).setDown(floor.isButtonDownPressed());
			}
		}
		elevatorPropertiesTable.refresh();
	}


	public void setHeight(Floor floor) {
		// not used in gui
	}

}
