package com.sulongfei.jump.context;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring 上下文
 *
 * @author siguiyang
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {

        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 通过class对象从Spring中获取原对象
     *
     * @param tClass class 对象
     * @param <T>    泛型
     * @return 实例对象引用
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * 通过beanName 和 class对象从Spring中获取原对象
     *
     * @param beanName bean 名称
     * @param tClass   class 对象
     * @param <T>      泛型
     * @return 实例对象引用
     */
    public static <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName, tClass);
    }
}
