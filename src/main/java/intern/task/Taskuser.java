package intern.task;

import intern.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by --- on 17/09/06.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taskuser")
@Component
public class Taskuser {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "task_id")
    private Task task;
    //    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "task_id", referencedColumnName = "id") }//, insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
