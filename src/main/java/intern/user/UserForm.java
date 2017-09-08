package intern.user;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;


@Data
public class UserForm {
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[a-zA-Z0-9-_]*")
	private String id;

	@NotNull
	@Size(min = 1, max = 30)
	private String name;
	
	@NotNull
	@Size(min = 1, max = 1000)
	@Pattern(regexp = "[a-zA-Z0-9-_]*")
	private String password;
}
