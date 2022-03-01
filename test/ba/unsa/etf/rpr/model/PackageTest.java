package ba.unsa.etf.rpr.model;

import ba.unsa.etf.rpr.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageTest {

    private Courier courier = new Courier(1,"Courier 1","777","courier1","courier1","image");
    private User sender = new User(1,"test","test", "test", "test",77220);
    private User receiver = new User(2,"test","test", "test", "test",77220);
    Package aPackage = new Package(1,"test","test",sender,receiver,courier,3,3,"Cazin",77220, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE);

    @Test
    void getId() {
        assertEquals(1,aPackage.getId());
    }

    @Test
    void setId() {
        aPackage.setId(-1);
        assertEquals(-1,aPackage.getId());
    }

    @Test
    void getDescription() {
        assertEquals("test",aPackage.getDescription());
    }

    @Test
    void setDescription() {
        aPackage.setDescription("Desc");
        assertEquals("Desc", aPackage.getDescription());

    }
    @Test
    void compareTo() {

        assertEquals(-1,aPackage.compareTo(new Package(3,"test","test",sender,receiver,courier,3,3,"Cazin",77220, LocalDateTime.now(),null, OrderStatus.IN_WAREHOUSE)));
    }
}