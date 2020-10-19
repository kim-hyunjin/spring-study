package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    private static GenericXmlApplicationContext ctx = null;
    private static MemberDao memberDao = null;

    public static void main(String[] args) throws IOException {
        ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        memberDao = ctx.getBean("memberDao", MemberDao.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("명령어를 입력하세요");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                ctx.close();
                break;
            }else if(command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            }else if(command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            }else if(command.equals("list")) {
                processListCommand();
                continue;
            }else if(command.equals("client")) {
                Client client = ctx.getBean("client", Client.class);
                client.send();
                continue;
            }
            printHelp();
        }
    }

    private static void processListCommand() {
        int total = memberDao.count();
        System.out.println("전체 데이터: " + total);
        MemberListPrinter listPrinter = ctx.getBean("memberListPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processNewCommand(String[] s) {
        if(s.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest(s[1], s[2], s[3], s[4]);

        if(!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 재확인암호가 일치하지 않습니다.");
            return;
        }

        try {
            regSvc.resist(req);
            System.out.println("등록했습니다.\n");
        } catch (AlreadyExistingMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }

    }

    private static void processChangeCommand(String[] s) {
        if(s.length != 4) {
            printHelp();
            return;
        }

        ChangePasswordService changePasswordService = ctx.getBean("changePwdSvc", ChangePasswordService.class);

        try {
            changePasswordService.changePassword(s[1], s[2], s[3]);
            System.out.println("비밀번호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 회원입니다.\n");
        } catch (IdPasswordNotMatchingException e) {
            System.out.println("아이디와 비밀번호가 일치하지 않습니다.\n");
        }

    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다.");
        System.out.println("---명령어 사용법---");
        System.out.println("new 이메일 암호 암호확인 이름");
        System.out.println("change 이메일 현재비밀번호 새비밀번호");
        System.out.println();
    }
}
