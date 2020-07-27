package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect
@Order(1)
public class ExeTimeAspect2 {

    // execution(수식어패턴(public)? 리턴타입패턴 클래스이름패턴?메서드이름패턴(파라미터패턴))
    // * = 모든 값
    // .. = 0개 이상.
    @Pointcut("execution(public * chap07..*(..))") // chap07 패키지 및 하위 패키지에 있는, 파라미터 0개 이상인 메서드
    public void publicTarget() {}

    @Around("publicTarget()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.nanoTime();
            Signature sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }
}
