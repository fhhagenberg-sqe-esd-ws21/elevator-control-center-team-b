package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;

@ExtendWith(MockitoExtension.class)
class ElevatorModelUpdaterTest {
	
	@Mock
    IElevator iElevatorMock;
	
	@Test
	void testUpdateSetsModelProperties() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(5);
		when(iElevatorMock.getElevatorDoorStatus(0)).thenReturn(IElevator.ELEVATOR_DOORS_CLOSING);
		
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		ElevatorModelUpdater updater = new ElevatorModelUpdater(man, model);
		
		updater.update();
		
		assertEquals(ElevatorDoorStatus.CLOSING, model.getElevator(0).getDoorStatus());
	}

	
	@Test 
	void testUpdateSetsErrorMessage() throws RemoteException, HardwareConnectionException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(5);
		
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		ElevatorModelUpdater updater = new ElevatorModelUpdater(man, model);
		
		when(iElevatorMock.getElevatorFloor(0)).thenThrow(new RemoteException());		
		updater.update();
		
		assertEquals("Hardware connection lost", model.getErrorMessage());
		assertTrue(model.isDataIsStale());
	}
	
	@Test 
	void testModelUpdaterSetsTargetFloorCorrectly() throws RemoteException, IllegalArgumentException, HardwareConnectionException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(5);
		
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		new ElevatorModelUpdater(man, model);
		
		model.getElevator(0).setTargetFloor(2); // calls target floor updated
		
		verify(iElevatorMock, times(1)).setTarget(0, 2);
	}
}
