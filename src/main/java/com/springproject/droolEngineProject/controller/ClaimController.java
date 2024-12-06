package com.springproject.droolEngineProject.controller;

import com.springproject.droolEngineProject.model.Claim;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClaimController {

    @Autowired
    private KieContainer kieContainer;

    @PostMapping("/getClaimRisk")
    public Claim processClaim(@RequestBody Claim claim) {
        KieSession session = kieContainer.newKieSession();
        session.insert(claim);
        session.fireAllRules();
        session.dispose();
        return claim;
    }

}