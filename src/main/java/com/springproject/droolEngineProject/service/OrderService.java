package com.springproject.droolEngineProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import com.springproject.droolEngineProject.model.ExcelRuleStringCondition;
import com.springproject.droolEngineProject.config.DroolConfig;
import com.springproject.droolEngineProject.model.ExcelRule;
import com.springproject.droolEngineProject.model.Order;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;
import com.springproject.droolEngineProject.repo.ExcelRuleStringRepo;
import com.springproject.droolEngineProject.repo.ExcelRulesRepo;
import com.springproject.droolEngineProject.model.ExcelRuleTemplate;

@Service
public class OrderService {

    private final KieContainer kieContainer;
    private final DroolRulesRepo rulesRepo;
    private final ExcelRulesRepo excelRulesRepo;
    private ExcelRuleStringRepo excelRuleStringRepo;

    public OrderService(KieContainer kieContainer, DroolRulesRepo rulesRepo,ExcelRulesRepo excelRulesRepo, ExcelRuleStringRepo excelRuleStringRepo){
        this.kieContainer = kieContainer;
        this.rulesRepo = rulesRepo;
        this.excelRulesRepo=excelRulesRepo;
        this.excelRuleStringRepo=excelRuleStringRepo;
    }

    public Order getDiscountForOrder(Order order) {
        KieSession session = kieContainer.newKieSession();
        session.insert(order);
        session.fireAllRules();
        session.dispose();
        return order;
    }

    public Order getDiscountForOrderV2(Order order) {
        System.out.println("--------------a"+ order.getCardType());
        System.out.println( rulesRepo.findByFortype(order.getCardType()));
        System.out.println("--------------q");
        // List<Rule> ruleAttributes = new ArrayList<>(rulesRepo.findAll());      for all type
        List<Rule> ruleAttributes = rulesRepo.findByFortype(order.getCardType());
        System.out.println(ruleAttributes);
        ObjectDataCompiler compiler = new ObjectDataCompiler();
        String generatedDRL = compiler.compile(ruleAttributes, Thread.currentThread().getContextClassLoader().getResourceAsStream(DroolConfig.RULES_TEMPLATE_FILE));
        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        byte[] b1 = generatedDRL.getBytes();
        System.out.println(b1);
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build(); 

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();

        return order;
    }


    public Order getDiscountForOrderExcelRule(Order order) {
        System.out.println("--------------a"+ order.getCardType());
        // System.out.println( excelRulesRepo.findByFortype(order.getCardType()));
        System.out.println("--------------q");
        // List<Rule> ruleAttributes = new ArrayList<>(rulesRepo.findAll());      for all type
        List<ExcelRule> ruleAttributes =excelRulesRepo.findByFortype(order.getCardType());
        System.out.println(ruleAttributes.get(0).getConditionString());
        
        

        
        ObjectDataCompiler compiler = new ObjectDataCompiler();
          
        List<ExcelRuleTemplate> ert=ruleAttributes.stream().map(e -> new ExcelRuleTemplate(e.getRuleName(),e.getConditionString().replace('?','"' ),Float.parseFloat(e.getActionValue())) ).collect(Collectors.toList());
        // ruleAttributes.stream().map(e -> new ExcelRuleTemplate(e.getRuleName(),e.getConditionString(),Float.parseFloat(e.getActionValue())) );
        ert.forEach(e-> System.out.println(e.toString()));

        //    System.out.println("-----------------sd-----"+ ert.forEach(e-> System.out.println(e.toString())));

        String generatedDRL = compiler.compile(ert, Thread.currentThread().getContextClassLoader().getResourceAsStream(DroolConfig.EXCEL_RULES_TEMPLATE_FILE));
        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        byte[] b1 = generatedDRL.getBytes();
        System.out.println(b1);
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();

        return order;
    }




    public Order getDiscountForOrderExcelRuleString(Order order) {
        System.out.println("--------------a"+ order.getCardType());
        // System.out.println( excelRulesRepo.findByFortype(order.getCardType()));
        System.out.println("--------------q");
        // List<Rule> ruleAttributes = new ArrayList<>(rulesRepo.findAll());      for all type
        List<ExcelRuleStringCondition> ruleAttributes =excelRuleStringRepo.findByFortype(order.getCardType());
        // System.out.println(ruleAttributes.get(0).getConditionString());
        
        

        
        ObjectDataCompiler compiler = new ObjectDataCompiler();
          
        List<ExcelRuleTemplate> ert=ruleAttributes.stream().map(e -> new ExcelRuleTemplate(e.getRuleName(),e.getConditions(),Float.parseFloat(e.getActionValue())) ).collect(Collectors.toList());
        // ruleAttributes.stream().map(e -> new ExcelRuleTemplate(e.getRuleName(),e.getConditionString(),Float.parseFloat(e.getActionValue())) );
        ert.forEach(e-> System.out.println(e.toString()));

        //    System.out.println("-----------------sd-----"+ ert.forEach(e-> System.out.println(e.toString())));

        String generatedDRL = compiler.compile(ert, Thread.currentThread().getContextClassLoader().getResourceAsStream(DroolConfig.EXCEL_RULES_TEMPLATE_FILE));
        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        byte[] b1 = generatedDRL.getBytes();
        System.out.println(b1);
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();

        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();

        return order;
    }

    
}
