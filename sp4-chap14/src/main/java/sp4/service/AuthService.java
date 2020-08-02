package sp4.service;

import org.springframework.beans.factory.annotation.Autowired;
import sp4.exception.IdPasswordNotMatchingException;
import sp4.exception.MemberNotFoundException;
import sp4.entity.command.AuthInfo;
import sp4.entity.domain.Member;
import sp4.dao.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private MemberDao memberDao;

    public AuthInfo authenticate(String email, String password) {
        Member member = memberDao.selectByEmail(email);
        if(member == null) {
            throw new MemberNotFoundException();
        }
        if(!member.getPassword().equals(password)) {
            throw new IdPasswordNotMatchingException();
        }
        return new AuthInfo(member.getId(), member.getEmail(), member.getName());
    }
}
