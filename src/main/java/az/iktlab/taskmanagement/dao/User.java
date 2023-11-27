package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

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
    private String address;

    @Column(name = "birth_date")
    private LocalDateTime birthAt;

    @Column(name = "contact_number")
    private String contactNumber;

    @OneToMany
    private Set<Task> taskSet;
}
