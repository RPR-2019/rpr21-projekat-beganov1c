package ba.unsa.etf.rpr.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    private Manager manager = new Manager(1,"Manager 1","manager1","manager1");

    @Test
    void getId() {
        assertEquals(1,manager.getId());
    }

    @Test
    void setId() {
        manager.setId(-1);
        assertEquals(-1,manager.getId());
    }

    @Test
    void getUsername() {
        assertEquals("manager1",manager.getUsername());
    }

    @Test
    void setUsername() {
        manager.setUsername("test");
        assertEquals("test",manager.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("manager1",manager.getPassword());
    }

    @Test
    void setPassword() {
        manager.setPassword("test");
        assertEquals("test",manager.getPassword());
    }

    @Test
    void testToString() {
        assertEquals("Manager 1", manager.toString());
    }
}