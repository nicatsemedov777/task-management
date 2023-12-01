package az.iktlab.taskmanagement.model.request;

import az.iktlab.taskmanagement.dao.Category;
import az.iktlab.taskmanagement.dao.enums.TaskPriority;
import az.iktlab.taskmanagement.dao.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message ="Priority can not be null")
    private TaskPriority priority;

    @NotNull(message ="Task-Status can not be null")
    private TaskStatus status;

    @NotEmpty(message = "Category is required")
    private String categoryName;

    @JsonIgnore
    private Category category;

    @JsonIgnore
    private Boolean isDeleted =false;

    private String description;
    private LocalDateTime deadline;
}
