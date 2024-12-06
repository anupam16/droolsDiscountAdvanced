package com.springproject.droolEngineProject.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolConfig {
    public static final String RULES_CUSTOMER_RULES_DRL = "rules/customer-discount.drl";
    public static final String RULES_TEMPLATE_FILE = "rules/discount-template.dtemplate";
    public static final String RULES_CLAIM_RULES_DRL = "rules/claim-rules.drl";  // Adding Claim Rules DRL
    public static final String EXCEL_RULES_TEMPLATE_FILE = "rules/discount-template-excel.dtemplate";

    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        // Load customer discount rules and claim rules
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CLAIM_RULES_DRL));
        //kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_TEMPLATE_FILE));

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}

