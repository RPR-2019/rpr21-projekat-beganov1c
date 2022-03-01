package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.enums.OrderStatus;
import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
class DetailsControllerTest {
    Stage theStage;
    DetailsController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        Courier courier= new Courier(1,"Courier", "123","courier","courier");
        User sender = new User(1,"Sender","321","Sender address", "Sender city", 123456);
        User receiver = new User(1,"Receiver","321","Receiver address", "Receiver city", 123456);
        Package aPackage = new Package(1,"Package", "Receiver address", sender, receiver, courier, 1, 1,"Receiver city", 123456, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/details.fxml"),bundle);
        ctrl = new DetailsController(aPackage);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle(bundle.getString("details"));
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testValidate(FxRobot robot) {

        TextField description = robot.lookup("#descriptionField").queryAs(TextField.class);
        TextField weight = robot.lookup("#weightField").queryAs(TextField.class);
        TextField senderName = robot.lookup("#senderNameField").queryAs(TextField.class);
        TextField receiverCityField = robot.lookup("#receiverCityField").queryAs(TextField.class);
        TextField courierNameField = robot.lookup("#courierNameField").queryAs(TextField.class);


        assertEquals("Package",description.getText());
        assertEquals(1,Integer.parseInt(weight.getText()));
        assertEquals("Sender",senderName.getText());
        assertEquals("Receiver city",receiverCityField.getText());
        assertEquals("Courier",courierNameField.getText());

        robot.clickOn("#cancelBtn");

        assertFalse(theStage.isShowing());

    }


}