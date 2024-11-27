package com.isylph.notification.beans;


import com.isylph.basis.beans.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SmsMap extends BaseDTO {

    private String key;

    private String value;

}
