package intern.request;

import intern.task.Task;
import intern.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by --- on 17/08/29.
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
@Component
public class Request {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer id;
    //-----------------check [FetchType.Lazy]---------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "task_id")
    private Task task;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "task_id", referencedColumnName = "id") }//, insertable = false, updatable = false)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

}
