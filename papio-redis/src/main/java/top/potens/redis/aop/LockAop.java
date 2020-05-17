package top.potens.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import top.potens.redis.annotation.Lock;
import top.potens.redis.enums.LockModel;
import top.potens.redis.exception.LockException;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className LockAop
 * @projectName web-api
 * @date 2019/8/24 10:35
 */
@Aspect
@Component
public class LockAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${fx.config.redisson.default.attempt.timeout:100}")
    private Integer attemptTimeout;

    @Value("${fx.config.redisson.default.watch.timeout:120}")
    private Integer watchTimeout;

    @Value("${spring.application.name}")
    private String springApplicationName;

    @Autowired
    private RedissonClient redisson;

    @Pointcut("@annotation(lock)")
    public void controllerAspect(Lock lock) {
    }

    /**
     * 通过spring Spel 获取参数
     *
     * @param key            定义的key值 以#开头 例如:#user
     * @param parameterNames 形参
     * @param values         形参值
     * @param projectPrefix  项目前缀
     * @return
     */
    private String getValueBySpel(String key, String[] parameterNames, Object[] values, boolean projectPrefix) {
        if (projectPrefix) {
            key = springApplicationName + ":"+ key;
        }
        if (!key.contains("#")) {
            return key;
        }
        // spel解析器
        ExpressionParser parser = new SpelExpressionParser();
        // spel上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], values[i]);
        }
        String value = null;
        try {
            Expression expression = parser.parseExpression(key, new TemplateParserContext());
            value = expression.getValue(context, String.class);
        } catch (Exception e) {
            throw new LockException("解析失败");
        }
        if (value == null || value.length() == 0) {
            throw new LockException("lock value is blank");
        }
        logger.info("spel表达式key={},value={}", key, value);
        return value;
    }

    @Around(value = "controllerAspect(lock)", argNames = "proceedingJoinPoint,lock")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Lock lock) throws Throwable {
        String[] keys = lock.keys();
        if (keys.length == 0) {
            throw new LockException("keys不能为空");
        }
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(((MethodSignature) proceedingJoinPoint.getSignature()).getMethod());
        Object[] args = proceedingJoinPoint.getArgs();

        long attemptTimeout = lock.attemptTimeout();
        if (attemptTimeout == 0) {
            attemptTimeout = this.attemptTimeout;
        }
        long lockWatchTimeout = lock.lockWatchTimeout();
        if (lockWatchTimeout == 0) {
            lockWatchTimeout = this.watchTimeout;
        }
        LockModel lockModel = lock.lockModel();
        if (lockModel.equals(LockModel.AUTO)) {
            if (keys.length > 1) {
                lockModel = LockModel.MULTIPLE;
            } else {
                lockModel = LockModel.FAIR;
            }
        }
        if (!lockModel.equals(LockModel.MULTIPLE) && !lockModel.equals(LockModel.REDLOCK) && keys.length > 1) {
            throw new LockException("参数有多个,锁模式为->" + lockModel.name() + ".无法锁定");
        }
        logger.info("model:[{}] attemptTimeout:[{}] lockWatchTimeout:[{}] timeUnit:[{}]", lockModel.name(), attemptTimeout, lockWatchTimeout, lock.timeUnit());
        boolean res = false;
        RLock rLock = null;

        if (LockModel.FAIR.equals(lockModel)) {
            rLock = redisson.getFairLock(getValueBySpel(keys[0], parameterNames, args, lock.projectPrefix()));
        } else if (LockModel.REDLOCK.equals(lockModel)) {
            RLock[] locks = new RLock[keys.length];
            int index = 0;
            for (String key : keys) {
                locks[index++] = redisson.getLock(getValueBySpel(key, parameterNames, args, lock.projectPrefix()));
            }
            rLock = new RedissonRedLock(locks);
        } else if (LockModel.MULTIPLE.equals(lockModel)) {
            RLock[] locks = new RLock[keys.length];
            int index = 0;
            for (String key : keys) {
                locks[index++] = redisson.getLock(getValueBySpel(key, parameterNames, args, lock.projectPrefix()));
            }
            rLock = new RedissonMultiLock(locks);
        } else if (LockModel.REENTRANT.equals(lockModel)) {
            rLock = redisson.getLock(getValueBySpel(keys[0], parameterNames, args, lock.projectPrefix()));
        } else if (LockModel.READ.equals(lockModel)) {
            RReadWriteLock rReadWriteLock = redisson.getReadWriteLock(getValueBySpel(keys[0], parameterNames, args, lock.projectPrefix()));
            rLock = rReadWriteLock.readLock();
        } else if (LockModel.WRITE.equals(lockModel)) {
            RReadWriteLock rReadWriteLock = redisson.getReadWriteLock(getValueBySpel(keys[0], parameterNames, args, lock.projectPrefix()));
            rLock = rReadWriteLock.writeLock();
        }
        //执行aop
        if (rLock != null) {
            try {
                if (attemptTimeout == -1) {
                    res = true;
                    // 一直等待加锁
                    rLock.lock(lockWatchTimeout, TimeUnit.MILLISECONDS);
                } else {
                     res = rLock.tryLock(attemptTimeout, lockWatchTimeout, lock.timeUnit());
                }
                if (res) {
                    return proceedingJoinPoint.proceed();
                } else {
                    throw new LockException("获取锁失败");
                }
            } finally {
                if (res) {
                    rLock.unlock();
                }
            }
        }
        throw new LockException("获取锁失败");
    }

}
