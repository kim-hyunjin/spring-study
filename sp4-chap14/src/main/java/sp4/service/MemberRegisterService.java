package sp4.service;

import org.springframework.beans.factory.annotation.Autowired;
import sp4.exception.AlreadyExistingMemberException;
import sp4.entity.domain.Member;
import sp4.dao.MemberDao;
import sp4.entity.command.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberDao memberDao;

	public void regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new AlreadyExistingMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(),
				new Date());
		memberDao.insert(newMember);
	}
}
