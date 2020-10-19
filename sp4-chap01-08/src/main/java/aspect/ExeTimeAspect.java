package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class ExeTimeAspect {

    @Pointcut("execution(public * spring..*.print*(..))")
    public void publicTarget() {}

    @Around("publicTarget()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            System.out.printf("%s.%s(%s) 실행시간 : %d ms\n",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()),
                    finish - start);
        }
    }
}
