package projectone.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;

public class TaskServiceTest {

    private TaskRepository repositoryMock;
    private TaskService service;

    @BeforeEach
    public void setup() {
        repositoryMock = Mockito.mock(TaskRepository.class);
        service = new TaskService(repositoryMock);
    }

    @Test
    public void testAddTaskSuccess() {
        Task task = new Task("123", "Test Task", "This is a test.");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(false);

        service.addTask(task);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(task);
    }

    @Test
    public void testAddDuplicateTaskFails() {
        Task task1 = new Task("123", "Task One", "First task.");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(task1));
    }

    @Test
    public void testAddTaskNullThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(null));
    }

    @Test
    public void testDeleteTaskSuccess() {
        Mockito.when(repositoryMock.existsById("123")).thenReturn(true);

        service.deleteTask("123");
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("123");
    }

    @Test
    public void testUpdateTaskNameSuccess() {
        Task task = new Task("123", "Old Name", "Description");
        Mockito.when(repositoryMock.findById("123")).thenReturn(Optional.of(task));

        service.updateTaskName("123", "New Name");

        Assertions.assertEquals("New Name", task.getName());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(task);
    }

    @Test
    public void testUpdateTaskDescriptionSuccess() {
        Task task = new Task("123", "Name", "Old Description");
        Mockito.when(repositoryMock.findById("123")).thenReturn(Optional.of(task));

        service.updateTaskDescription("123", "New Description");

        Assertions.assertEquals("New Description", task.getDescription());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(task);
    }
}