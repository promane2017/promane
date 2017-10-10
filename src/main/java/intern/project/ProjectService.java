package intern.project;

import intern.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by --- on 17/08/25.
 */
@Service
@Transactional
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberRepository memberRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    public List<Project> getProjectsByUserId(String id) {
        return memberRepository.findByUserId(id)
               .stream()
               .map(member -> { return member.getProject(); })
               .collect(Collectors.toList());
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project findProject(Integer id) {
        return  projectRepository.findOne(id);
    }

    public void delete(Integer id) {
        projectRepository.delete(id);
    }


}
