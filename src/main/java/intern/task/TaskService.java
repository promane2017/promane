package intern.task;

import intern.notice.*;
import intern.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserService userService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    NoticeRepository noticeRepository;

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findTask(Integer id) {
        return taskRepository.findByProjectId(id);
    }

    public void deleteTask(Integer id) {
        taskRepository.delete(id);
    }

    public Task findById(Integer id) {
        return taskRepository.findOne(id);
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void deleteUser(String userId, Integer taskId) {
        Task task = findById(taskId);
        task.deleteUser(userService.findUser(userId));
        update(task);
    }

    public void assignUser(String userId, Integer taskId) {
        Task task = findById(taskId);
        task.addUser(userService.findUser(userId));
        update(task);
    }

    public void update(TaskEditForm taskEditForm, Integer taskId) throws ParseException {
        Task task = taskRepository.findOne(taskId);
        task.setDescription(taskEditForm.getDescription());
        task.setProgress(taskEditForm.getProgress());
        //deadlineが設定されている場合
        if (!taskEditForm.getDeadline().equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date deadLine = sdf.parse(taskEditForm.getDeadline());
            task.setDeadline(deadLine);
        }
        taskRepository.save(task);

        if (!taskEditForm.getComment().isEmpty()) {
            Comment comment = new Comment();
            comment.setContent(taskEditForm.getComment());
            comment.setTask(task);
            comment.setCreatedAt(new Date());
            comment.setUser(userService.findUser(userService.getLoggedInUserId()));
            commentRepository.save(comment);

            task.getUserList().forEach(user -> {
                noticeRepository.save(new Notice(user.getId(),
                "<a href=\"/projects/" + task.getProject().getId() + "/tasks/" + task.getId()
                + "/edit\">" + task.getName() + "にコメントが追加されました</a>"));
            });
        }
    }

    public Integer findProgress(Integer task_id) {
        return taskRepository.findOne(task_id).getProgress();
    }

    public List<Comment> findComment(Integer task_id) {
        Task task = findById(task_id);
        List<Comment> comments = task.getComments();
        return comments;
    }

    public boolean isAlreadyAssigenedUser(String loggedInUserId, Integer taskId) {
        Task task = findById(taskId);
        List<User> assignedUsers = task.getUserList();
        for (User user : assignedUsers) if (user.getId().equals(loggedInUserId)) return true;
        return false;
    }
}
