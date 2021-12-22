package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.backend.HardwareConnectionException;
import at.fhhagenberg.sqe.model.Elevator;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
class AppTest {

	Stage stage;

	@Start
	public void start(Stage _stage) {
		stage = _stage;
	}

	@Test
	void testExceptionWithoutRMI(FxRobot robot) {
		var app = new App();
		assertThrows(Exception.class, () -> app.start(stage));
	}
}
