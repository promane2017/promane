package intern.member;

import javax.persistence.*;
import intern.project.Project;
import intern.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column
    private Boolean root;
    
    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(nullable = false)
    private Project project;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false)
    private User user;
}