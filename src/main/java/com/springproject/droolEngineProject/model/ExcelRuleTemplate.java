package com.springproject.droolEngineProject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelRuleTemplate{


    
    
    private String rulename;

    private String action;

    private Float value;


    public ExcelRuleTemplate(String rulename, String action, Float value){

        this.rulename=rulename;
        this.action=action;
        this.value=value;
    
    }

    @Override
    public String toString() {
        return "ExcelRuleTemplate{name='" + rulename + "',value='" + value + "', action=" + action + "}";
    }
    
}