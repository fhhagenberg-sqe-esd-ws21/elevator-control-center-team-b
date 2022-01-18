package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.ElevatorModel;
import at.fhhagenberg.sqe.model.ElevatorModelUpdater;
import at.fhhagenberg.sqe.model.Floor;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


class EccLayout {

	Border boarderBlack = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT));
	Border boarderRed = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT));
			
	private final VBox fullLayout;

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

	EccLayout(ElevatorModelUpdater elevModelUpdater, ElevatorModel elevModel) {
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
		levelToGo.borderProperty().set(boarderRed);
		// only allow empty field or number
		levelToGo.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("[0-9]*")) ? change : null));
		levelToGo.setOnAction(event -> onEnterPressedLevelToGo(levelToGo)); // add listener for enter
		levelToGo.textProperty().addListener((observable,  oldValue,  newValue) -> {
				if (oldValue == null || !oldValue.equals(newValue)) {
					onLevelToGoChanged(newValue, levelToGo, go);
				}
			}
		);

		go = new Button("Go");
		go.setOnAction(evt -> onGo());
		go.disableProperty().set(true);

		HBox controlPanel = new HBox();
		controlPanel.setSpacing(12);
		controlPanel.getChildren().addAll(automatic, goToLevel, levelToGo, go);

		VBox rightSide = new VBox(25);
		rightSide.getChildren().addAll(elevatorPropertiesTable, controlPanel);

		elevators = new ListView<>();
		elevators.getSelectionModel().selectedItemProperty().addListener((observable,  oldValue,  newValue) -> {
				if (oldValue == null || !oldValue.equals(newValue)) {
					onElevatorSelect(newValue);
				}

			}
		);

		elevators.getItems().addAll(elevModel.getElevators());

		HBox topView = new HBox(15);

		// botom view
		position = new Label("Position:");
		positionValue = new Label();
		direction = new Label("Direction:");
		directionValue = new Label();
		payload = new Label("Payload:");
		payloadValue = new Label();

		position.setStyle(FX_FONT_SIZE_18);
		positionValue.setStyle(FX_FONT_SIZE_18);
		direction.setStyle(FX_FONT_SIZE_18);
		directionValue.setStyle(FX_FONT_SIZE_18);
		payload.setStyle(FX_FONT_SIZE_18);
		payloadValue.setStyle(FX_FONT_SIZE_18);

		speed = new Label("Speed:");
		speedValue = new Label();
		doors = new Label("Doors:");
		doorsValue = new Label();
		targetFloor = new Label("Target floor:");
		targetFloorValue = new Label();

		speed.setStyle(FX_FONT_SIZE_18);
		speedValue.setStyle(FX_FONT_SIZE_18);
		doors.setStyle(FX_FONT_SIZE_18);
		doorsValue.setStyle(FX_FONT_SIZE_18);
		targetFloor.setStyle(FX_FONT_SIZE_18);
		targetFloorValue.setStyle(FX_FONT_SIZE_18);

		FlowPane botPane = new FlowPane();
		botPane.setHgap(20);
		botPane.setVgap(5);
		botPane.getChildren().addAll(position, positionValue, direction, directionValue, payload, payloadValue, speed,
				speedValue, doors, doorsValue, targetFloor, targetFloorValue);

		rightSide.getChildren().add(botPane);

		topView.getChildren().addAll(elevators, rightSide);
		HBox.setHgrow(elevators, Priority.ALWAYS);
		HBox.setHgrow(rightSide, Priority.ALWAYS);

		fullLayout = new VBox();
		fullLayout.setStyle("-fx-padding: 15; -fx-spacing: 15;");

		fullLayout.getChildren().addAll(title, topView);

		// set labels for easier testing
		elevators.setId("elevatorsList");
		elevatorPropertiesTable.setId("FloorTable");
		automatic.setId("AutomaticButton");
		levelToGo.setId("FloorToGo");
		go.setId("GoButton");

		positionValue.setId("Position");
		directionValue.setId("Direction");
		payloadValue.setId("Payload");
		speedValue.setId("Speed");
		doorsValue.setId("Doors");
		targetFloorValue.setId("TargetFloor");
		automatic.setDisable(true);

		elevators.getSelectionModel().select(0);
	}

	public Parent getLayout() {
		return (fullLayout);
	}

	// private functions for gui interaction
	private void onElevatorSelect(Elevator newElevator) {
		// update all
		int nrOfFloors = elevatorModel.getNumFloors();

		floors = FXCollections.observableArrayList();
		for (int i = 0; i < nrOfFloors; i++) {
			floors.add(new ElevatorProperties(newElevator.getFloor(), i, elevatorModel.getFloor(i).isButtonUpPressed(),
					elevatorModel.getFloor(i).isButtonDownPressed(), newElevator.getFloorStopRequested(i)));
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

	}

	private void onGo() {
		int target;
		try {
			target = Integer.parseInt(levelToGo.getText());
		} catch (NumberFormatException e) {
			return;
		}
		Elevator elevator = elevators.getSelectionModel().getSelectedItem();
		elevatorModelUpdater.updateElevatorTargetFloor(elevator.getElevatorNumber(), target);

	}

	private void onEnterPressedLevelToGo(TextField value) {
		if (isValidFloor(elevators.getSelectionModel().getSelectedItem(), value.getText())) {
			onGo();
		}
	}

	private void onLevelToGoChanged(String level, TextField levelToGo, Button goButton) {
		if (isValidFloor(elevators.getSelectionModel().getSelectedItem(), level)) {
			goButton.disableProperty().set(false);
			levelToGo.borderProperty().set(boarderBlack);
		} else {
			goButton.disableProperty().set(true);
			levelToGo.borderProperty().set(boarderRed);
		}
	}

	private boolean isValidFloor(Elevator el, String floor) {

		try {
			Integer fl = Integer.valueOf(floor);
			if (el.getNumFloors() > fl || el.getFloorToService(fl)) {
				return true;
			}
		}
		// if exception occurs, string is invalid value, thus not a valid floor
		catch (Exception e) {
			return false;
		}

		return false;
	}

	private void setDirection(Elevator elevator, Integer currentFloor, Integer targetFloor) {
		ElevatorDirection dir = ElevatorDirection.UNCOMMITTED;
		if (targetFloor > currentFloor) {
			dir = ElevatorDirection.UP;
		} else if (targetFloor < currentFloor) {
			dir = ElevatorDirection.DOWN;
		}
		elevatorModelUpdater.updateElevatorDirection(elevator.getElevatorNumber(), dir);
	}

	// public functions to update gui
	public void setFloor(Elevator elevator) {

		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			for (int i = 0; i < floors.size(); i++) {
				floors.get(i).setPosition(elevator.getFloor());
			}
			elevatorPropertiesTable.refresh();
		}
	}

	public void setDoorState(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			doorsValue.setText(elevator.getDoorStatus().toString());
		}
	}

	public void setDirection(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			directionValue.setText(elevator.getDirection().toString());
		}
	}

	public void setAcceleration(Elevator elevator) {
		// not shown in GUI
	}

	public void setPosition(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			setDirection(elevator, elevator.getFloor(), elevator.getTargetFloor());
			positionValue.setText(String.valueOf(elevator.getPosition()) + "m");
		}
	}

	public void setSpeed(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			speedValue.setText(String.valueOf(elevator.getSpeed()) + "m/s");
		}
	}

	public void setWeight(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			payloadValue.setText(String.valueOf(elevator.getWeight()) + "kg");
		}
	}

	public void setCapacity(Elevator elevator) {
		// not shown in GUI
	}

	public void setFloorsToStop(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			for (int i = 0; i < floors.size(); i++) {
				int floorNumber = floors.get(i).getFloor();
				floors.get(i).setStopPlanned(elevator.getFloorStopRequested(floorNumber));
			}
			elevatorPropertiesTable.refresh();
		}
	}

	public void setTargetFloor(Elevator elevator) {
		// if update occurs on selected elevator, update view
		if (elevators.getSelectionModel().getSelectedItem().equals(elevator)) {
			targetFloorValue.setText(Integer.toString(elevator.getTargetFloor()));
			setDirection(elevator, elevator.getFloor(), elevator.getTargetFloor());
		}
	}

	public void appendErrorMessage(ElevatorModel model) {
		//not used in gui
	}

	public void setButtonUpPressed(Floor floor) {
		for (int i = 0; i < floors.size(); i++) {
			if (floors.get(i).getFloor() == floor.getFloorNumber()) {
				floors.get(i).setUp(floor.isButtonUpPressed());
			}
		}
		elevatorPropertiesTable.refresh();
	}

	public void setButtonDownPressed(Floor floor) {
		for (int i = 0; i < floors.size(); i++) {
			if (floors.get(i).getFloor() == floor.getFloorNumber()) {
				floors.get(i).setDown(floor.isButtonDownPressed());
			}
		}
		elevatorPropertiesTable.refresh();
	}

	public void setHeight(Floor floor) {
		// not used in gui
	}

}
