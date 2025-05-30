package com.co.kc.shortening.infrastructure.aop;

import com.co.kc.shortening.application.client.LockClient;
import com.co.kc.shortening.application.annotation.Lock;
import com.co.kc.shortening.common.exception.ToastException;
import com.co.kc.shortening.infrastructure.utils.AopUtils;
import com.co.kc.shortening.infrastructure.utils.SpELUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author KIMVON
 * @since 2021/7/19
 */
@Aspect
@Component
public class LockAspect {

    private static final String LOCK_ASPECT_PREFIX = "lock_aspect";

    private final LockClient lockClient;

    public LockAspect(LockClient lockClient) {
        this.lockClient = lockClient;
    }

    @Pointcut("@annotation(com.co.kc.shortening.application.annotation.Lock)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取查询的key 连接点是在方法上的,所以可以强转成方法的签名信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取被注解注释的方法
        Method method = AopUtils.getMethod(joinPoint);

        Lock lock = method.getAnnotation(Lock.class);
        EvaluationContext context = SpELUtils.createContext(signature.getParameterNames(), joinPoint.getArgs());

        String key = SpELUtils.parseSpEL(lock.key(), context);
        if (lock.prefix()) {
            key = String.format("%s:%s:%s:%s", LOCK_ASPECT_PREFIX, method.getDeclaringClass().getName(), method.getName(), key);
        }

        boolean success = lockClient.tryLock(key, lock.timeout(), Math.max(lock.waitTime(), 0));
        if (!success) {
            throw new ToastException("操作太频繁，请稍后重试");
        }
        try {
            return joinPoint.proceed();
        } finally {
            lockClient.releaseLock(key);
        }
    }

}
