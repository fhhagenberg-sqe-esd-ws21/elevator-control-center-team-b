package at.fhhagenberg.sqe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FloorTest {

	@Mock
	ModelObserver observer;
	
	@Test 
	void testSetButtonUpPressedNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setButtonUpPressed(true);
		
		verify(observer, times(1)).FloorButtonUpPressedUpdated(fl);
		assertTrue(fl.isButtonUpPressed());
	}
	
	@Test 
	void testSetButtonDownPressedNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setButtonDownPressed(true);
		
		verify(observer, times(1)).FloorButtonDownPressedUpdated(fl);
		assertTrue(fl.isButtonDownPressed());
	}
	
	@Test 
	void testSetHeightNotifiesObserver() {
		Floor fl = new Floor(0);
		fl.addModelObserver(observer);
		
		fl.setFloorHeight(240);
		
		verify(observer, times(1)).FloorHeightUpdated(fl);
		assertEquals(240, fl.getFloorHeight());
	}
}
