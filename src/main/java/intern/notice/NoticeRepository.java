package intern.notice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by --- on 17/09/05.
 */
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    public List<Notice> findByuserIdOrderByCreatedAtDesc(String id,Pageable pageable);
    public List<Notice> findByUserIdAndUnReadTrueOrderByCreatedAtDesc(String id);
}
