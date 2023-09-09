package cn.javgo.drools.util;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.io.Serializable;
import java.util.Map;

/**
 * Spring Context 工具类
 */
public class SpringContextUtil implements Serializable , ApplicationContextAware {

    /**
     * 隐藏构造器
     * <p>
     * 说明：工具类不允许实例化，只能通过静态方法调用。
     */
    private SpringContextUtil() {
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 程序启动时，会将 ApplicationContext 对象注入到该方法中
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 如果 applicationContext 为空时，将 applicationContext 对象注入到该类中
        if (SpringContextUtil.applicationContext == null){
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name,clazz);
    }

    /**
     * 根据接口类型获取所有实现类
     * @param clazz 接口类型
     * @return 所有实现类
     * @param <T> 泛型
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        // Key: Bean 名称，Value: Bean 对象
        Map<String, T> beanMaps = getApplicationContext().getBeansOfType(clazz);
        if (!CollectionUtil.isEmpty(beanMaps)){
            return beanMaps;
        }
        return null;
    }
}
