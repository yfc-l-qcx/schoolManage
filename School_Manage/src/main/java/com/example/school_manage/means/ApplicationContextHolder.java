package com.example.school_manage.means;


import com.example.school_manage.service.ZzClassQuantizationService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author macro
 * @date 2022/12/18
 */
public class ApplicationContextHolder implements ApplicationContextAware {
    private static  ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextHolder.context = context;
        Object s = ApplicationContextHolder.getContext().getBean(ZzClassQuantizationService.class);

    }

    public static ApplicationContext getContext() {
        return context;
    }
}
