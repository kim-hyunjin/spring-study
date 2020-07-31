package service;

import exception.IdPasswordNotMatchingException;
import exception.MemberNotFoundException;
import entity.AuthInfo;
import entity.domain.Member;
import dao.MemberDao;

public class AuthService {
    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

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
