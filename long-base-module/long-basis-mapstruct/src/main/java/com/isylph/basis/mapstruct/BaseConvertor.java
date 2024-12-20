package com.isylph.basis.mapstruct;



import com.isylph.basis.types.ChinaIdNo;
import com.isylph.basis.types.Email;
import com.isylph.basis.types.Mobile;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *
 * </p>
 *
 * @Author SYLPH Technologies Co., Ltd
 * @Date 2024/11/21 00:38
 * @Version 1.0
 */
public interface BaseConvertor {

    ZoneId zoneId = ZoneId.systemDefault();

    // 使用ZoneId定义时区（这里使用默认时区，你可以根据需要替换为指定时区）

    default LocalDateTime toLdt(Long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    default Long toTimestamp(LocalDateTime localDateTime){

        // 转换为 ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // 转换为毫秒时间戳
        return zonedDateTime.toInstant().toEpochMilli();
    }

    DateTimeFormatter _sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    default DateTimeFormatter getSdf(){
        return _sdf;
    }
    default LocalDateTime toLocalDateTime(String time){
        if(time == null || time.trim().isEmpty()){
            return null;
        }
        DateTimeFormatter sdf = getSdf();

        return LocalDateTime.parse(time, sdf) ;
    }

    default String toLocalDateTimeString(LocalDateTime time){
        if (time == null){
            return null;
        }
        return time.format(getSdf());
    }

    default BigDecimal toBigDecimal(String value) {
        return new BigDecimal(value);
    }

    default BigDecimal toBigDecimal(double value) {
        return new BigDecimal(value);
    }


    default Mobile toMobile(String mobile) {
        if (mobile == null || mobile.trim().isEmpty()) {
            return null;
        }
        return new Mobile(mobile);
    }

    default Email toEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return new Email(email);
    }

    default ChinaIdNo toChinaIdNo(String chinaIdNo) {
        if (chinaIdNo == null || chinaIdNo.trim().isEmpty()) {
            return null;
        }
        return new ChinaIdNo(chinaIdNo);
    }
}
