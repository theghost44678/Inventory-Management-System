package projectone.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        // Strict boundary protection added to prevent NullPointerException crashes
        if (task == null) {
            throw new IllegalArgumentException("Task record cannot be null.");
        }
        String id = task.getTaskId();
        if (taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task ID already exists.");
        }
        taskRepository.save(task);
    }

    public void deleteTask(String taskId) {
        if (taskId == null || !taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task ID not found.");
        }
        taskRepository.deleteById(taskId);
    }

    public void updateTaskName(String taskId, String newName) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task ID not found."));
        
        task.setName(newName);
        taskRepository.save(task);
    }

    public void updateTaskDescription(String taskId, String newDescription) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task ID not found."));
        
        task.setDescription(newDescription);
        taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTask(String taskId) {
        if (taskId == null) {
            return null;
        }
        return taskRepository.findById(taskId).orElse(null);
    }
}