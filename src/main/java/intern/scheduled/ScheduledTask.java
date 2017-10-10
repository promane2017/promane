package intern.scheduled;

import intern.notice.Notice;
import intern.notice.NoticeRepository;
import intern.task.Task;
import intern.task.TaskRepository;
import intern.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.thymeleaf.util.DateUtils;

import java.util.Date;
import java.util.List;

public class ScheduledTask {
    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    TaskRepository taskRepository;

    //@Scheduled(cron = "0/5 * * * * *") // デバッグ用 5 sに一回通知をはく
    @Scheduled(cron = "0 0 0 * * *") // 本番用 日付変更時に通知をはく
    public void noticeDeadLine() {
        Date today = DateUtils.createToday().getTime();
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.getDeadline() == null) continue;
            long date = (task.getDeadline().getTime() - today.getTime()) / (24 * 60 * 60 * 1000);
            if (date > 3 || date < 0) continue;
            List<User> users = task.getUserList();
            for (User user : users) {
                noticeRepository.save(new Notice(user.getId(),
                                                 "<a href=\"/projects/" + task.getProject().getId() + "/tasks/" + task.getId() + "/edit\">" + task.getName() + "の期限まであと" + date + "日です</a>"));
            }
        }
    }
}
