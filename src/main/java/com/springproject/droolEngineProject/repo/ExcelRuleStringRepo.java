package com.springproject.droolEngineProject.repo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springproject.droolEngineProject.model.ExcelRuleStringCondition;

public interface ExcelRuleStringRepo extends JpaRepository<ExcelRuleStringCondition, Integer> {


    Optional<ExcelRuleStringCondition> findByRuleName(String ruleName);

     List<ExcelRuleStringCondition> findByFortype(String fortype);
    
}
