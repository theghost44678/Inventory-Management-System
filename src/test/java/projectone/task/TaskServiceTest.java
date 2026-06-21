package projectone.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

import java.util.Optional;

public class TaskServiceTest {

    private TaskRepository repositoryMock;
    private TaskService service;

    @BeforeEach
    public void setup() {
        repositoryMock = Mockito.mock(TaskRepository.class);
        service = new TaskService(repositoryMock);
        service.clearMemoryCache();
    }

    @Test
    public void testAddTaskSuccess() {
        Task task = new Task("123", "Test Task", "This is a test.");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(false);

        service.addTask(task);

        Assertions.assertEquals(task, service.getTask("123"));
        Mockito.verify(repositoryMock, Mockito.times(1)).save(task);
    }

    @Test
    public void testAddDuplicateTaskFails() {
        Task task1 = new Task("123", "Task One", "First task.");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(true);

        // Milestone 4 Update
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.addTask(task1));
    }

    @Test
    public void testAddTaskNullThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(null));
    }

    @Test
    public void testDeleteTaskSuccess() {
        Task task = new Task("123", "Test Task", "This is a test.");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(false);

        service.addTask(task);
        service.deleteTask("123");

        Assertions.assertNull(service.getTask("123"));
        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById("123");
    }

    @Test
    public void testDeleteNonExistentTaskThrows() {
        Mockito.when(repositoryMock.existsById("missing")).thenReturn(false);

        // Milestone 4 Update
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteTask("missing"));
    }

    @Test
    public void testUpdateTaskNameSuccess() {
        Task task = new Task("123", "Old Name", "Description");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(false);
        service.addTask(task);

        service.updateTaskName("123", "New Name");

        Task updated = service.getTask("123");
        Assertions.assertEquals("New Name", updated.getName());
    }

    @Test
    public void testUpdateTaskNameOnMissingThrows() {
        Mockito.when(repositoryMock.findById("missing")).thenReturn(Optional.empty());

        // Milestone 4 Update
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.updateTaskName("missing", "Name"));
    }

    @Test
    public void testUpdateTaskDescriptionSuccess() {
        Task task = new Task("123", "Name", "Old Description");
        Mockito.when(repositoryMock.existsById("123")).thenReturn(false);
        service.addTask(task);

        service.updateTaskDescription("123", "New Description");

        Task updated = service.getTask("123");
        Assertions.assertEquals("New Description", updated.getDescription());
    }

    @Test
    public void testUpdateTaskDescriptionOnMissingThrows() {
        Mockito.when(repositoryMock.findById("missing")).thenReturn(Optional.empty());

        // Milestone 4 Update
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.updateTaskDescription("missing", "Desc"));
    }
}