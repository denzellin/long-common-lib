package com.isylph.uid.controller;


import com.isylph.basis.beans.HttpRetData;
import com.isylph.uid.service.UidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uid")
public class UidController {

    @Autowired
    private UidService uidService;

    @GetMapping(value = "/r")
    public HttpRetData<Long> getUid() {

        return HttpRetData.success(uidService.getUid());
    }
}
