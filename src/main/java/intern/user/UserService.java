package intern.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import intern.member.*;

import java.util.List;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	MemberRepository memberRepository;

	public List<User> findUser() {
		return userRepository.findAll();
	}

	public User findUser(String id) {
		return userRepository.findOne(id);
	}

	public User create(User user) {
		User check = findUser(user.getId());
		if (check == null) {
			return userRepository.save(user);
		} else {
			return null;
		}
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public void delete(String id) {
		userRepository.delete(id);
	}

	public String getLoggedInUserId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public Boolean checkRoot(String userId, int projectId) {
		List<Member> memberList = memberRepository.findByUserId(userId);
		for (Member member : memberList) {
			if (member.getRoot() && member.getProject().getId().equals(projectId)) {
				return true;
			}
		}
		return false;

	}
}
