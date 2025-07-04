package com.co.kc.shortening.infrastructure.aop;

import com.co.kc.shortening.application.client.CacheClient;
import com.co.kc.shortening.application.annotation.Cache;
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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author KIMVON
 * @since 2021/7/20
 */
@Aspect
@Component
public class CacheAspect {
    private final CacheClient cacheClient;

    public CacheAspect(CacheClient cacheClient) {
        this.cacheClient = cacheClient;
    }

    @Pointcut("@annotation(com.co.kc.shortening.application.annotation.Cache)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取查询的key 连接点是在方法上的,所以可以强转成方法的签名信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取被注解注释的方法
        Method method = AopUtils.getMethod(joinPoint);

        Cache cache = method.getAnnotation(Cache.class);
        EvaluationContext context = SpELUtils.createContext(signature.getParameterNames(), joinPoint.getArgs());

        String key = String.format("%s:%s:%s",
                method.getDeclaringClass().getName(), method.getName(), SpELUtils.parseSpEL(cache.key(), context));

        Object value = cacheClient.get(key, method.getReturnType()).orElse(null);
        if (Objects.isNull(value)) {
            value = joinPoint.proceed();
            cacheClient.set(key, value, cache.timeout(), TimeUnit.SECONDS);
        }
        return value;
    }
}
