package ba.unsa.etf.rpr.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void testToString() {
        assertEquals("Delivered", OrderStatus.DELIVERED.toString());
    }
}