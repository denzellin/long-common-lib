package com.isylph.basis.types;

import com.isylph.basis.base.BaseType;
import com.isylph.basis.error.TypeErrors;
import com.isylph.basis.controller.exception.ReturnException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 订单的价格
 * </p>
 *
 * @author denzel.lin
 * @since 2020-02-12
 */


@Slf4j
@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class Price extends BaseType {

    Long price;

    public Price(Long price) {
        if (price < 0){
            log.warn("invalid price: {}", price);
            throw new ReturnException(TypeErrors.INVALID_NEGATIVE_PRICE);
        }

        this.price = price;
    }

    public float toYuan(){
        if (null == price || price == 0){
            return 0;
        }
        return (float)price/100;
    }

    public boolean valid(){
        return this.price != null && this.price > 0;
    }
}
