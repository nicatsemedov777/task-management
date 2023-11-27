package az.iktlab.taskmanagement.dao;

import az.iktlab.taskmanagement.dao.generator.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Category {
    @Id
    @GeneratedValue(generator = "id-generator")
    @GenericGenerator(name = "id-generator", type = IdGenerator.class)
    private String id;

    private String name;
}
