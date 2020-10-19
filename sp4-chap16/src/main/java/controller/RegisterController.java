package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import exception.AlreadyExistingMemberException;
import service.MemberRegisterService;
import entity.command.RegisterRequest;
import validator.RegisterRequestValidator;

@Controller
public class RegisterController {
    private MemberRegisterService memberRegisterService;

    public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }

    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "register/step1";
    }

    @RequestMapping(value = "/register/step2", method = RequestMethod.GET)
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @RequestMapping(value = "/register/step2", method = RequestMethod.POST)
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false")Boolean agree, Model model) {
        if(!agree) {
            return "register/step1";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step2";
    }

    @RequestMapping(value = "/register/step3", method = RequestMethod.POST)
    public String handleStep3(RegisterRequest regReq, Errors errors) { //Errors 타입 파라미터는 반드시 커맨드 객체 다음에 위치해야한다.
        new RegisterRequestValidator().validate(regReq, errors);
        if(errors.hasErrors())
            return "register/step2";

        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (AlreadyExistingMemberException ex) {
            errors.rejectValue("email", "duplicate");
            return "register/step2";
        }

    }
}
