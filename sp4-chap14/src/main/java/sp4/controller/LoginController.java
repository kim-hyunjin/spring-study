package sp4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import sp4.entity.command.LoginCommand;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sp4.entity.command.AuthInfo;
import sp4.service.AuthService;
import sp4.exception.IdPasswordNotMatchingException;
import sp4.validator.LoginCommandValidator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    public String form(LoginCommand loginCommand, @CookieValue(value="REMEMBER", required = false) Cookie rCookie) {
        // REMEMBER라는 이름의 쿠키가 있으면 그 안에 담긴 이메일 값과 rememberEmail 상태 값을 loginForm에 전달.
        if(rCookie != null) {
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, HttpServletResponse response) {
        new LoginCommandValidator().validate(loginCommand, errors);
        if(errors.hasErrors()) {
            return "login/loginForm";
        }

        try {
            AuthInfo authInfo = authService.authenticate(loginCommand.getEmail(), loginCommand.getPassword());

            session.setAttribute("authInfo", authInfo);

            // rememberEmail 을 체크해 post요청한 경우, REMEMBER라는 이름으로 쿠키를 만들고 값으로 이메일을 저장.
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
            rememberCookie.setPath("/");
            if(loginCommand.isRememberEmail()) {
                rememberCookie.setMaxAge(60*60*24*30);
            } else {
                rememberCookie.setMaxAge(0);
            }
            response.addCookie(rememberCookie);

            return "login/loginSuccess";
        } catch (IdPasswordNotMatchingException ex) {
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";
        }
    }
}
