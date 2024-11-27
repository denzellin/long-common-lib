package com.isylph.security.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoadUserVO {

    String username;

    String password;

    String mobile;

    String smsCode;

    String captcha;
}
