package at.fhhagenberg.sqe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ElevatorModelFactoryTest {
	@Mock
    IElevator iElevatorMock;
	
	@Test
	void testCreatingFactoryWithNoManagerFails() {
		assertThrows(IllegalArgumentException.class, () -> new ElevatorModelFactory(null));
	}
	
	@Test 
	void testFactoryCreatesCorrectNumberOfElevators() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		when(iElevatorMock.getElevatorNum()).thenReturn(10);
		when(iElevatorMock.getFloorNum()).thenReturn(20);
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		
		assertEquals(10, model.getNumElevators());
	}
	
	@Test 
	void testFactorySetsCorrectElevatorNumbers() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		when(iElevatorMock.getElevatorNum()).thenReturn(20);
		when(iElevatorMock.getFloorNum()).thenReturn(10);
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		
		for (int i = 0; i < 20; i++) {
			assertEquals(i, model.getElevator(i).getElevatorNumber());
		}
	}
	
	@Test 
	void testFactoryCreatesCorrectNumberOfFloors() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		when(iElevatorMock.getElevatorNum()).thenReturn(20);
		when(iElevatorMock.getFloorNum()).thenReturn(10);
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		
		assertEquals(10, model.getNumFloors());
	}
	

	@Test 
	void testFactorySetsCorrectFloorNumbers() throws IllegalArgumentException, HardwareConnectionException, RemoteException {
		when(iElevatorMock.getElevatorNum()).thenReturn(20);
		when(iElevatorMock.getFloorNum()).thenReturn(10);
		IElevatorManager man = new ElevatorHardwareManager(iElevatorMock);
		ElevatorModelFactory fact = new ElevatorModelFactory(man);
		ElevatorModel model = fact.createModel();
		
		for (int i = 0; i < 10; i++) {
			assertEquals(i, model.getFloor(i).getFloorNumber());
		}
	}
	
}
