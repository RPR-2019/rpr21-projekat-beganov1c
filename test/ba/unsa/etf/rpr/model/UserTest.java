package ba.unsa.etf.rpr.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User user = new User(1,"Name","test","test","test",77220);

    @Test
    void getAddress() {
        assertEquals("test",user.getAddress());
    }

    @Test
    void setAddress() {
        user.setAddress("new");
        assertEquals("new",user.getAddress());
    }

    @Test
    void getId() {
        assertEquals(1,user.getId());
    }

    @Test
    void setId() {
        user.setId(-1);
        assertEquals(-1,user.getId());
    }

    @Test
    void getCity() {
        assertEquals("test",user.getCity());
    }

    @Test
    void setCity() {
        user.setCity("city");
        assertEquals("city",user.getCity());
    }

    @Test
    void getZipCode() {
        assertEquals(77220,user.getZipCode());
    }

    @Test
    void setZipCode() {
        user.setZipCode(123);
        assertEquals(123,user.getZipCode());
    }

    @Test
    void testToString() {
        assertEquals("Name",user.toString());
    }
}