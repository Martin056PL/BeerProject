package wawer.kamil.beerproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class Aspects {

    @Around("execution(* wawer.kamil.beerproject.controllers.BreweryController.getAllBreweryPage(..)) && args(pageable)")
    public Object log(ProceedingJoinPoint joinPoint, Pageable pageable) throws Throwable {
        log.debug("Endpoint address: 'brewery' with GET method, request parameter - pageable: {}", pageable);
        return joinPoint.proceed();
    }

    @Around("execution(* wawer.kamil.beerproject.controllers.BreweryController.getAllBreweryList(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Endpoint address: 'brewery/list' with GET method");
        return joinPoint.proceed();
    }

    @Around("execution(* wawer.kamil.beerproject.controllers.BreweryController.getAllBreweryPage(..)) && args(id)")
    public Object log1(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        log.debug("Endpoint address: 'brewery/{breweryId}' with GET method, request parameter - id: {}",id);
        return joinPoint.proceed();
    }
}