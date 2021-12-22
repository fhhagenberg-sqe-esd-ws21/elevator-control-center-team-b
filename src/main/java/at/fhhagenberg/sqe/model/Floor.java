package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {

	private int floorNumber;
	private boolean buttonUpPressed;
	private boolean buttonDownPressed;
	private int floorHeight;
	private List<IModelObserver> observers;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		observers = new ArrayList<>();
	}
	
	public boolean isButtonUpPressed() {
		return buttonUpPressed;
	}

	public void setButtonUpPressed(boolean buttonUpPressed) {
		if (this.buttonUpPressed == buttonUpPressed) {
			return;
		}
		
		this.buttonUpPressed = buttonUpPressed;
		observers.forEach((obs) -> obs.floorButtonUpPressedUpdated(this));
	}

	public boolean isButtonDownPressed() {
		return buttonDownPressed;
	}

	public void setButtonDownPressed(boolean buttonDownPressed) {
		if (this.buttonDownPressed == buttonDownPressed) {
			return;
		}
		
		this.buttonDownPressed = buttonDownPressed;
		observers.forEach((obs) -> obs.floorButtonDownPressedUpdated(this));
	}

	public int getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(int floorHeight) {
		if (this.floorHeight == floorHeight) {
			return;
		}
		
		this.floorHeight = floorHeight;
		observers.forEach((obs) -> obs.floorHeightUpdated(this));
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void addModelObserver(IModelObserver observer) {
		observers.add(observer);
	}
	
	
}
