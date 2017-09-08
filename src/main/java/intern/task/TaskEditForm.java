package intern.task;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by --- on 17/08/29.
 */
@Data
public class TaskEditForm {
    @NotNull
    private Integer progress;

    @Size(min = 0,max = 1000)
    private String comment;
}
