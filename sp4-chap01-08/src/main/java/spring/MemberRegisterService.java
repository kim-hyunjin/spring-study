package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class MemberRegisterService {

    private MemberDao memberDao;

    @Autowired
    public MemberRegisterService(MemberDao memberDao) {
        System.out.println("MemberDao 주입: "+ memberDao);
        this.memberDao = memberDao;
    }

    public void resist(RegisterRequest req) {
        Member member = memberDao.selectByEmail(req.getEmail());
        if(member != null) {
            throw new AlreadyExistingMemberException("이미 존재하는 회원입니다.");
        }
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), new Date());
        memberDao.insert(newMember);
    }
}
