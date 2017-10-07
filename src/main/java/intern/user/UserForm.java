package intern.user;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;


@Data
public class UserForm {
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[a-zA-Z0-9-_]*" , message = "IDは半角英数字とアンダーバー、ハイフンのみ使用できます")
	private String id;

	@NotNull
	@Size(min = 1, max = 30)
	private String name;
	
	@NotNull
	@Size(min = 1, max = 1000)
	private String password;
}
