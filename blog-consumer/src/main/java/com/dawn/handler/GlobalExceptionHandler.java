package com.dawn.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandling(HttpServletRequest request, Exception e) throws Exception {

        log.error("Request URL: {}, Exception: {}", request.getRequestURL(), e);

        if (AnnotationUtils.findAnnotation(e.getClass(),
                ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();

        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);

        mv.setViewName("error/error");

        return mv;
    }
}
