package at.fhhagenberg.sqe.gui;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class ElevatorProperties {

	private Circle position;
	private Integer floor;
	private Circle up;
	private Circle down;
	private Circle stopPlanned;


	public ElevatorProperties() {
		this(0, 0, false, false, false);
	}

	public ElevatorProperties(Integer position, Integer floor, Boolean up, Boolean down, Boolean stopPlanned) {         
		this.position = new Circle(5);
		this.up = new Circle(5);
		this.down = new Circle(5);
		this.stopPlanned = new Circle(5);
		
		
		setFloor(floor);
		setPosition(position);
		setUp(up);
		setDown(down);
		setStopPlanned(stopPlanned);
	}

	public Circle getPosition() {
		return position;
	}

	public void setPosition(Integer fPosition) {
		position.setFill(floor == fPosition ? Color.GREEN : Color.RED);
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer fFloor) {
		floor = fFloor;
	}

	public Circle getUp() {
		return up;
	}

	public void setUp(Boolean fup) {       
		up.setFill(fup ? Color.GREEN : Color.GRAY);
	}

	public Circle getDown() {
		return down;
	}

	public void setDown(Boolean fdown) {
		down.setFill(fdown ? Color.GREEN : Color.GRAY);
	}

	public Circle getStopPlanned() {
		return stopPlanned;
	}

	public void setStopPlanned(Boolean fstopPlanned) {
		stopPlanned.setFill(fstopPlanned ? Color.GREEN : Color.GRAY);
	}
}
