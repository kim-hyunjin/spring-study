package config;

import aspect.ExeTimeAspect2;
import chap07.Calculator;
import chap07.ImpCalculator;
import chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // <aop:aspectj-autoproxy>태그와 마찬가지로 @Aspect 애노테이션을 적용한 클래스를 빈으로 등록한다.
public class JavaConfig {

    @Bean
    public ExeTimeAspect2 exeTimeAspect() {
        return new ExeTimeAspect2();
    }

    @Bean
    public Calculator impCal() {
        return new ImpCalculator();
    }

    @Bean
    public Calculator recCal() {
        return new RecCalculator();
    }
}
