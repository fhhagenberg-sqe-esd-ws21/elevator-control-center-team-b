package at.fhhagenberg.sqe.backend;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Elevator.ElevatorDirection;
import sqelevator.IElevator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorHardwareManagerTest {

	@Mock
	IElevator iElevatorMock;

	ElevatorHardwareManager ehm;

	@Test
	void testNoInterface() {
		assertThrowsExactly(IllegalArgumentException.class, () -> ehm = new ElevatorHardwareManager(null));
	}

	@Test
	void testNoConnection() throws Exception {
		lenient().when(iElevatorMock.getElevatorNum()).thenThrow(new RemoteException());
		lenient().when(iElevatorMock.getFloorNum()).thenThrow(new RemoteException());

		assertThrowsExactly(HardwareConnectionException.class, () -> ehm = new ElevatorHardwareManager(iElevatorMock));
	}

	@Test
	void testIsConnected() throws Exception {
		ehm = new ElevatorHardwareManager(iElevatorMock);

		assertTrue(ehm.isConnected());

		Mockito.when(iElevatorMock.getElevatorNum()).thenThrow(new RemoteException());
		assertFalse(ehm.isConnected());
	}

	@Test
	void testGetCommittedDirection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
		assertEquals(Elevator.ElevatorDirection.UP, ehm.getCommittedDirection(0));

		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_DOWN);
		assertEquals(Elevator.ElevatorDirection.DOWN, ehm.getCommittedDirection(0));

		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		assertEquals(Elevator.ElevatorDirection.UNCOMMITTED, ehm.getCommittedDirection(0));

		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(-1);
		assertThrowsExactly(IllegalStateException.class, () -> ehm.getCommittedDirection(0));

		Mockito.verify(iElevatorMock, times(4)).getCommittedDirection(0);
	}

	@Test
	void testGetElevatorAccel() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorAccel(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorAccel(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorAccel(0);
	}

	@Test
	void testGetElevatorButton() throws Exception {
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorButton(1, 0)).thenReturn(true);
		assertTrue(ehm.getElevatorButton(1, 0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorButton(1, 0);
	}

	@Test
	void testGetElevatorDoorStatus() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorAccel(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorAccel(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorAccel(0);
	}

	@Test
	void testGetElevatorFloor() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorFloor(0)).thenReturn(1);
		assertEquals(1, ehm.getElevatorFloor(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorFloor(0);
	}

	@Test
	void testGetElevatorNum() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		assertEquals(1, ehm.getElevatorNum());
		Mockito.verify(iElevatorMock, times(1)).getElevatorNum();
	}

	@Test
	void testGetElevatorPosition() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorPosition(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorPosition(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorPosition(0);
	}

	@Test
	void testGetElevatorSpeed() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorSpeed(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorSpeed(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorSpeed(0);
	}

	@Test
	void testGetElevatorWeight() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorWeight(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorWeight(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorWeight(0);
	}

	@Test
	void testGetElevatorCapacity() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getElevatorCapacity(0)).thenReturn(4711);
		assertEquals(4711, ehm.getElevatorCapacity(0));
		Mockito.verify(iElevatorMock, times(1)).getElevatorCapacity(0);
	}

	@Test
	void testGetFloorButtonDown() throws Exception {
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getFloorButtonDown(0)).thenReturn(true);
		Mockito.when(iElevatorMock.getFloorButtonDown(1)).thenReturn(false);
		assertTrue(ehm.getFloorButtonDown(0));
		assertFalse(ehm.getFloorButtonDown(1));

		Mockito.verify(iElevatorMock, times(1)).getFloorButtonDown(0);
		Mockito.verify(iElevatorMock, times(1)).getFloorButtonDown(1);
	}

	@Test
	void testGetFloorButtonUp() throws Exception {
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getFloorButtonUp(0)).thenReturn(true);
		Mockito.when(iElevatorMock.getFloorButtonUp(1)).thenReturn(false);
		assertTrue(ehm.getFloorButtonUp(0));
		assertFalse(ehm.getFloorButtonUp(1));

		Mockito.verify(iElevatorMock, times(1)).getFloorButtonUp(0);
		Mockito.verify(iElevatorMock, times(1)).getFloorButtonUp(1);
	}

	@Test
	void testGetFloorHeight() throws Exception {
		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getFloorHeight()).thenReturn(4711);
		assertEquals(4711, ehm.getFloorHeight());
		Mockito.verify(iElevatorMock, times(1)).getFloorHeight();
	}

	@Test
	void testGetFloorNum() throws Exception {
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		assertEquals(1, ehm.getFloorNum());
		Mockito.verify(iElevatorMock, times(1)).getFloorNum();
	}

	@Test
	void testGetServicesFloors() throws Exception {
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getServicesFloors(1, 0)).thenReturn(false);
		assertFalse(ehm.getServicesFloors(1, 0));
		Mockito.verify(iElevatorMock, times(1)).getServicesFloors(1, 0);
	}

	@Test
	void testGetTarget() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getTarget(0)).thenReturn(0);
		assertEquals(0, ehm.getTarget(0));
		Mockito.verify(iElevatorMock, times(1)).getTarget(0);

		Mockito.when(iElevatorMock.getTarget(1)).thenReturn(1);
		assertEquals(1, ehm.getTarget(1));
		Mockito.verify(iElevatorMock, times(1)).getTarget(1);
	}

	@Test
	void testSetCommittedDirection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		ehm.setCommittedDirection(0, Elevator.ElevatorDirection.UP);
		Mockito.verify(iElevatorMock, times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);

		ehm.setCommittedDirection(0, Elevator.ElevatorDirection.DOWN);
		Mockito.verify(iElevatorMock, times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);

		ehm.setCommittedDirection(0, Elevator.ElevatorDirection.UNCOMMITTED);
		Mockito.verify(iElevatorMock, times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
	}

	@Test
	void testSetServicesFloors() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		ehm.setServicesFloors(0, 0, false);
		Mockito.verify(iElevatorMock, times(1)).setServicesFloors(0, 0, false);
	}

	@Test
	void testSetTarget() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(2);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(2);

		ehm = new ElevatorHardwareManager(iElevatorMock);

		ehm.setTarget(0, 1);
		Mockito.verify(iElevatorMock, times(1)).setTarget(0, 1);

		ehm.setTarget(1, 0);
		Mockito.verify(iElevatorMock, times(1)).setTarget(1, 0);
	}

	@Test
	void testGetClockTick() throws Exception {
		ehm = new ElevatorHardwareManager(iElevatorMock);
		long tick = 1;

		Mockito.when(iElevatorMock.getClockTick()).thenReturn(tick);
		assertEquals(tick, ehm.getClockTick());
		tick++;
		Mockito.when(iElevatorMock.getClockTick()).thenReturn(tick);
		assertEquals(tick, ehm.getClockTick());
		tick++;
		Mockito.when(iElevatorMock.getClockTick()).thenReturn(tick);
		assertEquals(tick, ehm.getClockTick());

		Mockito.verify(iElevatorMock, times(3)).getClockTick();
	}

	@Test
	void testGetClockTickDisconnect() throws Exception {
		ehm = new ElevatorHardwareManager(iElevatorMock);

		Mockito.when(iElevatorMock.getClockTick()).thenThrow(new RemoteException());
		assertThrowsExactly(HardwareConnectionException.class, () -> ehm.getClockTick());
	}

	@Test
	void testThrowsExceptionWhenInvalidElevatorIsGiven() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(5);
		ehm = new ElevatorHardwareManager(iElevatorMock);

		assertThrows(IllegalArgumentException.class, () -> ehm.getCommittedDirection(5));
	}

	@Test
	void testCommittedDirectionFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getCommittedDirection(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getCommittedDirection(0));
	}

	@Test
	void testElevatorAccelerationFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorAccel(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorAccel(0));
	}

	@Test
	void testGetElevatorButtonFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorButton(0, 0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorButton(0, 0));
	}

	@Test
	void testGetElevatorDoorStatusFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorDoorStatus(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorDoorStatus(0));
	}

	@Test
	void testGetElevatorPositionFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorPosition(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorPosition(0));
	}

	@Test
	void testGetElevatorSpeedFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorSpeed(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorSpeed(0));
	}

	@Test
	void testGetElevatorWeightFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorWeight(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorWeight(0));
	}

	@Test
	void testGetElevatorCapacityFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getElevatorCapacity(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getElevatorCapacity(0));
	}

	@Test
	void testFloorButtonDownFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorButtonDown(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getFloorButtonDown(0));
	}

	@Test
	void testFloorButtonUpFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorButtonUp(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getFloorButtonUp(0));
	}

	@Test
	void testGetFloorHeightFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorHeight()).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getFloorHeight());
	}

	@Test
	void testGetServicesFloorsFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getServicesFloors(0, 0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getServicesFloors(0, 0));
	}

	@Test
	void testGetTargetFailsWithNoConnection() throws Exception {
		Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getFloorNum()).thenReturn(3);
		Mockito.when(iElevatorMock.getTarget(0)).thenThrow(RemoteException.class);
		ehm = new ElevatorHardwareManager(iElevatorMock);
		assertThrows(HardwareConnectionException.class, () -> ehm.getTarget(0));
	}
}