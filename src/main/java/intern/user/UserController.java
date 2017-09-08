package intern.user;

import intern.notice.Notice;
import intern.notice.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;
    @Autowired
    NoticeService noticeService;

	@ModelAttribute
	UserForm setUpForm() {
		return new UserForm();
	}
	
	@GetMapping
	String list(Model model) {
		List<User> users = new ArrayList<User>();
		User user = userService.findUser(userService.getLoggedInUserId());
		if(user != null && user.getRole().equals("ADMIN_USER"))  users = userService.findUser();	
		model.addAttribute("users", users);
		return "users/list";
	}
	
	@PostMapping(path = "create")
	String create(@Validated UserForm form, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return list(model);
		}
		User user = new User();
		user.setId(form.getId());
		user.setName(form.getName());
		user.setHashedPassword(new Pbkdf2PasswordEncoder().encode(form.getPassword()));
		user.setRole("ROLE_USER");
		User check = userService.create(user);
		if(check == null) {
			return "redirect:/users?error";
		}
        noticeService.create(new Notice(check.getId(), "ぷろまね。ちゃんへようこそ"));

		//作成に成功したらsuccessパラメータを付加してリダイレクト
		return "redirect:/loginForm?success";
	}

	@GetMapping(path = "edit", params = "form")
	String editForm(@RequestParam String id, User form) {
		User user = userService.findUser(id);
		BeanUtils.copyProperties(user, form);
		return "users/edit";
	}

	@PostMapping(path = "edit")
	String edit(@RequestParam String id, @Validated User form, BindingResult result){
		if (result.hasErrors()) {
			return editForm(id, form);
		}
		User user = userService.findUser(id);
		user.setName(form.getName());
		userService.update(user);
		return "redirect:/users";
	}

	@RequestMapping(path = "edit", params = "goToTop")
	String goToTop(){
		return "redirect:/users";
	}

	@PostMapping(path = "delete")
	String delete(@RequestParam String id) {
		User user = userService.findUser(userService.getLoggedInUserId());
		if(user != null && user.getRole().equals("ADMIN_USER")) userService.delete(id);
		return "redirect:/users";
	}
}
