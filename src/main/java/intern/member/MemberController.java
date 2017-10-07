package intern.member;

import intern.task.*;
import intern.request.*;
import intern.project.*;
import intern.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/members")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    TaskService taskService;
    @Autowired
    RequestService requestService;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;

    @ModelAttribute
    MemberForm setUpForm() {
        return new MemberForm();
    }

    @GetMapping
    String list(Principal principal, Model model, @PathVariable("projectId") Integer projectId) {
        Member loginMember = memberService.getMemberByLoginUser(principal.getName(), projectId);
        List<Member> members = memberService.getMembersByProjectId(projectId);
        model.addAttribute("members", members);
        model.addAttribute("projectId", projectId);
        model.addAttribute("loginMember", loginMember);
        return "members/edit";
    }
    
    @GetMapping("taskList/{userId}")
    String taskList(Model model, @PathVariable("projectId") Integer projectId, @PathVariable("userId") String userId) {
    		//アサイン済みのタスクリストを作成
    		List<Task> tasks = taskService.findTask(projectId);
    		List<Task> assignedTaskList = new ArrayList<Task>();
            for(Task task: tasks) {
            		if(taskService.isAlreadyAssigenedUser(userId, task.getId())) {
            			assignedTaskList.add(task);
            		}
            }
        model.addAttribute("assignedTaskList", assignedTaskList);
        return "members/task_assigned_list";
    }

    @PostMapping(path = "add")
    String add(@Validated MemberForm form, BindingResult result, Principal principal, Model model,
        @PathVariable("projectId") Integer projectId) {
        if (result.hasErrors()) return list(principal, model, projectId);
        // PM判定
        if(!projectService.findProject(projectId).isManager(userService.getLoggedInUserId())) return "errors/not_root";

        if (memberService.add(false, form.getUserId(), projectId) == null) {
            return "redirect:/projects/{projectId}/members?error";
        }

        return "redirect:/projects/{projectId}/members?success";
    }

    @PostMapping(path = "delete")
	String delete(@RequestParam Integer memberId, @PathVariable("projectId") Integer projectId) {
        // PM判定
        if(!projectService.findProject(projectId).isManager(userService.getLoggedInUserId())) return "errors/not_root";
    	
    		Member member = memberService.findOne(memberId);
    		//プロジェクトからメンバーを削除するときはタスク・リクエストからも消す必要がある
    		String userId = member.getUser().getId();
    		for(Task task: member.getProject().getTaskList()) {
    			Integer taskId = task.getId();
    			if(taskService.isAlreadyAssigenedUser(userId, taskId)) {
    				taskService.deleteUser(userId, taskId);
    			}
    			if(requestService.isAlreadyRequest(userId, taskId)) {
    				requestService.deleteRequest(userId, taskId);
    			}
    		}
        memberService.delete(memberId);
        return "redirect:/projects/{projectId}/members";
    }
}
