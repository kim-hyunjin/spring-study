package sp4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import sp4.entity.command.ChangePwdCommand;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sp4.entity.command.AuthInfo;
import sp4.service.ChangePasswordService;
import sp4.exception.IdPasswordNotMatchingException;
import sp4.validator.ChangePwdCommandValidator;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @RequestMapping(method = RequestMethod.GET)
    public String form(@ModelAttribute("command") ChangePwdCommand pwdCmd) {
        return "edit/changePwdForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute("command") ChangePwdCommand pwdCmd, Errors errors, HttpSession session) {
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if(errors.hasErrors()) {
            return "edit/changePwdForm";
        }
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        try {
            changePasswordService.changePassword(authInfo.getEmail(), pwdCmd.getCurrentPassword(), pwdCmd.getNewPassword());
            return "edit/changedPwd";
        } catch (IdPasswordNotMatchingException ex) {
            errors.rejectValue("currentPassword", "notMatching");
            return "edit/changePwdForm";
        }
    }
}
