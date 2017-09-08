package intern.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by --- on 17/09/06.
 */
@Service
public class TaskuserService {
    @Autowired
    TaskuserRepository taskuserRepository;

    public boolean isAlreadyAssigenedUser(String loggedInUserId, Integer taskId){
        List<Taskuser> taskuserList=taskuserRepository.findTaskIdByUserId(loggedInUserId);
        for (Taskuser taskuser: taskuserList) {
            if(taskuser.getTask().getId().equals(taskId)){
                return true;
            }
        }
        return false;
    }
}
