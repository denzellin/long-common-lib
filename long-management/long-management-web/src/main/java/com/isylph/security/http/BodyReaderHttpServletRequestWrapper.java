package com.isylph.security.http;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isylph.basis.controller.filter.BaseHttpServletRequestWrapper;
import com.isylph.security.beans.SessionUserContextVO;
import com.isylph.utils.StringUtils;
import com.isylph.utils.http.HttpHelper;
import com.isylph.utils.json.JacksonUtils;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.json.JSONException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

public class BodyReaderHttpServletRequestWrapper extends BaseHttpServletRequestWrapper {

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request, SessionUserContextVO memberVo) throws IOException {
        super(request);
        String contentType = Optional.ofNullable(request.getContentType()).map(String::toLowerCase).orElse("");
        if (!contentType.contains("application/json")){
            Map<String, String[]> o = request.getParameterMap();
            return;
        }

        String content = HttpHelper.getBodyString(request);

        ObjectNode object = null;
        if (!StringUtils.isEmpty(content)){
            object = (ObjectNode) JacksonUtils.deserialize(content);
        }

        if(object == null){
            object = JacksonUtils.createNode();
        }

        assert object != null;
        if(object.get("opAccountId") == null && memberVo != null) {
        	object.put("opAccountId", memberVo.getId());

        	object.put("opAccount", memberVo.getUsername());
        	object.put("opName", memberVo.getName());
        	object.put("employeeId", memberVo.getEmployeeId());
        	object.put("opMobile", memberVo.getMobile());

        	object.put("permissionCode", memberVo.getPermissionCode());
        }
        body = JacksonUtils.serialize(object).getBytes(Charset.forName("UTF-8"));

    }

}
