package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

class ElevatorTest {
	
	@Test
	void testSetElevatorNumberWorks() {
		Elevator el = new Elevator(2, 5);
		assertEquals(2, el.getElevatorNumber());
	}
	
	@Test
	void testInvalidElevatorNumberCausesException() {
		assertThrows(IllegalArgumentException.class, () -> new Elevator(-2, 5));
	}
	
	@Test 
	void testInvalidNumFloorsCausesException() {
		assertThrows(IllegalArgumentException.class, () -> new Elevator(0, 0));
	}
	
	@Test
	void testSetFloorWorks() {
		Elevator el = new Elevator(0, 5);
		el.setFloor(4);
		assertEquals(4, el.getFloor());
	}
	
	@Test
	void testSetDoorStatusWorks() {
		Elevator el = new Elevator(3, 5);
		el.setDoorStatus(ElevatorDoorStatus.CLOSING);
		assertEquals(ElevatorDoorStatus.CLOSING, el.getDoorStatus());
	}
	
	@Test
	void testSetDirectionWorks() {
		Elevator el = new Elevator(8, 5);
		el.setDirection(ElevatorDirection.UP);
		assertEquals(ElevatorDirection.UP, el.getDirection());
	}
	
	@Test 
	void testSetAccelerationWorks() {
		Elevator el = new Elevator(0, 5);
		el.setAcceleration(40);
		assertEquals(40, el.getAcceleration());
	}
	
	@Test
	void testSetFloorIsPressedWorks() {
		Elevator el = new Elevator(0, 3);
		el.setFloorIsPressed(0, true);
		assertTrue(el.getFloorIsPressed(0));
		for (int i = 1; i < 3; i++) {
			assertFalse(el.getFloorIsPressed(i));
		}
	}
	
	@Test
	void testSetPositionWorks() {
		Elevator el = new Elevator(0, 2);
		el.setPosition(1);
		assertEquals(1, el.getPosition());
	}
	
	@Test
	void testSettingInvalidPositionFails() {
		Elevator el = new Elevator(0, 2);
		assertThrows(IllegalArgumentException.class, () -> el.setPosition(2));		
	}
	
	@Test 
	void testSetSpeedWorks() {
		Elevator el = new Elevator(0, 3);
		el.setSpeed(10);
		assertEquals(10, el.getSpeed());
	} 
	
	@Test
	void testSetWeightWorks() {
		Elevator el = new Elevator(0, 3);
		el.setWeight(1000);
		assertEquals(1000, el.getWeight());
	}
	
	@Test
	void testSetCapacityWorks() {
		Elevator el = new Elevator(0, 3);
		el.setCapacity(10);
		assertEquals(10, el.getCapacity());
	}
	
	@Test
	void testSetFloorToServiceWorks() {
		Elevator el = new Elevator(0, 5);
		el.setFloorToService(4, true);
		assertTrue(el.getFloorToService(4));
		for (int i = 0; i < 4; i++) {
			assertFalse(el.getFloorToService(i));
		}
	}
	
	@Test
	void testSetTargetFloor() {
		Elevator el = new Elevator(0, 4);
		el.setTargetFloor(3);
		assertEquals(3, el.getTargetFloor());
	}
	
	@Test 
	void testSettingInvalidTargetFloorFails() {
		Elevator el = new Elevator(0, 4);
		assertThrows(IllegalArgumentException.class, () -> el.setTargetFloor(4));
	}
	
	@Test 
	void testGetNumFloorsWorks() {
		assertEquals(10, new Elevator(0, 10).getNumFloors());
	}
}
