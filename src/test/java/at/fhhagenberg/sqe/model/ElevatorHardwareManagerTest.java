package at.fhhagenberg.sqe.model;

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
    void testIsConnected() throws Exception {
        ehm = new ElevatorHardwareManager(iElevatorMock);

        assertTrue(ehm.isConnected());

        Mockito.when(iElevatorMock.getElevatorNum()).thenThrow(new RemoteException()); // TODO updated to getClockTick?
        assertFalse(ehm.isConnected());
    }

    @Test
    void testGetFloorNum() throws Exception {
        Mockito.when(iElevatorMock.getFloorNum()).thenReturn(1);

        ehm = new ElevatorHardwareManager(iElevatorMock);

        assertEquals(1, ehm.getFloorNum());
        Mockito.verify(iElevatorMock, times(1)).getFloorNum();
    }

    @Test
    void testGetElevatorNum() throws Exception {
        Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);

        ehm = new ElevatorHardwareManager(iElevatorMock);

        assertEquals(1, ehm.getElevatorNum());
        Mockito.verify(iElevatorMock, times(1)).getElevatorNum();
    }

    @Test
    void testGetCommittedDirection() throws Exception {
        Mockito.when(iElevatorMock.getElevatorNum()).thenReturn(1);
        Mockito.when(iElevatorMock.getFloorNum()).thenReturn(1);

        ehm = new ElevatorHardwareManager(iElevatorMock);

        Mockito.when(iElevatorMock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);

        assertEquals(Elevator.ElevatorDirection.Up, ehm.getCommittedDirection(0));
        Mockito.verify(iElevatorMock, times(1)).getCommittedDirection(0);
    }
}