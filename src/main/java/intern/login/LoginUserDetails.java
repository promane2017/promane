package intern.login;

import lombok.Data;
import org.springframework.security.core.authority.AuthorityUtils;

import intern.user.User;

/**
 *
 * ユーザIDとパスワードを取得
 * ゲッタセッタはLombokを用いている
 * 認証そのものはSpring Securityで実装
 *
 */
@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
	private final User user;

	public LoginUserDetails(User user) {
		super(user.getId(), user.getHashedPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}

}
