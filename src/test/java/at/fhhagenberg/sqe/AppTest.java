package at.fhhagenberg.sqe;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.model.HardwareConnectionException;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws IOException 
     * @throws HardwareConnectionException 
     */
    @Start
    public void start(Stage stage) throws IOException, HardwareConnectionException {
        var app = new App();
        app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonWithText(FxRobot robot) {
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Go"));
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Disabled
    @Test
    public void testButtonClick(FxRobot robot) {
        // when:
        robot.clickOn(".button");

        // or (lookup by css class):
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Clicked!"));
    }
}