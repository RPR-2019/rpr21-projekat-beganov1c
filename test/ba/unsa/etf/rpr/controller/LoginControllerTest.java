package ba.unsa.etf.rpr.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Locale;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    Stage theStage;

    @Start
    public void start (Stage stage) throws Exception {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.toFront();
        stage.show();
        theStage=stage;
    }

    @Test
    public void testValidate(FxRobot robot) {

        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        Background bg2 = username.getBackground();
        TextField password = robot.lookup("#fldPassword").queryAs(TextField.class);
        Background bg3 = password.getBackground();
        boolean colorFoundUsername = false;
        boolean colorFoundPassword = false;

        for (BackgroundFill bf : bg2.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFoundUsername = true;
        assertTrue(colorFoundUsername);
        for (BackgroundFill bf : bg3.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFoundPassword = true;
        assertTrue(colorFoundPassword);


        robot.clickOn("#fldUsername");
        robot.write("admin");
        robot.clickOn("#fldPassword");
        robot.write("admin");

        robot.clickOn("#okBtn");

        assertFalse(theStage.isShowing());
    }
}