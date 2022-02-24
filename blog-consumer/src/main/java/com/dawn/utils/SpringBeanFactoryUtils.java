package com.dawn.utils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jitwxs
 * @date 2018/3/20 14:22
 */
@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware {

    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
    private static UserService userService;

    public static UserService getUserService() {
        return userService;
    }

    private static ApplicationContext context = null;

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanFactoryUtils.context == null) {
            SpringBeanFactoryUtils.context = applicationContext;
        }
    }
}
