package projectone.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void testValidTaskCreation() {
        Task task = new Task("123", "Task Title", "Valid sample description.");
        Assertions.assertNotNull(task);
    }

    @Test
    void testTaskIdEmptyThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("   ", "Task Title", "Description");
        });
    }

    @Test
    void testTaskIdTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("12345678901", "Task Title", "Description");
        });
    }

    @Test
    void testTaskNameEmptyThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "   ", "Description");
        });
    }

    @Test
    void testTaskNameTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "A".repeat(21), "Description");
        });
    }

    @Test
    void testTaskDescriptionEmptyThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "Task Title", "   ");
        });
    }

    @Test
    void testTaskDescriptionTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "Task Title", "A".repeat(51));
        });
    }
}