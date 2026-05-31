package projectone.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "task_id", length = 10, nullable = false)
    private String taskId;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 50;

    // Protected default constructor required by JPA/Hibernate proxying
    protected Task() {}

    public Task(String taskId, String name, String description) {
        if (taskId == null || taskId.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("Task ID must be non-null and <= " + MAX_ID_LENGTH + " characters.");
        }
        this.taskId = taskId;
        
        setName(name);
        setDescription(description);
    }

    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) {
        if (name == null || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name must be non-null and <= " + MAX_NAME_LENGTH + " characters.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description must be non-null and <= " + MAX_DESCRIPTION_LENGTH + " characters.");
        }
        this.description = description;
    }
}