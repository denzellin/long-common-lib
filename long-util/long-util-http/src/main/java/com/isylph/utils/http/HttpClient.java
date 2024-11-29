package com.isylph.utils.http;

import com.isylph.basis.consts.BaseErrorConsts;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.utils.StringUtils;
import com.isylph.utils.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.LayeredConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.HttpStatus;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


@Slf4j
public class HttpClient {

    // 编码格式。发送编码格式统一用UTF-8
    public static final String CHARSET_UTF_8 = "UTF-8";


    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml;charset=utf-8";

    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded;charset=utf-8";

    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json";

    // 设置连接超时时间，单位毫秒。
    protected static final int CONNECT_TIMEOUT = 6000;

    protected static final int CONNECT_REQUEST_TIMEOUT = 2000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    protected static final int SOCKET_TIMEOUT = 20000;

    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {

        try {
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();

            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(registry);

            // 将最大连接数
            pool.setMaxTotal(800);

            // 设置最大路由
            pool.setDefaultMaxPerRoute(700);

//			HttpHost localhost = new HttpHost("http://www.sinopaypal.cn/api/gateway.do",80);
//			pool.setMaxPerRoute(new HttpRoute(localhost), 30);

            // 设置请求超时时间
            requestConfig = RequestConfig.custom()
                    .setResponseTimeout(Timeout.ofMilliseconds(5000L))
                    .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECT_REQUEST_TIMEOUT))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CloseableHttpClient getHttpClient() {

        // 设置连接池管理
        // 设置请求配置

        return HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig).build();
    }

    private static String doHttp(HttpUriRequestBase http){
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;

        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            http.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(http);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            int code = response.getCode();
            if (code >= 300) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                if(StringUtils.isEmpty(responseContent)){
                    responseContent = "HTTP Request is not success, Response code is " + code;
                }

                throw new ReturnException((long)code, responseContent);
            }

            if (HttpStatus.OK.value() == code) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new ReturnException("ClientProtocolException" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReturnException("IOException" + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return responseContent;
    }


    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @param headers 请求头集合
     */
    public static String doGet(String url, Object params, Map<String, String> headers)  {

        Map<String, Object> p;
        if (params instanceof Map){
            p = (Map<String, Object>) params;
        }else {
            p = objectToMap(params);
        }

        URI uri = packUrl(url, p);
        // 创建get请求
        HttpGet httpGet = new HttpGet(uri);
        if(null != headers){
            packageHeader(headers, httpGet);
        }

        return doHttp(httpGet);
    }


    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param params 请求参数集合
     * @return
     * @throws
     */
    public static String doGet(String url, Object params)  {
        return doGet(url, params, null);
    }


    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }


    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params vo对象
     * @param headers 请求头集合
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object params, String contentType, Map<String, String> headers, String encode){

        Map<String, Object> p;

        HttpPost httpPost = new HttpPost(url);

        if (null != params){
            if (params instanceof Map){
                p = (Map<String, Object>) params;
                packageParam(httpPost, p, encode);
            }else if (params instanceof String){
                packageParam(httpPost, (String) params, encode, contentType);
            }else {
                p = objectToMap(params);
                packageParam(httpPost, p, encode);
            }

        }



        if (null != headers){
            packageHeader(headers, httpPost);
        }

        return doHttp(httpPost);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params vo对象
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object params, String contentType){

        return doPost(url, params, contentType, null, CHARSET_UTF_8);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param params vo对象
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Object params){

        return doPost(url, params, CONTENT_TYPE_FORM_URL);
    }


    /**
     * 发送 post请求
     *
     * @param url
     *            地址
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }




    /**
     * 发送 post请求 发送json数据
     *
     * @param url
     *            地址
     * @param json
     *            参数(格式 json)
     *
     */
    public static String doPostJson(String url, String json, Map<String, String> headers) {

        return doPost(url, json, CONTENT_TYPE_JSON_URL,headers, CHARSET_UTF_8);
    }

    /**
     * 发送 post请求 发送json数据
     *
     * @param url
     *            地址
     * @param obj
     *            参数(格式 json)
     *
     */
    public static String doPostJson(String url, Object obj, Map<String, String> headers) {


        String json = JacksonUtils.serialize(obj);
        return doPostJson(url, json, headers);
    }

    /**
     * 发送 post请求 发送json数据
     *
     * @param url
     *            地址
     * @param obj
     *            参数(格式 json)
     *
     */
    public static String doPostJson(String url, Object obj) {

        return doPostJson(url, obj, null);
    }


    /**
     * 发送 post请求 发送json数据
     *
     * @param url
     *            地址
     * @param obj
     *            参数(格式 json)
     *
     */
    public static String doPostJson(String url, String obj) {

        return doPostJson(url, obj, null);
    }

    /**
     * 发送 post请求 发送xml数据
     *
     * @param url
     *            地址
     * @param xml
     *            参数(格式 Xml)
     *
     */
    public static String doPostXml(String url, String xml) {


        HttpPost httpPost = new HttpPost(url);
        packageParam(httpPost, xml, CHARSET_UTF_8, CONTENT_TYPE_TEXT_HTML);
        return doHttp(httpPost);
    }


    /**
     * 发送put请求；带请求参数
     *
     * @param url 请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static String doPut(String url, Object params, Map<String, String> headers) {

        HttpPut httpPut = new HttpPut(url);
        Map<String, Object> p;

        if (null != params){
            if (params instanceof Map){
                p = (Map<String, Object>) params;
                packageParam(httpPut, p, CHARSET_UTF_8);
            }else if (params instanceof String){
                packageParam(httpPut, (String) params, CHARSET_UTF_8, CONTENT_TYPE_JSON_URL);
            }else {
                p = objectToMap(params);
                packageParam(httpPut, p, CHARSET_UTF_8);
            }

        }

        //packageParam(httpPut, ()params, null);


        if (null != headers){
            packageHeader(headers, httpPut);
        }

        return doHttp(httpPut);
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static String doDelete(String url, Map<String, String> headers) {

        HttpDelete httpDelete = new HttpDelete(url);
        if (null != headers){
            packageHeader(headers, httpDelete);
        }
        return doHttp(httpDelete);
    }



    /**
     * Description: 封装请求头
     * @param params
     * @param httpMethod
     */
    public static void packageHeader(Map<String, String> params, HttpUriRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    public static URI packUrl(String url, Map<String, Object> params){

        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (params != null) {
            Set<Entry<String, Object>> entrySet = params.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();

            throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
        }

    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @param encode 编码格式
     * @throws
     */
    public static void packageParam(HttpUriRequestBase httpMethod, Map<String, Object> params, String encode){


        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Entry<String, Object>> entrySet = params.entrySet();
            for (Entry<String, Object> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, Charset.forName(StringUtils.isEmpty(encode)?CHARSET_UTF_8:encode));

            // 设置到请求的http对象中
            httpMethod.setEntity(formEntity);
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param httpMethod
     * @param encode 编码格式
     * @param contentType 内容形式
     * @throws
     */
    public static void packageParam(HttpUriRequestBase httpMethod, String content, String encode, String contentType){

        try{
            if (!StringUtils.isEmpty(content)) {
                StringEntity stringEntity = new StringEntity(content, ContentType.create(contentType, Charset.forName(encode)) );
                httpMethod.setEntity(stringEntity);
            }
        }catch (Exception e){
            log.warn("Unsupported params: {} ", content);
            throw new ReturnException(BaseErrorConsts.RET_BAD_PARAM);
        }

    }


    /**
	 * 对象转换map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap(Object obj) {
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<>();

        BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor property : propertyDescriptors) {
	            String key = property.getName();
	            if (key.compareToIgnoreCase("class") == 0) {
	                continue;
	            }
	            Method getter = property.getReadMethod();
	            Object value = getter!=null ? getter.invoke(obj) : null;
	            if(value != null) {
	            	map.put(key, String.valueOf(value));
	            }
	        }
		} catch (IntrospectionException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
        return map;
    }

}
