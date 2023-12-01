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
public class TaskUpdateRequest {
    private String name;
    private String priority;
    private String status;
    private String description;
    private String categoryName;
}
