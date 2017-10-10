package intern.member;

import java.util.List;
import intern.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	public List<Member> findByUserId(String id);
	public List<Member> findByProjectId(Integer id);
}
