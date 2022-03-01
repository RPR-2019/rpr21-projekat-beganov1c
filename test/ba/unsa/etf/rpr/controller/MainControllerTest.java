package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.DAO.ExpressMailDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
@ExtendWith(ApplicationExtension.class)
class MainControllerTest {
    Stage theStage;
    ExpressMailDAO dao = ExpressMailDAO.getInstance();

    @Start
    public void start (Stage stage) throws Exception {

        dao.returnDatabaseToDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        stage.setScene(new Scene(root));
        stage.setTitle("login");
        stage.setResizable(false);
        stage.toFront();
        stage.show();
        theStage = stage;
    }

    @Test
    public void loginTest(FxRobot robot) {

        robot.clickOn("#fldUsername");
        robot.write("courier1");
        robot.clickOn("#fldPassword");
        robot.write("courier1");

        robot.clickOn("#okBtn");
        assertFalse(theStage.isShowing());

    }

    @Test
    public void loginTest1(FxRobot robot) {

        robot.clickOn("#fldUsername");
        robot.write("manager1");
        robot.clickOn("#fldPassword");
        robot.write("manager1");

        robot.clickOn("#okBtn");
        assertFalse(theStage.isShowing());

    }

    @Test
    public void mainTest(FxRobot robot) {

        robot.clickOn("#fldUsername");
        robot.write("admin");
        robot.clickOn("#fldPassword");
        robot.write("admin");

        robot.clickOn("#okBtn");
        assertFalse(theStage.isShowing());


        robot.clickOn("#createPackageBtn");
        robot.clickOn("#descriptionField");
        robot.write("Package");
        robot.clickOn("#senderNameField");
        robot.write("Sender");
        robot.clickOn("#senderTelephoneNumberField");
        robot.write("111");
        robot.clickOn("#senderAddressField");
        robot.write("Sender address");
        robot.clickOn("#senderCityField");
        robot.write("Sender city");
        robot.clickOn("#senderZipCodeField");
        robot.write("123");
        robot.clickOn("#receiverNameField");
        robot.write("Receiver");
        robot.clickOn("#receiverTelephoneNumberField");
        robot.write("222");
        robot.clickOn("#receiverAddressField");
        robot.write("Receiver address");
        robot.clickOn("#receiverCityField");
        robot.write("Receiver city");
        robot.clickOn("#receiverZipCodeField");
        robot.write("321");
        robot.clickOn("#weightField");
        robot.write("2");
        robot.clickOn("#deliveryPriceField");
        robot.write("8");
        robot.clickOn("#courierRadioBtn");
        robot.clickOn("#courierTelephoneNumberField");
        robot.write("1");

        robot.clickOn("#okBtn");
        robot.clickOn("#logoutBtn");
        assertEquals("Package", dao.packages().get(6).getDescription());
        assertEquals("7771", dao.packages().get(6).getCourier().getTelephoneNumber());

    }



}