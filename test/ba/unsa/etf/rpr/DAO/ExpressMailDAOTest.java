package ba.unsa.etf.rpr.DAO;

import ba.unsa.etf.rpr.enums.OrderStatus;
import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Manager;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressMailDAOTest {

    ExpressMailDAO model = ExpressMailDAO.getInstance();

    @BeforeEach
    public void resetDatabse() throws SQLException {
        model.returnDatabaseToDefault();
    }


    @Test
    void couriers() {

        ArrayList<Courier> couriers = (ArrayList<Courier>) model.couriers();
        assertEquals(2,couriers.size());
    }

    @Test
    void packages() {

        List<Package> packages = model.packages();
        assertEquals(6,packages.size());
    }

    @Test
    void deletePackage() {
        model.deletePackage(3);
        assertEquals(5,model.packages().size());
        assertEquals(2,model.packages().get(1).getId());
    }

    @Test
    void addCourier() {
        Courier courier= new Courier(1,"Courier", "123","courier","courier");
        model.addCourier(courier);
        assertTrue(model.couriers().contains(courier));
    }

    @Test
    void updatePackage() {

        Courier courier= new Courier(1,"Courier", "123","courier","courier");
        User sender = new User(1,"Sender","321","Sender address", "Sender city", 123456);
        User receiver = new User(1,"Receiver","321","Receiver address", "Receiver city", 123456);
        Package aPackage = new Package(1,"Package", "Receiver address", sender, receiver, courier, 1, 1,"Receiver city", 123456, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE);
        model.createPackage(aPackage);
        aPackage.setDescription("new");
        model.updatePackage(aPackage);
        assertEquals("new",model.packages().get(6).getDescription());
    }

    @Test
    void updateCourier() {

        Courier courier= new Courier(3,"Courier", "123","courier","courier");
        model.addCourier(courier);
        assertTrue(model.couriers().contains(courier));
        courier.setImage("image");
        model.updateCourier(courier);
        assertEquals("image",model.couriers().get(2).getImage());

    }

    @Test
    void createPackage() {

        Courier courier= new Courier(1,"Courier", "123","courier","courier");
        User sender = new User(1,"Sender","321","Sender address", "Sender city", 123456);
        User receiver = new User(1,"Receiver","321","Receiver address", "Receiver city", 123456);
        Package aPackage = new Package(1,"Package", "Receiver address", sender, receiver, courier, 1, 1,"Receiver city", 123456, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE);
        model.createPackage(aPackage);
        assertTrue(model.packages().contains(aPackage));
    }

    @Test
    void deleteCourier() {
        Courier courier= new Courier(3,"Courier", "123","courier","courier");
        model.addCourier(courier);
        assertTrue(model.couriers().contains(courier));
        model.deleteCourier(courier);
        assertFalse(model.couriers().contains(courier));
    }


    @Test
    void getUsernames() {
        Courier courier= new Courier(1,"Courier", "123","courier","courier");
        model.addCourier(courier);
        assertTrue(model.getUsernames().contains("courier"));
    }

    @Test
    void managers() {
        assertEquals(1,model.managers().size());
    }

    @Test
    void addManager() {
        Manager manager = new Manager(2,"managerTest","manager","manager");
        model.addManager(manager);
        assertTrue(model.managers().contains(manager));
    }

    @Test
    void updateManager() {

        Manager manager = new Manager(2,"managerTest","manager","manager");
        model.addManager(manager);
        assertTrue(model.managers().contains(manager));
        manager.setPassword("password");
        model.updateManager(manager);
        assertEquals("password",model.managers().get(1).getPassword());
    }

    @Test
    void deleteManager() {
        Manager manager = new Manager(2,"managerTest","manager","manager");
        model.addManager(manager);
        assertTrue(model.managers().contains(manager));
        model.deleteManager(manager);
        assertFalse(model.managers().contains(manager));
    }

    @Test
    void getPackagesForCourier() {
        Courier courier= new Courier(3,"Courier", "123","courier","courier");
        assertEquals(0,model.getPackagesForCourier(courier).size());
        model.addCourier(courier);
        User sender = new User(1,"Sender","321","Sender address", "Sender city", 123456);
        User receiver = new User(1,"Receiver","321","Receiver address", "Receiver city", 123456);
        Package aPackage = new Package(1,"Package", "Receiver address", sender, receiver, courier, 1, 1,"Receiver city", 123456, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE);
        model.createPackage(aPackage);
        assertEquals(1,model.getPackagesForCourier(courier).size());

    }

    @Test
    void getCourier() {

        Courier courier=model.getCourier("courier1","courier1");
        assertEquals(1,courier.getId());
    }

    @Test
    void getManager() {
        Manager manager=model.getManager("manager1","manager1");
        assertEquals(1,manager.getId());
    }

    @Test
    void getManagerUsernames() {
        Manager manager = new Manager(2,"managerTest","managerTest","manager");
        model.addManager(manager);
        assertTrue(model.getManagerUsernames().contains("managerTest"));
    }

    @Test
    void users() {
        assertEquals(3,model.users().size());
    }


}