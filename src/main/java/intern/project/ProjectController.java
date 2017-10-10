package intern.project;

import intern.member.*;
import intern.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by --- on 17/08/25.
 */

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;

    @GetMapping(path = "/projects/entry")
    String list(Model model) {
        return "projects/entry";
    }

    @PostMapping(path = "/projects/create")
    String create(@Validated Project form, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            return list(model);
        }
        Project project = new Project(null, form.getName(), form.getDescription());
        Project createdProject = projectService.create(project);
        memberService.add(true, principal.getName(), createdProject.getId());
        BeanUtils.copyProperties(form, project);

        return "redirect:/projects";
    }

    @GetMapping(path = "/projects")
    String list(Principal principal, Model model) {
        //参加プロジェクト一覧
        List<Project> projects = projectService.getProjectsByUserId(principal.getName());
        model.addAttribute("projects", projects);

        //それぞれのプロジェクトのPM判定
        List<Boolean> pms = new ArrayList<>();
        for (Project p : projects) pms.add(p.isManager(userService.getLoggedInUserId()));
        model.addAttribute("pm", pms);

        //admin判定
        User user = userService.findUser(userService.getLoggedInUserId());
        if (user != null && user.getRole().equals("ADMIN_USER")) model.addAttribute("admin", true);
        else model.addAttribute("admin", false);
        return "projects/list";
    }

    @PostMapping(path = "/projects/delete")
    String delete(@RequestParam Integer id) {
        // PM判定
        if (!projectService.findProject(id).isManager(userService.getLoggedInUserId())) return "errors/not_root";
        String userId = userService.getLoggedInUserId();
        Member member = memberService.getMemberByLoginUser(userId, id);
        if (member.getRoot()) projectService.delete(id);
        return "redirect:/projects";
    }
}
