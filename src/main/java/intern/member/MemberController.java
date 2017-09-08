package intern.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/members")
public class MemberController {
    @Autowired
    MemberService memberService;

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

    @PostMapping(path = "add")
    String add(@Validated MemberForm form, BindingResult result, Principal principal, Model model,
        @PathVariable("projectId") Integer projectId) {
        if (result.hasErrors()) {
            return list(principal, model, projectId);
        }

        if (memberService.add(false, form.getUserId(), projectId) == null) {
            return "redirect:/projects/{projectId}/members?error";
        }

        return "redirect:/projects/{projectId}/members?success";
    }

    @PostMapping(path = "delete")
    String delete(@RequestParam Integer memberId) {
        memberService.delete(memberId);
        return "redirect:/projects/{projectId}/members";
    }
}
