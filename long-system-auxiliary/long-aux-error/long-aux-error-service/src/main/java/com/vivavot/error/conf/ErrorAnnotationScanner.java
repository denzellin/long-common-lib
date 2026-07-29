package com.vivavot.error.conf;

import com.vivavot.basis.anno.ErrorItem;
import com.vivavot.basis.anno.ErrorManager;
import com.vivavot.error.api.beans.ErrorDTO;
import com.vivavot.utils.spring.ClassScaner;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ErrorAnnotationScanner {

    private static Map<Long, ErrorDTO> mErrMap;

    public static Map<Long, ErrorDTO> getErrMap(){
        return mErrMap;
    }

    @Value("${spring.auto-scan}")
    private String[] basePacks;

    @PostConstruct
    public void init() {

        mErrMap = new HashMap<>();
        for(String pack: basePacks){
            ClassScaner.scan(pack, ErrorManager.class).forEach(this::procClass);
        }
    }

    private void procClass(Class<?> clazz){

        // 获取模块名
        String moduleName = clazz.getAnnotation(ErrorManager.class).value();

        // 将注解中的类型值作为key，对应的类作为value，保存在Map中
        Field[] fields = clazz.getDeclaredFields();
        try {
            for(Field item: fields) {

                if(Modifier.isStatic(item.getModifiers())){
                    ErrorItem anno = item.getAnnotation(ErrorItem.class);
                    if (anno == null) {
                        continue;
                    }
                    item.setAccessible(true);

                    // 取RetCode中错误码字段名称
                    String errName = item.getName();

                    Long val = (long)item.get(clazz);

                    ErrorDTO vo = new ErrorDTO()
                            .setModule(moduleName)
                            .setName(errName)
                            .setVal(val)
                            .setAnnotation(anno.value());
                    mErrMap.put(val, vo);
                }

            }


        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
    }
}
