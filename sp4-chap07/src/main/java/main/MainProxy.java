package main;

import chap07.ExeTimeCalculator;
import chap07.ImpCalculator;
import chap07.RecCalculator;

public class MainProxy {

    public static void main(String[] args) {
        ExeTimeCalculator ttCal1 = new ExeTimeCalculator(new ImpCalculator());
        System.out.println(ttCal1.factorial(20));

        ExeTimeCalculator ttCal2 = new ExeTimeCalculator(new RecCalculator());
        System.out.println(ttCal2.factorial(20));

    }
}
/* 프록시 패턴
 * 프록시의 특징은 핵심 기능은 구현하지 않는다는 점이다.
 * 대신 여러 객체에 공통으로 적용될 수 있는 기능을 구현한다.
 *
 * AOP의 기본 개념은 핵심 기능에 공통 기능을 삽입하는 것이다.(핵심 기능의 코드를 수정하지 않으면서)
 * 방법 1. 컴파일 시점에 코드에 공통 기능을 추가
 * 방법 2. 클래스 로딩 시점에 바이트 코드에 공통 기능을 추가
 * 방법 3. 런타임에 프록시 객체를 생성해서 공통 기능을 추가(널리 사용되는 방법)
 *
 * 스프링 AOP는 프록시 객체를 자동으로 만들어 준다.
 */

