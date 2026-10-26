package pl.workshop.qa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static pl.workshop.qa.AppointmentPolicy.Decision.*;

class AppointmentPolicyTest {
    private final AppointmentPolicy policy = new AppointmentPolicy();

    @Test void acceptsOnePerson() { assertEquals(ACCEPTED, policy.evaluate(1, 4)); }
    @Test void acceptsFourPeople() { assertEquals(ACCEPTED, policy.evaluate(4, 4)); }
    @Test void acceptsExactCapacity() { assertEquals(ACCEPTED, policy.evaluate(2, 2)); }
    @Test void rejectsZeroPeople() { assertEquals(INVALID_PARTY, policy.evaluate(0, 4)); }
    @Test void rejectsFivePeople() { assertEquals(INVALID_PARTY, policy.evaluate(5, 10)); }
    @Test void rejectsFullSlot() { assertEquals(FULL, policy.evaluate(1, 0)); }
    @Test void rejectsInsufficientCapacity() { assertEquals(FULL, policy.evaluate(3, 2)); }
    @Test void rejectsNegativeRemainingData() {
        assertThrows(IllegalArgumentException.class, () -> policy.evaluate(1, -1));
    }
}
