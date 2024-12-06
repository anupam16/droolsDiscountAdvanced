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
@Table(name = "excel_rule_String_condition_table" , uniqueConstraints = @UniqueConstraint(columnNames = {"rule_name"}))
@Getter
@Setter
public class ExcelRuleStringCondition {
    

     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "rule_name")
    private String ruleName;


    private String conditions;

    private String actionKey;



    private String actionValue;

    // @Column(name = "version")
    // private int version;

    private String fortype;

}
