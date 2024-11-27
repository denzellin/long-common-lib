package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import com.isylph.basis.error.TypeErrors;
import com.isylph.basis.controller.exception.ReturnException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付平台
 * </p>
 *
 * @author denzel.lin
 * @since 2020-02-12
 */

@Slf4j
@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class PaymentPlatform extends BaseType {

    private static final String payWechat = "WECHAT";

    private static final String payAli = "ALIPAY";

    private static final String unionPay = "UNIONPAY";

    private static final String point = "POINT";

    private static Map<String, Integer> payMapString = new HashMap<String, Integer>(){
        {
            put(payWechat, 1);
            put(payAli, 2);
            put(unionPay, 3);
            put(point, 4);
        }
    };

    private static Map<Integer, String> payMap = new HashMap<Integer, String>(){
        {
            put(1, payWechat);
            put(2, payAli);
            put(3, unionPay);
            put(4, point);
        }
    };

    private String name;

    private Integer code;

    public PaymentPlatform(String name) {

        if (null == payMapString.get(name)){
            log.warn("invalid payment platform: {}", name);
            throw new ReturnException(TypeErrors.INVALID_PAYMENT_PLATFORM);
        }
        this.name = name;
    }

    public PaymentPlatform(Integer code) {

        if (null == payMap.get(code)){
            log.warn("invalid payment platform: {}", code);
            throw new ReturnException(TypeErrors.INVALID_PAYMENT_PLATFORM);
        }

        this.code = code;
    }

    public static PaymentPlatform point(){
        return new PaymentPlatform(4);
    }

    public boolean isPointPayment(){
        return this.code == 4;
    }
}
