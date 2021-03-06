package at.fhhagenberg.sqe.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.backend.ElevatorHardwareManager;
import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.backend.IElevatorManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.Elevator.ElevatorDoorStatus;
import sqelevator.IElevator;

@ExtendWith(MockitoExtension.class)
class ElevatorModelUpdaterTest {

	@Mock
	IElevator iElevatorMock;

	@Test
	void testUpdateSetsModelProperties() throws IllegalArgumentException, HardwareConnectionException, RemoteException, MalformedURLException, NotBoundException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(5);
		
		for (int i = 0; i < 10; i++)
			when(iElevatorMock.getElevatorDoorStatus(i)).thenReturn(IElevator.ELEVATOR_DOORS_CLOSING);
		
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		ElevatorModelUpdater updater = new ElevatorModelUpdater(man, model);

		updater.update();

		assertEquals(ElevatorDoorStatus.CLOSING, model.getElevator(0).getDoorStatus());
	}

	@Test 
	void testModelUpdaterSetsTargetFloorCorrectly() throws RemoteException, IllegalArgumentException, HardwareConnectionException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(5);

		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		ElevatorModelUpdater updater = new ElevatorModelUpdater(man, model);

		updater.updateElevatorTargetFloor(0, 2);

		verify(iElevatorMock, times(1)).setTarget(0, 2);
	}
}
