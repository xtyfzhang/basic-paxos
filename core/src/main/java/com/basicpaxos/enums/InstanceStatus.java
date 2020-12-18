package com.basicpaxos.enums;

public enum  InstanceStatus {

    // 准备阶段
    GETREADY(1,"准备阶段"),
    ACCEPT(2,"接受阶段")

    ;
    private int val;
    private String desc;

    InstanceStatus(int val,String desc){

        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
