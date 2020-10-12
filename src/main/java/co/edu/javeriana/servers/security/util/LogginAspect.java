package co.edu.javeriana.servers.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogginAspect.class);

    @Pointcut("@annotation(co.edu.javeriana.servers.security.util.InfoLogger)")
    public void infoLoggerPointCut() {
    }

    @Before("infoLoggerPointCut()")
    public void before(JoinPoint point) {
        try {
            Object result = point.getArgs();
            ObjectMapper objectMapper = new ObjectMapper();
            logger.debug("*============================ BEFORE > > > {}", objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException jpe) {
            logger.error("JsonGenerationException > > > {}", jpe.toString());
        } catch (Throwable e) {
            logger.error("*============================ Throwable > > > {}", e.toString());
        }
    }

    @After("infoLoggerPointCut()")
    public void afterOper(JoinPoint jp) {
        try {
            Object result = jp.getArgs();
            ObjectMapper objectMapper = new ObjectMapper();
            logger.debug("*============================ AFTER > > > {}", objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException jpe) {
            logger.error("*JsonGenerationException > > > {}", jpe.toString());
        } catch (Throwable e) {
            logger.error("*============================ Throwable > > > {}", e.toString());
        }

    }

    @AfterThrowing(pointcut = "infoLoggerPointCut()", throwing = "error")
    public void controlException(JoinPoint joinPoint, Throwable error) {
        logger.debug("*============================ SE PRODUJO UNA EXCEPCION > > > {} CAUSA {}", joinPoint.getSignature().getName(), error);
    }

    @Around("infoLoggerPointCut()")
    public Object logMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object retval = null;
        try {
            StringBuffer stb = new StringBuffer();
            stb.append("============================ INICIANDO METODO > > > " + pjp.getTarget().getClass().getName());
            stb.append("(");

            Object[] arg = pjp.getArgs();

            for (int i = 0; i < arg.length; i++) {
                stb.append(arg[i]).append(",");
            }

            if (arg.length > 0) {
                stb.deleteCharAt(stb.length() - 1);
            }

            stb.append(")");

            retval = pjp.proceed();

            logger.debug("*============================ AROUND > > > {}", stb.toString());
        } catch (Throwable tr) {
            logger.error("============================ ERROR > > > {}", tr);
            throw tr;
        }

        return retval;
    }

    @AfterReturning(pointcut = "infoLoggerPointCut()", returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            logger.debug("*============================ DESPUES DEL RETURNING > > > {}", objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException jpe) {
            logger.error("JsonGenerationException > > > {}", jpe.toString());
        }

    }

}
