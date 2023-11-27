package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Account {
    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    private String username;
    private String email;
    private String password;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updatedAt;
}
