package intern.task;

import intern.notice.*;
import intern.project.*;
import intern.member.*;
import intern.request.*;
import intern.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by --- on 17/08/29.
 */
@Controller
@RequestMapping("projects/{projectId}")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TaskuserService taskuserService;
    @Autowired
    MemberService memberService;
    @Autowired
    RequestService requestService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    ProjectService projectService;

    @GetMapping("tasks/index")
    String list(Model model, @PathVariable("projectId") Integer projectId) {
        List<Task> tasks = taskService.findTask(projectId);
        model.addAttribute("tasks", tasks);

        Project project = projectService.findProject(projectId);
        model.addAttribute("pm", project.isManager(userService.getLoggedInUserId()));
        return "tasks/task_index";
    }

    @PostMapping(path = "tasks/create")
    String create(
            @Validated TaskForm form,
            BindingResult result,
            Model model,
            @PathVariable("projectId") Integer projectId) {
        if (result.hasErrors()) {
            return null;
        }
        Task task = new Task();
        task.setName(form.getName());
        task.setDescription(form.getDescription());
        task.setImportance(form.getImportance());
        task.setDeadline(form.getDeadline());
        if (taskService.findTask(projectId) != null) {
            task.setProject(projectService.findProject(projectId));
        } else {
            return "redirect:/loginForm";
        }
        taskService.create(task);
        memberRepository.findByProjectId(projectId)
                .stream()
                .map(member->{return member.getUser();})
                .forEach(user->{
                    noticeService.create(new Notice(user.getId(),
                            "<a href=\"/projects/"+projectId+"/tasks/index\">"
                    +task.getProject().getName()+"にタスクが追加されました</a>"));
                });
        //作成に成功したらsuccessパラメータを付加してリダイレクト
        return "redirect:index?success";
    }

    @RequestMapping("tasks/{taskId}/assignees")
    @GetMapping
    String getMemberList(Model model,
                         @PathVariable("projectId") Integer projectId,
                         @PathVariable("taskId") Integer taskId) {

        // root check
        String loginedId = userService.getLoggedInUserId();
        if (!userService.checkRoot(loginedId, projectId)) {
            return "projects/tasks/not_root";
        }

        List<User> assignedUsers = taskService.findById(taskId).getUserList();
        model.addAttribute("assignedUser", assignedUsers);

        List<User> projectMembers = memberRepository.findByProjectId(projectId).stream()
                .map(member -> member.getUser())
                .collect(Collectors.toList());
        projectMembers.removeAll(assignedUsers);
        model.addAttribute("projectMember", projectMembers);

        //requestメンバー
        List<Request> requests = requestService.findRequest(taskId);
        model.addAttribute("requests", requests);
        return "projects/tasks/assignees";
    }

    @PostMapping(path = "tasks/{taskId}/assignees/delete")
    String deleteTaskUser(@RequestParam String userId, @PathVariable("projectId") Integer projectId,
        @PathVariable("taskId") Integer taskId) {
        taskService.deleteUser(userId, taskId);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId + "/assignees";
    }

    @PostMapping(path = "tasks/{taskId}/assignees/assign")
    String userAssignToTask(@RequestParam String userId,
                            @PathVariable("projectId") Integer projectId,
                            @PathVariable("taskId") Integer taskId) {
        taskService.assignUser(userId, taskId);
        requestService.deleteRequest(userId,taskId);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId + "/assignees";
    }

    @GetMapping(path = "task")
    String taskCreate(@PathVariable("projectId") Integer projectId) {
        return "tasks/task_create";
    }

    @GetMapping(path = "tasks/{taskId}/edit")
    String taskEdit(@PathVariable("projectId") Integer projectId,
        @PathVariable("taskId") Integer taskId, Model model) {
        Task task = taskService.findById(taskId);
        Integer progress = task.getProgress();
        model.addAttribute("progress", progress);

        List<Comment> comments = task.getComments();
        String logged_in_user_id = userService.getLoggedInUserId();
        model.addAttribute("logged_in_user_id", logged_in_user_id);
        model.addAttribute("comments", comments);

        Project project = projectService.findProject(projectId);
        model.addAttribute("pm", project.isManager(userService.getLoggedInUserId()));
        return "tasks/edit";
    }
    @PostMapping("tasks/delete")
    String taskDelete(@PathVariable Integer projectId, @RequestParam Integer id) {
        taskService.deleteTask(id);
        return "redirect:index";
    }

    @PostMapping(path = "tasks/{taskId}/update")
    String update(@Validated TaskEditForm form, BindingResult result,
                  @PathVariable("projectId") Integer projectId,//TODO:
                  @PathVariable("taskId") Integer taskId, Model model) {
        if (result.hasErrors()) {
            return null;
        }
        String logged_in_user_id = userService.getLoggedInUserId();
        model.addAttribute("logged_in_user_id", logged_in_user_id);

        if (taskuserService.isAlreadyAssigenedUser(logged_in_user_id, taskId)) {
            taskService.update(form, taskId);
        }
        Integer progress = taskService.findProgress(taskId);
        model.addAttribute("progress", progress);
        List<Comment> comments = taskService.findComment(taskId);
        model.addAttribute("comments", comments);
        taskService.update(form, taskId);
        return "tasks/edit";
    }

    @PostMapping(path = "tasks/{taskId}/requests")
    String taskRequest(@PathVariable("projectId") Integer projectId,
                       @PathVariable("taskId") Integer taskId) {
        User user = userService.findUser(userService.getLoggedInUserId());
        if (!requestService.isAlreadyRequest(user.getId(), taskId)) {
            Request request = new Request();
            request.setTask(taskService.findById(taskId));
            request.setUser(userService.findUser(userService.getLoggedInUserId()));
            requestService.create(request);
            Notice notice = new Notice();
            List<Member> MemberList = memberService.getMembersByProjectId(projectId);
            for (Member member : MemberList) {
                if (userService.checkRoot(member.getUser().getId(), projectId)) {
                    notice.setUserId(member.getUser().getId());
                    notice.setMessage("タスク申請が来ています");
                    notice.setCreatedAt(new Date());
                    notice.setUnRead(true);
                    noticeService.create(notice);
                }
            }
        }
        return "redirect:/projects/{projectId}/tasks/index?success_request";
    }
    @PostMapping(path = "tasks/{taskId}/assignees/request_delete")
    String deleteRequest(@RequestParam String userId,
                          @PathVariable("projectId") Integer projectId,
                          @PathVariable("taskId") Integer taskId) {
        requestService.deleteRequest(userId,taskId);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId + "/assignees";
    }
}
