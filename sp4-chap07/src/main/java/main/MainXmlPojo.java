package main;

import chap07.Calculator;
import chap07.ImpCalculator;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainXmlPojo {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:aopPojo.xml");

        // 스프링은 AOP를 위한 프록시 객체를 생성할 때 실제 생성할 빈 객체가 인터페이스를 상속하고 있으면, 인터페이스를 이용해서 프록시를 생성한다.
        // 따라서 아래 코드는 에러 발생
        // Bean named 'impCal' must be of type [chap07.ImpCalculator], but was actually of type [com.sun.proxy.$Proxy5]
        // 인터페이스가 아닌 클래스를 이용해서 프록시를 생성하고 싶다면?
        // proxy-target-class="true"로 설정해주면 된다.
        Calculator impCal = ctx.getBean("impCal", ImpCalculator.class);
        long fiveFact1 = impCal.factorial(5);
        System.out.println("impCal.factorial(5) = " + fiveFact1);

        Calculator recCal = ctx.getBean("recCal", Calculator.class);
        long fiveFact2 = recCal.factorial(5);
        System.out.println("impCal.factorial(5) = " + fiveFact2);
    }

}
