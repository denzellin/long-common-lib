package com.isylph.error.conf;

import com.isylph.basis.anno.ErrorItem;
import com.isylph.basis.anno.ErrorManager;
import com.isylph.error.api.beans.ErrorDTO;
import com.isylph.utils.spring.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorAnnotationScanner implements BeanFactoryPostProcessor {

    private static Map<Long, ErrorDTO> mErrMap;

    public static Map<Long, ErrorDTO> getErrMap(){
        return mErrMap;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        Map<Long, ErrorDTO> errMap = new HashMap<>();

        ClassScaner.scan("com.isylph", ErrorManager.class).forEach(clazz -> {

            // 获取模块名
            String moduleName = clazz.getAnnotation(ErrorManager.class).value();

            // 将注解中的类型值作为key，对应的类作为value，保存在Map中
            Field[] fields = clazz.getDeclaredFields();
            try {
                for(Field item: fields) {

                    if(Modifier.isStatic(item.getModifiers())){

                        item.setAccessible(true);

                        // 取RetCode中错误码字段名称
                        String errName = item.getName();

                        Long val = (long)item.get(clazz);

                        ErrorItem anno = item.getAnnotation(ErrorItem.class);

                        if (anno != null){
                            ErrorDTO vo = new ErrorDTO()
                                    .setModule(moduleName)
                                    .setName(errName)
                                    .setVal(val)
                                    .setAnnotation(anno.value());
                            errMap.put(val, vo);
                        }
                    }

                }

                mErrMap = errMap;

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
    }
}
