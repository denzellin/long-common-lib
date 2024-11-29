package com.isylph.console.settings;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.console.api.beans.system.func.SysFuncQuery;
import com.isylph.console.api.beans.system.func.SysFuncSaveCmd;
import com.isylph.console.api.beans.system.func.SysFuncUpdateCmd;
import com.isylph.console.api.beans.system.func.SysFuncVO;
import com.isylph.console.scanner.ControllerAnnotationScanner;
import com.isylph.console.settings.model.SysFuncPO;
import com.isylph.console.settings.service.SysFuncService;
import com.isylph.controller.CrudController;
import com.isylph.service.BaseService;
import com.isylph.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denzel.lin
 * @since 2019-03-20
 */
@Tag( name = "系统功能", description = "100")
@RestController
@RequestMapping("/console/function")
public class SysFuncController
        implements CrudController<
        SysFuncSaveCmd,
        SysFuncUpdateCmd,
        SysFuncQuery,
                SysFuncVO,
                SysFuncPO> {

    @Autowired
    private SysFuncService sysFuncService;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @Operation(summary = "系统功能管理", method = "/**", operationId = "100")
    public HttpRetData demo() {
        return HttpRetData.success();
    }

    @Override
    public BaseService<SysFuncPO> getService() {
        return sysFuncService;
    }

    @Override
    public SysFuncPO createModel() {
        return new SysFuncPO();
    }


    @Autowired
    private ControllerAnnotationScanner controllerAnnotationScanner;
    @Operation(summary = "导入功能")
    @PostMapping(value = "/load")
    public HttpRetData loadFunctionAnnotation(){
        controllerAnnotationScanner.saveSystemFunctionsInfo();
        return HttpRetData.success();
    }


    @GetMapping(path = "/fetch")
    @Operation(summary = "查询SPRING MVC的URL")
    public HttpRetData<List<Map<String, String>>> fecthUrl(String pat) {

        Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> set = map.keySet();
        List<Map<String, String>> urls = new ArrayList<>();
        for (RequestMappingInfo info : set) {


            HandlerMethod handlerMethod = map.get(info);

            // springmvc的url地址，不包含项目名
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();

            String url = patternsCondition.getPatterns().toArray()[0].toString();
            if (url.startsWith("/common")){
                continue;
            }
            if (url.startsWith("/login")){
                continue;
            }
            if (url.startsWith("/logout")){
                continue;
            }
            if (url.startsWith("/tool")){
                continue;
            }
            if (url.startsWith("/swagger")){
                continue;
            }
            if (url.startsWith("/error")){
                continue;
            }

            if (!StringUtils.isEmpty(pat) && !url.contains(pat)){
                continue;
            }
            Method method = handlerMethod.getMethod();

            Tag anno = method.getDeclaringClass().getAnnotation(Tag.class);
            String name = Optional.ofNullable(anno).map((Tag an) -> an.description()).orElse("");

            if(method.isAnnotationPresent(Operation.class)){
                Operation annoApi = method.getAnnotation(Operation.class);
                if(!StringUtils.isEmpty(name)){
                    name += '-';
                }
                name += annoApi.description();
            }

            Map<String, String> urlMap = new HashMap<>();
            urlMap.put("name", name);
            urlMap.put("url", url);

            urls.add(urlMap);
            if(urls.size() > 15){
                break;
            }
        }
        return HttpRetData.success(urls);
    }

    @Override
    public SysFuncVO createViewObject() {
        return new SysFuncVO();
    }
}

