package intern.notice;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by --- on 17/09/05.
 */
@Service
@Transactional
public class NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    public List<Notice> getNotice(String id) {
        Pageable topfifty = new PageRequest(0, 50);
        List<Notice> notices = noticeRepository.findByuserIdOrderByCreatedAtDesc(id, topfifty);
        setAlreadyRead(notices);
        return notices;
    }

    public List<Notice> getUnReadNotice(String id) {
        List<Notice> notices = noticeRepository.findByUserIdAndUnReadTrueOrderByCreatedAtDesc(id);
        setAlreadyRead(notices);
        return notices;
    }

    private void setAlreadyRead(List<Notice> notices) {
        notices.forEach(notice -> { notice.setUnRead(false); });
    }

    public Notice create(Notice notice) {
        return noticeRepository.save(notice);
    }

    public void delete(Integer id) {
        noticeRepository.delete(id);
    }

}
