package com.springproject.droolEngineProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excel_rule_table" , uniqueConstraints = @UniqueConstraint(columnNames = {"rule_name"}))
@Getter
@Setter
public class ExcelRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "rule_name")
    private String ruleName;

    @OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleCondition> conditions;

    private String actionKey;



    private String actionValue;

    // @Column(name = "version")
    // private int version;

    private String fortype;

      public String getConditionString() {
        if (conditions == null || conditions.isEmpty()) {
            return ""; // No conditions
        }
        return conditions.stream()
                .map(cond -> cond.getValue().replace("?", "\"")) // Extract the value of each condition
                .collect(Collectors.joining(" && ")); // Join with " AND "
    }

    
    // public List<ExcelRuleTemplate> getConditionExcelTemplate() {
    //     if (conditions == null || conditions.isEmpty()) {
    //         return new ArrayList<ExcelRuleTemplate>(); // No conditions
    //     }

    //     return  conditions.stream().map(e -> new ExcelRuleTemplate(e.ruleName,this.getConditionString(),Float.parseFloat(this.actionValue)) )

    // }


}
