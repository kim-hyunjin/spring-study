package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {

	private MemberDao memberDao;

	public ChangePasswordService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();
		
		member.changePassword(oldPwd, newPwd);
		
		memberDao.update(member);
	}
	// 스프링은 @Transactional 애노테이션을 이용해서 트랜잭션을 처리하기 위해 내부적으로 AOP를 사용한다.
	// @Transactional을 처리하기 위한 프록시 객체는 원본 객체의 메서드를 실행하는 과정에서 RuntimeException 및 그 하위 타입의 익셉션이 발생하면 트랜잭션을 롤백한다.
	// SQLException의 경우는 RuntimeException을 상속하고 있지 않기 때문에, SQLException이 발생하면 롤백하지 않는다.
	// => 이 경우에도 롤백하고 싶다면 @Transactional의 rollbackFor 속성을 사용하면 된다.
	// 그 반대 기능을 하는 noRollbackFor 속성도 있다.
}
