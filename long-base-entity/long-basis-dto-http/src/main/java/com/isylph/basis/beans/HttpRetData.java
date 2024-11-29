package com.isylph.basis.beans;


import com.isylph.basis.base.RetPage;
import com.isylph.basis.consts.BaseErrorConsts;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HttpRetData<T> extends BaseVO {

    private Long code;

    private String message;

    private Integer total;

    /*
     * 查询返回的时间戳
     * */
    private Long timestamp;

    private T data;

    public HttpRetData() {
        super();
        timestamp = System.currentTimeMillis();
    }

    public HttpRetData(Long code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
        timestamp = System.currentTimeMillis();
    }


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void set(Long code, String message, Integer total, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public static HttpRetData success() {
        HttpRetData resultData = new HttpRetData();
        resultData.setCode(BaseErrorConsts.RET_OK);
        resultData.setMessage(BaseErrorConsts.RET_OK_MSG);
        return resultData;
    }

    public static <T> HttpRetData<T> success(T data) {
        HttpRetData<T> resultData = HttpRetData.success();
        resultData.setData(data);
        return resultData;
    }

    public static <T> HttpRetData<List<T>> success(RetPage<T> data) {
        HttpRetData resultData = new HttpRetData<List<T>>();
        resultData.setCode(BaseErrorConsts.RET_OK);
        resultData.setMessage(BaseErrorConsts.RET_OK_MSG);
        resultData.setTotal(data.getTotal());
        resultData.setData(data.getRecords());
        return resultData;
    }

    public static <T> HttpRetData<T> success(T data, int total) {
        HttpRetData<T> resultData = HttpRetData.success();
        resultData.setData(data);
        resultData.setTotal(total);
        return resultData;
    }



    public static HttpRetData error(Long code, String message) {
        HttpRetData resultData = new HttpRetData();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }

    public static HttpRetData error(Long code) {
        HttpRetData resultData = new HttpRetData();
        resultData.setCode(code);
        resultData.setMessage("");
        return resultData;
    }

    public static HttpRetData error(HttpStatus code) {
        HttpRetData resultData = new HttpRetData();
        long val = code.value();
        resultData.setCode(val);
        resultData.setMessage(code.name());
        return resultData;
    }


}
