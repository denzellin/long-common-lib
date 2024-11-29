package com.isylph.console.scanner;

/**
 * 扫描controller层的api, 保存到sys_fun表
 */

import com.isylph.console.settings.model.SysFuncGroupPO;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.service.SysFuncGroupService;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.utils.StringUtils;
import com.isylph.utils.spring.ClassScaner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;

@Slf4j
@Component
public class ControllerAnnotationScanner {

    @Autowired
    private SysFuncGroupService sysFuncGroupService;

    @Autowired
    private SysFuncService sysFuncService;

    public void saveSystemFunctionsInfo (){

        ClassScaner.scan("com.isylph", Tag.class).forEach(clazz -> {

            // 获取模块名
            String moduleId = clazz.getAnnotation(Tag.class).description();

            Long mid = null;
            try {
                mid =Long.valueOf(moduleId);
            }catch (NumberFormatException e){
                log.info("Invalid module id: {}", moduleId);
                return;
            }
            String moduleName = clazz.getAnnotation(Tag.class).name();
            SysFuncGroupPO po = sysFuncGroupService.getById(mid);
            SysFuncGroupPO gp = new SysFuncGroupPO();
            gp.setId(mid).setName(moduleName);
            if (po == null){
                sysFuncGroupService.save(gp);
            }else {
                sysFuncGroupService.updateById(gp);
            }
            String baseUrl = "";
            String[] urls = clazz.getAnnotation(RequestMapping.class).value();
            if (urls.length > 0){
                baseUrl = urls[0];
            }
            // 将注解中的类型值作为key，对应的类作为value，保存在Map中
            Method[] methods = clazz.getDeclaredMethods();
            if (methods.length == 0){
                return;
            }
            try {
                for(Method item: methods) {

                    item.setAccessible(true);
                    Operation api = item.getAnnotation(Operation.class);

                    if (api == null || StringUtils.isEmpty(api.operationId())){
                        continue;
                    }
                    String funcId = api.operationId();
                    Long fid = null;
                    try {
                        fid = Long.valueOf(moduleId+funcId);
                    }catch (NumberFormatException e){
                        log.info("Invalid function id: {}", funcId);
                        continue;
                    }
                    GetMapping get = item.getAnnotation(GetMapping.class);
                    SysFuncPO func = new SysFuncPO()
                            .setActive(1)
                            .setGroupId(mid)
                            .setId(fid)
                            .setName(api.summary())
                            .setUrl(baseUrl + api.method());
                    if (get != null){
                        func.setMethod("GET");
                    }
                    PostMapping post = item.getAnnotation(PostMapping.class);
                    if (post != null){
                        func.setMethod("POST");
                    }
                    PutMapping put = item.getAnnotation(PutMapping.class);
                    if (put != null){
                        func.setMethod("PUT");
                    }
                    DeleteMapping delete = item.getAnnotation(DeleteMapping.class);
                    if (delete != null){
                        func.setMethod("DELETE");
                    }

                    SysFuncPO sysfunc = sysFuncService.getById(fid);
                    if (sysfunc == null){
                        sysFuncService.save(func);
                    }else{
                        sysFuncService.updateById(func);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
