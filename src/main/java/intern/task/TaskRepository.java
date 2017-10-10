package intern.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by --- on 17/08/29.
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	@Query("SELECT t FROM Task t WHERE t.project.id = :projectId")
	List<Task> findByProjectId(@Param("projectId")Integer id);
}
