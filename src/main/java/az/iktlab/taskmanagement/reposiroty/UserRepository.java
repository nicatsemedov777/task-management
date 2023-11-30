package az.iktlab.taskmanagement.reposiroty;

import az.iktlab.taskmanagement.dao.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByEmailAndIsDeletedFalse(String email);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    Optional<User> findByUsernameAndIsDeletedFalse(String username);



}
