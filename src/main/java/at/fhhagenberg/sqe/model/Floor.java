package at.fhhagenberg.sqe.model;

import java.util.ArrayList;

public class Floor {

	private int floorNumber;
	private boolean buttonUpPressed;
	private boolean buttonDownPressed;
	private int floorHeight;
	private ArrayList<ModelObserver> observers;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		observers = new ArrayList<ModelObserver>();
	}
	
	public boolean isButtonUpPressed() {
		return buttonUpPressed;
	}

	public void setButtonUpPressed(boolean buttonUpPressed) {
		if (this.buttonUpPressed == buttonUpPressed) {
			return;
		}
		
		this.buttonUpPressed = buttonUpPressed;
		observers.forEach((obs) -> obs.FloorButtonUpPressedUpdated(this));
	}

	public boolean isButtonDownPressed() {
		return buttonDownPressed;
	}

	public void setButtonDownPressed(boolean buttonDownPressed) {
		if (this.buttonDownPressed == buttonDownPressed) {
			return;
		}
		
		this.buttonDownPressed = buttonDownPressed;
		observers.forEach((obs) -> obs.FloorButtonDownPressedUpdated(this));
	}

	public int getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(int floorHeight) {
		if (this.floorHeight == floorHeight) {
			return;
		}
		
		this.floorHeight = floorHeight;
		observers.forEach((obs) -> obs.FloorHeightUpdated(this));
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void addModelObserver(ModelObserver observer) {
		observers.add(observer);
	}
	
	
}
