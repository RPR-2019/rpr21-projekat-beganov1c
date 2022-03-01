package ba.unsa.etf.rpr.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CourierTest {

    private Courier courier = new Courier(1,"Courier 1","777","courier1","courier1","image");


    @Test
    void getId() {
        assertEquals(1,courier.getId());
    }

    @Test
    void setId() {
        courier.setId(-1);
        assertEquals(-1,courier.getId());
    }

    @Test
    void getPackages() {
        assertEquals(0,courier.getPackages().size());
    }

    @Test
    void setPackages() {
        courier.setPackages(null);
        assertNull(courier.getPackages());
    }

    @Test
    void getUsername() {
        assertEquals("courier1",courier.getUsername());
    }

    @Test
    void setUsername() {
        courier.setUsername("test");
        assertEquals("test",courier.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("courier1",courier.getPassword());
    }

    @Test
    void setPassword() {
        courier.setPassword("test");
        assertEquals("test",courier.getPassword());
    }

    @Test
    void getImage() {
        assertEquals("image", courier.getImage());
    }

    @Test
    void setImage() {
        courier.setImage("test");
        assertEquals("test",courier.getImage());
    }

    @Test
    void testToString() {
        assertEquals("Courier 1", courier.toString());
    }

    @Test
    void compareTo() {
        Courier test = new Courier(2,"Courier 1","777","courier1","courier1","image");
        assertEquals(-1,courier.compareTo(test));
    }

    @Test
    void testEquals() {
        Courier test = new Courier(1,"Courier 1","777","courier1","courier1","image");
        courier.setId(1);
        assertEquals(courier, test);
    }
}