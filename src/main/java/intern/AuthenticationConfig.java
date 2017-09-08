package intern;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@EnableWebSecurity
/**
 *
 * Spring SecurityのConfigファイル
 * セキュリティのスコープやログインの各種設定を行うファイル
 * .defaultSuccessUrl（ログイン成功時のパス）は仮のものを入力中
 *
 */
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception{
		//セキュリティを適用しないリクエストの設定
		web.ignoring().antMatchers("/webjars/**","/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
					//認証されていないユーザがアクセスできるパスの設定：loginForm/ログイン画面、/users/create：登録処理
					.antMatchers("/loginForm","/users","/users/create", "/h2-console").permitAll()
					.anyRequest().authenticated()
				.and()
					.headers()
						.frameOptions().sameOrigin()
				.and()
				.formLogin()
				//ログイン処理を実行するパスや成功時失敗時のリアクションを記述（P.129,130）
				.loginProcessingUrl("/login")
					.loginPage("/loginForm")
					.defaultSuccessUrl("/successfull_test",true)
					.usernameParameter("username").passwordParameter("password")
				.and()
				.csrf().disable()
			.logout()
				//ログアウト時に遷移するパスの指定
				.logoutUrl("/logout")
				.logoutSuccessUrl("/loginForm")
				.and()
				.csrf().disable();
	}

	//パスワードのハッシュ化の設定
	@Bean
	PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

}
