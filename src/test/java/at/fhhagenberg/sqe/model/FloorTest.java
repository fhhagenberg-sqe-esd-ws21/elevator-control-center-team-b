package at.fhhagenberg.sqe.model;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FloorTest {

	@Mock
	ModelObserver observer;
	
	@Test 
	void testSetButtonUpPressedNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setButtonUpPressed(true);
		
		verify(observer, times(1)).floorButtonUpPressedUpdated(fl);
		assertTrue(fl.isButtonUpPressed());
	}
	
	@Test 
	void testSetButtonDownPressedNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setButtonDownPressed(true);
		
		verify(observer, times(1)).floorButtonDownPressedUpdated(fl);
		assertTrue(fl.isButtonDownPressed());
	}
	
	@Test 
	void testSetHeightNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setFloorHeight(240);
		
		verify(observer, times(1)).floorHeightUpdated(fl);
		assertEquals(240, fl.getFloorHeight());
	}
}
