package com.springproject.droolEngineProject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.droolEngineProject.model.ExcelRule;
import com.springproject.droolEngineProject.model.RuleCondition;

public interface RuleConditionRepository extends JpaRepository<RuleCondition, Integer>{


     Optional<RuleCondition> findByRuleAndValue(ExcelRule rule, String value);
}
