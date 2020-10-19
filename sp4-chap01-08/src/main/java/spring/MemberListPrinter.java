package spring;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class MemberListPrinter {

    private final MemberDao memberDao;
    private final MemberPrinter memberPrinter;

    @Autowired
    public MemberListPrinter(MemberDao memberDao, MemberPrinter memberPrinter) {
        this.memberDao = memberDao;
        this.memberPrinter = memberPrinter;
    }

    public void printAll() {
        Collection<Member> members = memberDao.selectAll();
        for(Member m : members) {
            memberPrinter.print(m);
        }
    }
}
