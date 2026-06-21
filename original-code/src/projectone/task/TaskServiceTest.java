package projectone.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    private TaskService service;

    @BeforeEach
    public void setup() {
        service = new TaskService();
    }

    @Test
    public void testAddTaskSuccess() {
        Task task = new Task("123", "Test Task", "This is a test.");
        service.addTask(task);
        assertEquals("Test Task", service.getTask("123").getName());
    }

    @Test
    public void testAddDuplicateTaskFails() {
        Task task1 = new Task("123", "Task One", "First task.");
        Task task2 = new Task("123", "Task Two", "Second task.");
        service.addTask(task1);
        assertThrows(IllegalArgumentException.class, () -> service.addTask(task2));
    }
    
    // ... (Other service and object validation tests from original file) ...
}