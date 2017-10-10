package intern.member;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by --- on 17/08/30.
 */
@Data
public class MemberForm {
	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[a-zA-Z0-9-_]*" , message = "ユーザIDは半角英数字とアンダーバー、ハイフンのみの組み合わせです")
	private String userId;
}
