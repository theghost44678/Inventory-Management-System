package projectone.task;

public class Task {
    private final String taskId;
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        if (taskId == null || taskId.length() > 10) {
            throw new IllegalArgumentException("Task ID must be non-null and <= 10 characters.");
        }
        if (name == null || name.length() > 20) {
            throw new IllegalArgumentException("Name must be non-null and <= 20 characters.");
        }
        if (description == null || description.length() > 50) {
            throw new IllegalArgumentException("Description must be non-null and <= 50 characters.");
        }

        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) {
        if (name == null || name.length() > 20) {
            throw new IllegalArgumentException("Name must be non-null and <= 20 characters.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > 50) {
            throw new IllegalArgumentException("Description must be non-null and <= 50 characters.");
        }
        this.description = description;
    }
}