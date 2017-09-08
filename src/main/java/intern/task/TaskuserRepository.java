package intern.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by --- on 17/09/06.
 */
public interface TaskuserRepository extends JpaRepository<Taskuser, Integer > {
    @Query("SELECT t FROM Taskuser t WHERE t.user.id = :userId")
    List<Taskuser> findTaskIdByUserId(@Param("userId")String id);
}
