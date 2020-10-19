package spring;

import lombok.Getter;
import java.util.Date;

@Getter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Date registerDate;

    public Member(String email, String password, String name, Date registerDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDate = registerDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void changePassword(String oldPwd, String newPwd) {
        if(!password.equals(oldPwd)) {
            throw new IdPasswordNotMatchingException();
        }
        this.password = newPwd;
    }

}
