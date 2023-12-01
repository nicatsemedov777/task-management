package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;

    @Column(name = "birth_date")
    private LocalDateTime birthAt;

    @Column(name = "contact_number")
    private String contactNumber;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updatedAt;

    @Builder.Default
    private Boolean isDeleted = false;
}
