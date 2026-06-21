package projectone.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectone.exception.DuplicateEntityException;
import projectone.exception.EntityNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final Map<String, Task> memoryCache = new ConcurrentHashMap<>();

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task record cannot be null.");
        }

        String id = task.getTaskId();

        if (memoryCache.containsKey(id) || taskRepository.existsById(id)) {
            throw new DuplicateEntityException("Task ID already exists.");
        }

        taskRepository.save(task);
        memoryCache.put(id, task);
    }

    public void deleteTask(String taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null.");
        }

        boolean exists = Optional.ofNullable(memoryCache.remove(taskId)).isPresent()
                || taskRepository.existsById(taskId);

        if (!exists) {
            throw new EntityNotFoundException("Task ID not found.");
        }

        taskRepository.deleteById(taskId);
    }

    public void updateTaskName(String taskId, String newName) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null.");
        }

        Task task = Optional.ofNullable(memoryCache.get(taskId))
                .or(() -> taskRepository.findById(taskId))
                .orElseThrow(() -> new EntityNotFoundException("Task ID not found."));

        task.setName(newName);

        taskRepository.save(task);
        memoryCache.put(taskId, task);
    }

    public void updateTaskDescription(String taskId, String newDescription) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null.");
        }

        Task task = Optional.ofNullable(memoryCache.get(taskId))
                .or(() -> taskRepository.findById(taskId))
                .orElseThrow(() -> new EntityNotFoundException("Task ID not found."));

        task.setDescription(newDescription);

        taskRepository.save(task);
        memoryCache.put(taskId, task);
    }

    @Transactional(readOnly = true)
    public Task getTask(String taskId) {
        if (taskId == null) {
            return null;
        }
        return Optional.ofNullable(memoryCache.get(taskId))
                .or(() -> taskRepository.findById(taskId))
                .orElse(null);
    }

    public void clearMemoryCache() {
        this.memoryCache.clear();
    }
}