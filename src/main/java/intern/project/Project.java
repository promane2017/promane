package intern.project;

import intern.member.*;
import intern.task.*;

import java.lang.Integer;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    @Size(min = 1, max = 128)
    private String name;

    @Column(nullable = false)
    @Size(min = 1, max = 1000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @OneToMany(mappedBy="project", cascade = CascadeType.REMOVE)
    private List<Member> memberList;
    
    @OneToMany(mappedBy="project", cascade = CascadeType.REMOVE)
    private List<Task> taskList;
    
    public Project(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = new Date();
    }
	
	public Boolean isManager(String userId){
		List<Member> members = this.getMemberList();
        for(Member member: members)
            if(member.getRoot() && member.getUser().getId().equals(userId)) return true;
        return false;
	}
}