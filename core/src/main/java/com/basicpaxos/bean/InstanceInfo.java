package com.basicpaxos.bean;

import com.basicpaxos.enums.InstanceStatus;
import lombok.Data;

/**
 * 实例字段
 */
@Data
public class InstanceInfo {

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String value;

    /**
     * 提案号
     */
    private int proposalNumber;


    /**
     * 处于得阶段
     */
    private InstanceStatus instanceStatus;

}
