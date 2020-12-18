package com.basicpaxos.proposal;

import com.basicpaxos.bean.InstanceInfo;
import com.basicpaxos.config.ConfigInfo;
import com.basicpaxos.enums.InstanceStatus;
import com.basicpaxos.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 提议者： 接受客户端提交的信息
 */
@Component
@Slf4j
public class Proposal {

    @Autowired
    private ConfigInfo configInfo;

    @Autowired
    private IPUtil ipUtil;

    /**
     * 提案号信息
     */
    private Map<String, Integer> values = new ConcurrentHashMap<>();

    /**
     * 更新
     */
    private BlockingQueue<InstanceInfo> queue = new ArrayBlockingQueue<InstanceInfo>(100);

    /**
     * 线程池信息
     */
    private ThreadPoolExecutor service ;

    @PostConstruct
    public void init(){
        log.info("Proposal 开始初始化");
        String serviceIp = configInfo.getServiceIp();
        //String localPort = IPUtil.getLocalPort();

        configInfo.setServiceIp(serviceIp);
        TaskRunnable taskRunnable = new TaskRunnable(queue,configInfo,ipUtil);
        Thread thread = new Thread(taskRunnable);
        thread.setName("basic-paxos-thread");
        thread.start();
    }

    /**
     * 接受客户端传的值
     */
    public void proposal(String key,String value){

        int proposalNumber = values.getOrDefault(key,0);
        InstanceInfo instanceInfo = new InstanceInfo();
        instanceInfo.setKey(key);
        instanceInfo.setValue(value);
        // 如果不包含该key,则提案号为0
        instanceInfo.setProposalNumber(proposalNumber);
        instanceInfo.setInstanceStatus(InstanceStatus.GETREADY);
        values.put(key,proposalNumber + 1);
        queue.offer(instanceInfo);
    }



}
