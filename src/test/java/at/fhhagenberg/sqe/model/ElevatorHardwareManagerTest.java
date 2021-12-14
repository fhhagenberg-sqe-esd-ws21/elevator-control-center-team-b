package at.fhhagenberg.sqe.model;

import org.junit.After;
import org.junit.Before;
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

    // TODO add tests per function with connection abort

    @Test
    void testNoInterface() {
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            ehm = new ElevatorHardwareManager(null);
        });
    }

    @Test
    void testNoConnection() throws Exception {
        lenient().when(iElevatorMock.getElevatorNum()).thenThrow(new RemoteException());
        lenient().when(iElevatorMock.getFloorNum()).thenThrow(new RemoteException());

        assertThrowsExactly(HardwareConnectionException.class, () -> {
            ehm = new ElevatorHardwareManager(iElevatorMock);
        });
    }

    @Test
    void testIsConnected() throws Exception {
        ehm = new ElevatorHardwareManager(iElevatorMock);

        assertTrue(ehm.isConnected());

        Mockito.when(iElevatorMock.getElevatorNum()).thenThrow(new RemoteException()); // TODO updated to getClockTick?
        assertFalse(ehm.isConnected());
    }

    @Test
    void testGetCommittedDirection() throws Exception {
        Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

        ehm = new ElevatorHardwareManager(iElevatorMock);

        Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
        assertEquals(Elevator.ElevatorDirection.Up, ehm.getCommittedDirection(0));

        Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_DOWN);
        assertEquals(Elevator.ElevatorDirection.Down, ehm.getCommittedDirection(0));

        Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
        assertEquals(Elevator.ElevatorDirection.Uncommitted, ehm.getCommittedDirection(0));

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

        ehm.setCommittedDirection(0, Elevator.ElevatorDirection.Up);
        Mockito.verify(iElevatorMock, times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);

        ehm.setCommittedDirection(0, Elevator.ElevatorDirection.Down);
        Mockito.verify(iElevatorMock, times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);

        ehm.setCommittedDirection(0, Elevator.ElevatorDirection.Uncommitted);
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
}