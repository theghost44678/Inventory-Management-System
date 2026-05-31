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
    void testTaskIdTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("12345678901", "Task Title", "Description");
        });
    }

    @Test
    void testTaskNameTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "A".repeat(21), "Description");
        });
    }

    @Test
    void testTaskDescriptionTooLong() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Task("123", "Task Title", "A".repeat(51));
        });
    }

    @Test
    void testSetNameNullThrows() {
        Task task = new Task("123", "Task Title", "Description");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            task.setName(null);
        });
    }
}