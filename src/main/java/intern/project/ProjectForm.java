package intern.project;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by --- on 17/08/25.
 */
@Data
public class ProjectForm {
	@NotNull
	@Size(min = 1, max = 20)
	private String project_name;
	@NotNull
	@Size(min = 1, max = 200)
	private String project_description;
}
