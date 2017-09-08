package intern.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * ログイン成功時のビューをGETするリクエストに対するレスポンス
 * 現状successfull.htmlというビューを返している
 * 後々プロジェクト出力画面のビューを渡すように変更予定
 * ↑変更後は消してください
 *
 */
@Controller
public class LoginSuccessController {
	@GetMapping(path = "successfull_test")
	String loginForm() {
		return "redirect:projects";
	}
}
