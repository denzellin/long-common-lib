package com.isylph.console.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(description = "", name = "")
@RestController
public class LoginController {

    @PostMapping("/login")
    public void logon(String account, String password){
        return;
    }
    @PostMapping("/logout")
    public void logout(){
        return;
    }

}
