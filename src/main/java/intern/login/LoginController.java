package intern.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * ログインフォームへのGETリクエストに対するレスポンス
 * ビュー(loginForm.html)を返す
 *
 */
@Controller

//localhost:8080/loginFormへGETリクエストが送られた時にloginForm.htmlを返す
public class LoginController {

	@GetMapping(path = "loginForm")
	String loginForm() {
		//src/main/resources/templates/以下にアクセスし、returnで受け取ったビューを返す
		return "loginForm";
	}
}
