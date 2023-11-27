package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.enums.TaskPriority;
import az.iktlab.taskmanagement.dao.enums.TaskStatus;
import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Task {

    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
