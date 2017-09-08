package intern.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request,Integer>{
    @Query("SELECT r FROM Request r WHERE r.task.id = :task_id")
    List<Request> findByTaskId(@Param("task_id")Integer id);

    @Modifying
    @Query("DELETE FROM Request r WHERE r.user.id = :user_id AND r.task.id = :task_id")
    public int  deleteByUserIdAndTaskId(@Param("user_id")String user_id, @Param("task_id")Integer task_id);
}
