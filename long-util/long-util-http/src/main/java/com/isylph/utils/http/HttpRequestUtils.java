package com.isylph.utils.http;


import com.isylph.basis.base.BaseListQuery;
import com.isylph.utils.datetime.DateTimeUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

public class HttpRequestUtils {

    public static void preProcListReq(BaseListQuery request){

        if (request.getCnt() == null || request.getCnt() > 500 ) {
            request.setCnt(500);
        }

        if (null == request.getOffset()){
            request.setOffset(0);
        }

        if (null != request.getTimestamp()) {

            request.setRefreshTimestamp(DateTimeUtils.getDateTimeOfTimestamp(request.getTimestamp()));
        }

        return;
    }

    public static String getUrlPath(HttpServletRequest request){
        String uri = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 0) {
            uri = uri + pathInfo;
        }
        uri = uri.split("\\.")[0];

        return uri.substring(1);
    }

    public static String getControllerPath(String path){
        String uri = path.split("/")[0];
        return uri;
    }

    public static String getRealContextPath(HttpServletRequest request) {
        String rootPath = request.getSession().getServletContext()
                .getRealPath(File.separator);
        return rootPath;
    }

    public static String getHttpRootPath(HttpServletRequest request) {
        String requestUrl = request.getScheme() //当前链接使用的协议
                +"://" + request.getServerName()//服务器地址
                + ":" + request.getServerPort() //端口号
                + request.getContextPath(); //应用名称，如果应用名称为
        //+ request.getServletPath() //请求的相对url
        //+ "?" + request.getQueryString(); //请求参数
        return requestUrl;
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostBody(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readLen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }

        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

}
