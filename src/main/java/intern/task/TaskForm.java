package intern.task;

import lombok.Data;
import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMax;

/**
 * Created by --- on 17/08/29.
 */
@Data
public class TaskForm {
    @Size(min = 1, max = 50)
    private String name;
	
    @Size(min = 0, max = 1000)
    private String description;
    private Date   deadline;
    @Pattern(regexp = "high|middle|low")
    private String importance;
    @DecimalMax("100")
    private int    progress;
}
