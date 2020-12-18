package com.basicpaxos.controller;

import com.basicpaxos.Response;
import com.basicpaxos.accept.Accept;
import com.basicpaxos.bean.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accept")
public class AcceptController {

    @Autowired
    private Accept accept;

    @PostMapping("prepping")
    public Response prepping(@RequestBody InstanceInfo instanceInfo){

        return accept.prepping(instanceInfo);
    }


    @PostMapping("accept")
    public void accept(@RequestBody InstanceInfo instanceInfo){

        accept.accept(instanceInfo);
    }

    @GetMapping("/getValue")
    public String getValue(String key){

        return accept.getValue(key);
    }
}
