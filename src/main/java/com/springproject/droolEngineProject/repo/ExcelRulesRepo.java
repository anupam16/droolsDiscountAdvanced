package com.springproject.droolEngineProject.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.droolEngineProject.model.ExcelRule;


public interface ExcelRulesRepo extends JpaRepository<ExcelRule, Integer> {
    Optional<ExcelRule> findByRuleName(String ruleName);

     List<ExcelRule> findByFortype(String fortype);
}



