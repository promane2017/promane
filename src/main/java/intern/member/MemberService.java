package intern.member;

import intern.project.Project;
import intern.project.ProjectRepository;
import intern.user.User;
import intern.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;

    public Member add(boolean root, String userId, Integer projectId) {
        // user check
        User user = userRepository.findOne(userId);
        Project project = projectRepository.findOne(projectId);

        if (isNotExistenceUserAndProject(user, project))
            return null;

        // すでにmemberかチェック
        if (isAlreadyMember(userId, projectId))
            return null;

        return memberRepository.save(new Member(null, root, project, user));
    }

    public void delete(Integer id) {
        memberRepository.delete(id);
    }

    public Member findOne(Integer id) {
        return memberRepository.findOne(id);
    }

    public List<Member> getMembersByProjectId(Integer id) {
        return memberRepository.findByProjectId(id);
    }

    public Member getMemberByLoginUser(String userId, Integer projectId) {
        List<Member> members = memberRepository.findByProjectId(projectId);
        for (Member member : members) {
            if (member.getUser().getId().equals(userId))
                return member;
        }
        return null;
    }

    private boolean isNotExistenceUserAndProject(User user, Project project) {
        return user == null || project == null;
    }

    private boolean isAlreadyMember(String userId, Integer projectId) {
        List<Member> members = memberRepository.findByUserId(userId);
        for (Member member : members) {
            if (member.getProject().getId().equals(projectId))
                return true;
        }
        return false;
    }


}
