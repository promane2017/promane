package intern.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by --- on 17/09/05.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RequestService {
    @Autowired
    RequestRepository requestRepository;

    public Request create(Request request){
        return requestRepository.save(request);
    }

    public void deleteRequest(String userId,Integer taskId){
        requestRepository.deleteByUserIdAndTaskId(userId,taskId);
    }

    public List<Request> findRequest(Integer id) {
        return requestRepository.findByTaskId(id);
    }

    public boolean isAlreadyRequest(String userId, Integer taskId) {
        List<Request> request_users = requestRepository.findByTaskId(taskId);
        for (Request request : request_users) {
            if (request.getUser().getId().equals(userId))
                return true;
        }
        return false;
    }
}
