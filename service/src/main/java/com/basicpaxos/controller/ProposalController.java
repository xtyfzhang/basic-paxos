package com.basicpaxos.controller;

import com.basicpaxos.bean.InstanceInfo;
import com.basicpaxos.proposal.Proposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    private Proposal proposal;


    @RequestMapping("/submit")
    public void submit(InstanceInfo instanceInfo){

        proposal.proposal(instanceInfo.getKey(),instanceInfo.getValue());
    }
}
