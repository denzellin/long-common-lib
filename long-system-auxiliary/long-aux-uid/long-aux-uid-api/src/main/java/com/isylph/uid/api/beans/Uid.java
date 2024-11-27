package com.isylph.uid.api.beans;


import com.isylph.basis.beans.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Uid extends BaseDTO {

    private Long uid;
}
