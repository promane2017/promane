package intern.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by --- on 17/08/29.
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
