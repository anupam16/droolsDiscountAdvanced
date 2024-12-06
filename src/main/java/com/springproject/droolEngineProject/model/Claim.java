package com.springproject.droolEngineProject.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Claim {
    private String type;
    private String riskLevel;
    private String message;  // Added field to hold the message

}
