package intern.task;

import intern.user.*;
import intern.project.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.lang.Integer;
import java.util.List;

/**
 * Created by --- on 17/08/25.
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String importance;

    private String description;

    private Integer progress;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "assignees", joinColumns = {
			@JoinColumn(name = "task_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") })
    private List<User> userList;

    @OneToMany(mappedBy="task", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne
    private Project project;

    public void addUser(User user) {
        userList.add(user);
    }

    public void deleteUser(User user) {
		int index = userList.indexOf(user);
		if (index != -1) userList.remove(index);
    }

    public Task() {
        this.progress = 0;
        this.createdAt = new Date();
    }
}
