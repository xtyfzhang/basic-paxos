package com.basicpaxos;

import com.basicpaxos.bean.InstanceInfo;
import lombok.Data;

@Data
public class Response {

    private String code;

    private String msg;

    private InstanceInfo instanceInfo;
}
