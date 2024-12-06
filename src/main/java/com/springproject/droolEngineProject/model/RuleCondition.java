package com.springproject.droolEngineProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.springproject.droolEngineProject.model.ExcelRule;

@Entity
@Table(name = "rule_condition")
@Getter
@Setter
public class RuleCondition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private ExcelRule rule; 

    @Column(nullable = false)
    private String value; 

   

}
