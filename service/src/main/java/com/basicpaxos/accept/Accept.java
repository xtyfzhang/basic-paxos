package com.basicpaxos.accept;

import com.basicpaxos.Response;
import com.basicpaxos.bean.InstanceInfo;
import com.basicpaxos.enums.InstanceStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Accept {

    private Map<String, InstanceInfo> values = new ConcurrentHashMap<>();



    /**
     * 准备阶段
     * @param instanceInfo
     * @return
     */
    public Response prepping(InstanceInfo instanceInfo){

        Response response = new Response();
        response.setCode("200");

        if (!values.containsKey(instanceInfo.getKey())) {
            values.put(instanceInfo.getKey(),instanceInfo);
            response.setMsg("尚无提案");
            return response;
        } else {
            InstanceInfo oldInstanceInfo = values.get(instanceInfo.getKey());
            if (oldInstanceInfo.getInstanceStatus() == InstanceStatus.ACCEPT
                    && oldInstanceInfo.getProposalNumber() < instanceInfo.getProposalNumber()) {

                oldInstanceInfo.setProposalNumber(instanceInfo.getProposalNumber());
                response.setMsg("将不通过小于该提案号！");
                InstanceInfo newInstance = new InstanceInfo();
                newInstance.setProposalNumber(instanceInfo.getProposalNumber());
                newInstance.setValue(oldInstanceInfo.getValue());
                newInstance.setKey(oldInstanceInfo.getKey());
                response.setInstanceInfo(newInstance);
                return response;
            }
        }
        return null;
    }


    /**
     * 接受阶段处理：已达成共识，设置新值
     * @param newInstanceInfo
     */
    public void accept(InstanceInfo newInstanceInfo){

        values.put(newInstanceInfo.getKey(),newInstanceInfo);
    }


    public String getValue(String key){

        return values.get(key) == null ? null : values.get(key).getValue();
    }
}
