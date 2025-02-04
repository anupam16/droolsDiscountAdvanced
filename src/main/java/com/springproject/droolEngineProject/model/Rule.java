package com.springproject.droolEngineProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rule_table")
@Getter
@Setter
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    @Column(name = "ifcondition")
    private String ifcondition;

    @Column(name = "thencondition")
    private String thencondition;

    @Column(name = "version")
    private int version;

    @Column(name = "fortype")
    private String fortype;
}


