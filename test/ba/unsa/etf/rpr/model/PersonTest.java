package ba.unsa.etf.rpr.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

   User person = new User(1,"Name","123456","address","City",12345);
    @Test
    void getName() {
        assertEquals("Name",person.getName());
    }

    @Test
    void setName() {
        person.setName("test");
        assertEquals("test",person.getName());
    }

    @Test
    void getTelephoneNumber() {
        assertEquals("123456",person.getTelephoneNumber());
    }

    @Test
    void setTelephoneNumber() {
        person.setTelephoneNumber("1122");
        assertEquals("1122",person.getTelephoneNumber());
    }
}