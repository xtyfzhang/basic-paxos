package com.basicpaxos.enums;

public enum Status {

;
    private int val;
    private String desc;
    Status(int val,String desc){

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
