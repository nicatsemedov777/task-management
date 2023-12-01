package az.iktlab.taskmanagement.reposiroty;

import az.iktlab.taskmanagement.dao.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    @Modifying
    @Transactional
    @Query("update Task t set t.isDeleted = true where t.category.name = ?1")
    void deleteAllByCategoryName(String categoryName);

    @Modifying
    @Transactional
    @Query("update Task t set t.isDeleted = true where t.id =?1")
    void deleteByIdSoft(String id);

    Optional<List<Task>> findAllByUserId(String userId);

    @Query("SELECT t from Task as t where t.category.name = ?1")
    Optional<List<Task>> findAllByCategoryName(String categoryName);
}
