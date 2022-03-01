package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.model.Manager;
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

import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ManagerControllerTest {
    Stage theStage;
    ManagerController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add("test");
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager.fxml"),bundle);
        ctrl = new ManagerController(null,arrayList);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("createManager"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testValidate(FxRobot robot) {
        robot.clickOn("#okBtn");

        TextField name = robot.lookup("#nameField").queryAs(TextField.class);
        Background bg1 = name.getBackground();
        TextField username = robot.lookup("#usernameField").queryAs(TextField.class);
        Background bg2 = username.getBackground();
        TextField password = robot.lookup("#passwordField").queryAs(TextField.class);
        Background bg3 = password.getBackground();
        boolean colorFoundName = false;
        boolean colorFoundUsername = false;
        boolean colorFoundPassword = false;
        for (BackgroundFill bf : bg1.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFoundName = true;
        assertTrue(colorFoundName);
        for (BackgroundFill bf : bg2.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFoundUsername = true;
        assertTrue(colorFoundUsername);
        for (BackgroundFill bf : bg3.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFoundPassword = true;
        assertTrue(colorFoundPassword);


        robot.clickOn("#nameField");
        robot.write("Name");
        robot.clickOn("#usernameField");
        robot.write("Username");
        robot.clickOn("#passwordField");
        robot.write("Password");

        robot.clickOn("#okBtn");

        assertFalse(theStage.isShowing());
    }

    @Test
    public void testReturn(FxRobot robot) {

        robot.clickOn("#nameField");
        robot.write("Name");
        robot.clickOn("#usernameField");
        robot.write("Username");
        robot.clickOn("#passwordField");
        robot.write("Password");
        robot.clickOn("#okBtn");

        assertFalse(theStage.isShowing());

        Manager manager = ctrl.getManager();
        assertEquals(manager.getPassword(),"Password");
        assertEquals(manager.getName(),"Name");
    }
}