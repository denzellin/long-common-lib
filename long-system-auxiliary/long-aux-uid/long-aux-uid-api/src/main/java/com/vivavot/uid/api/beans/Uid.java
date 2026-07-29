package com.vivavot.uid.api.beans;


import com.vivavot.basis.beans.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Uid extends BaseDTO {

    private Long uid;
}
