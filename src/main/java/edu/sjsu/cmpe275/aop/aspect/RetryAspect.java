import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

import java.io.IOException;

@Aspect
@Order(0)
public class RetryAspect {

    Integer retryCount = 0;

    @Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
    public void retryAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            if (retryCount > 3)
                notifyRetryFailure(joinPoint);
            else {
                joinPoint.proceed();
            }
        } catch (IOException e1) {
            retryCount++;
            System.out.println("Failed attempt " + retryCount + ".");
            if(retryCount<=3)
                System.out.println("Retrying...");
            this.retryAdvice(joinPoint);
        } catch (IllegalArgumentException e) {
            //System.out.println("Tweet length is more than 140 !");
            //e.printStackTrace();
            System.out.printf("Aborted the execution of the method %s\n", joinPoint.getSignature().getName());
            resetRetryCount();
        }
        resetRetryCount();
    }

    private void notifyRetryFailure(ProceedingJoinPoint joinPoint) {
        System.out.printf("Aborted the execution of the method %s  as Max retry attempts already made!\n ", joinPoint.getSignature().getName());
    }

    private void resetRetryCount() {
        retryCount = 0;
    }
}
