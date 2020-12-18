package com.basicpaxos.proposal;

import com.basicpaxos.Response;
import com.basicpaxos.bean.InstanceInfo;
import com.basicpaxos.config.ConfigInfo;
import com.basicpaxos.utils.HttpUtil;
import com.basicpaxos.utils.IPUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class TaskRunnable implements Runnable {


    private BlockingQueue<InstanceInfo> queue;

    private ConfigInfo configInfo;

    private IPUtil ipUtil;

    public TaskRunnable(BlockingQueue<InstanceInfo> queue,ConfigInfo configInfo,IPUtil ipUtil){

        this.queue = queue;
        this.configInfo = configInfo;
        this.ipUtil = ipUtil;
    }

    @Override
    public void run() {

        while (true) {

            if (!queue.isEmpty()) {

                try {
                    log.info("线程 {} 执行共识任务开始",Thread.currentThread().getName() );
                    InstanceInfo instanceInfo = queue.take();
                    // 获取远程对接地址
                    String serviceIp = configInfo.getServiceIp();
                    if (serviceIp == null) {
                        serviceIp = ipUtil.getUrl();
                    } else {
                        serviceIp += ("," + ipUtil.getUrl());
                    }
                    // 获取单独的IP地址信息
                    String[] ips = serviceIp.split(",");

                    InstanceInfo newInstanceInfo = null;
                    Gson gson = new Gson();
                    String pa = gson.toJson(instanceInfo);
                    // 准备阶段
                    for (int i = 0 ; i < ips.length; i ++) {
                        String reponse = HttpUtil.post(ips[i]+"/accept/prepping",pa);
                        if (reponse != null) {

                            Response temp = gson.fromJson(reponse, Response.class);
                            if (temp.getInstanceInfo() != null) {

                                InstanceInfo infoTemp = temp.getInstanceInfo();
                                if (newInstanceInfo != null && newInstanceInfo.getProposalNumber() < infoTemp.getProposalNumber() ) {
                                    newInstanceInfo = infoTemp;
                                } else {
                                    newInstanceInfo = infoTemp;
                                }
                            }
                        }
                    }

                    // 接受阶段
                    if (newInstanceInfo == null ) {
                        newInstanceInfo = instanceInfo;
                    }
                    for (int i = 0; i < ips.length; i ++) {

                        HttpUtil.post(ips[i]+"/accept/prepping",gson.toJson(newInstanceInfo));
                    }
                    log.info("线程 {} 执行共识任务结束",Thread.currentThread().getName() );
                } catch (InterruptedException e) {
                    log.error("获取队列中的信息失败:{}",e.getMessage());
                }
            }
        }

    }
}
