package at.fhhagenberg.sqe.model;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

public interface IElevatorManager {	
	
	/**
	 * Gives information over whether the hardware connection is ready to be used
	 * @return true if hardware is correctly connected and usable, false otherwise
	 */
	public boolean isConnected();
	
	/**
	 * Retrieves the committed direction of the specified elevator (up / down / uncommitted). 
	 * @param elevatorNumber - elevator number whose committed direction is being retrieved 
	 * @return the current direction of the specified elevator where up=0, down=1 and uncommitted=2
	 */
	public ElevatorDirection getCommittedDirection(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException, IllegalStateException; 

	/**
	 * Provides the current acceleration of the specified elevator in meters per sec^2. 
	 * @param elevatorNumber - elevator number whose acceleration is being retrieved 
	 * @return returns the acceleration of the indicated elevator where positive speed is acceleration and negative is deceleration

	 */
	public int getElevatorAccel(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException;

	/**
	 * Provides the status of a floor request button on a specified elevator (on/off).      
	 * @param elevatorNumber - elevator number whose button status is being retrieved
	 * @param floor - floor number button being checked on the selected elevator 
	 * @return returns boolean to indicate if floor button on the elevator is active (true) or not (false)
	 */
	public boolean getElevatorButton(int elevatorNumber, int floor) throws IllegalArgumentException, HardwareConnectionException;

	/**
	 * Provides the current status of the doors of the specified elevator (open/closed).      
	 * @param elevatorNumber - elevator number whose door status is being retrieved 
	 * @return returns the door status of the indicated elevator
	 */
	public ElevatorDoorStatus getElevatorDoorStatus(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException, IllegalStateException; 

	/**
	 * Provides the current location of the specified elevator to the nearest floor 
	 * @param elevatorNumber - elevator number whose location is being retrieved 
	 * @return returns the floor number of the floor closest to the indicated elevator
	 */
	public int getElevatorFloor(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the number of elevators in the building. 
	 * @return total number of elevators
	 */
	public int getElevatorNum() throws HardwareConnectionException; 

	/**
	 * Provides the current location of the specified elevator in feet from the bottom of the building. 
	 * @param elevatorNumber  - elevator number whose location is being retrieved 
	 * @return returns the location in meters of the indicated elevator from the bottom of the building
	 */
	public int getElevatorPosition(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Provides the current speed of the specified elevator in meters per sec. 
	 * @param elevatorNumber - elevator number whose speed is being retrieved 
	 * @return returns the speed of the indicated elevator where positive speed is up and negative is down
	 */
	public int getElevatorSpeed(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the weight of passengers on the specified elevator. 
	 * @param elevatorNumber - elevator number whose service is being retrieved
	 * @return total weight of all passengers in lbs
	 */
	public int getElevatorWeight(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the maximum number of passengers that can fit on the specified elevator.
	 * @param elevatorNumber - elevator number whose service is being retrieved
	 * @return number of passengers
	 */
	public int getElevatorCapacity(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException;
	
	/**
	 * Provides the status of the Down button on specified floor (on/off). 
	 * @param floor - floor number whose Down button status is being retrieved 
	 * @return returns boolean to indicate if button is active (true) or not (false)
	 */
	public boolean getFloorButtonDown(int floor) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Provides the status of the Up button on specified floor (on/off). 
	 * @param floor - floor number whose Up button status is being retrieved 
	 * @return returns boolean to indicate if button is active (true) or not (false)
	 */
	public boolean getFloorButtonUp(int floor) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the height of the floors in the building. 
	 * @return floor height (ft)
	 */
	public int getFloorHeight() throws HardwareConnectionException; 

	/**
	 * Retrieves the number of floors in the building. 
	 * @return total number of floors
	 */
	public int getFloorNum() throws HardwareConnectionException; 

	/** 
	 * Retrieves whether or not the specified elevator will service the specified floor (yes/no). 
	 * @param elevatorNumber elevator number whose service is being retrieved
	 * @param floor floor whose service status by the specified elevator is being retrieved
	 * @return service status whether the floor is serviced by the specified elevator (yes=true,no=false)
	 */
	public boolean getServicesFloors(int elevatorNumber, int floor) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the floor target of the specified elevator. 
	 * @param elevatorNumber elevator number whose target floor is being retrieved
	 * @return current floor target of the specified elevator
	 */
	public int getTarget(int elevatorNumber) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Sets the committed direction of the specified elevator (up / down / uncommitted). 
	 * @param elevatorNumber elevator number whose committed direction is being set
	 * @param direction direction being set
	 */
	public void setCommittedDirection(int elevatorNumber, ElevatorDirection direction) throws IllegalArgumentException, HardwareConnectionException;

	/**
	 * Sets whether or not the specified elevator will service the specified floor (yes/no). 
	 * @param elevatorNumber elevator number whose service is being defined
	 * @param floor floor whose service by the specified elevator is being set
	 * @param service indicates whether the floor is serviced by the specified elevator (yes=true,no=false)
	 */
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Sets the floor target of the specified elevator. 
	 * @param elevatorNumber elevator number whose target floor is being set
	 * @param target floor number which the specified elevator is to target
	 */
	public void setTarget(int elevatorNumber, int target) throws IllegalArgumentException, HardwareConnectionException; 

	/**
	 * Retrieves the current clock tick of the elevator control system. 
	 * @return clock tick
	 */
	public long getClockTick() throws HardwareConnectionException;
}
