package com.springproject.droolEngineProject.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springproject.droolEngineProject.model.ApiResponse;
import com.springproject.droolEngineProject.model.Rule;
import com.springproject.droolEngineProject.model.RuleCondition;
import com.springproject.droolEngineProject.repo.DroolRulesRepo;
import com.springproject.droolEngineProject.repo.RuleConditionRepository;
import com.springproject.droolEngineProject.repo.ExcelRulesRepo;
import com.springproject.droolEngineProject.repo.ExcelRuleStringRepo;
import com.springproject.droolEngineProject.model.ExcelRule;
import com.springproject.droolEngineProject.model.ExcelRuleStringCondition;

@RestController
public class RulesController {

    public final DroolRulesRepo rulesRepo;

    public RulesController(DroolRulesRepo rulesRepo) {
        this.rulesRepo = rulesRepo;
    }

    @Autowired
    private RuleConditionRepository ruleConditionRepository;

    @Autowired
    private ExcelRulesRepo excelRulesRepo;

    @Autowired
    private ExcelRuleStringRepo excelRuleStringRepo;

    // @PostMapping("/rule")
    // public void addRule (@RequestBody Rule rule) {
    //     rulesRepo.save(rule);
    // }
    @PostMapping("/rule")
    public ResponseEntity<ApiResponse<Rule>> addRule(@RequestBody Rule rule) {
        if (rule == null) {
            ApiResponse<Rule> response = new ApiResponse<>(400, "BAD_REQUEST", "Invalid rule data provided.", null);
            return ResponseEntity.badRequest().body(response); // HTTP 400 for invalid payload
        }

        Rule savedRule = rulesRepo.save(rule); // Save the rule
        ApiResponse<Rule> response = new ApiResponse<>(201, "CREATED", "Rule created successfully.", savedRule);
        return ResponseEntity.status(201).body(response); // HTTP 201 with the saved rule
    }

    // @GetMapping("/rules")
    // public List<Rule> getRules () {
    //     return new ArrayList<>(rulesRepo.findAll());
    // }
    @GetMapping("/rules")
    public ResponseEntity<ApiResponse<List<Rule>>> getRules() {
        List<Rule> rules = rulesRepo.findAll();

        if (rules.isEmpty()) {
            ApiResponse<List<Rule>> response = new ApiResponse<>(204, "NO_CONTENT", "No rules found.", null);
            return ResponseEntity.status(204).body(response); // HTTP 204 if no rules are found
        }

        ApiResponse<List<Rule>> response = new ApiResponse<>(200, "OK", "Rules retrieved successfully.", rules);
        return ResponseEntity.ok(response); // HTTP 200 with the list of rules
    }

    @GetMapping("/rules/{id}")
    public ResponseEntity<ApiResponse<Rule>> getRuleById(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            ApiResponse<Rule> response = new ApiResponse<>(400, "BAD_REQUEST", "Invalid ID provided.", null);
            return ResponseEntity.badRequest().body(response);
        }

        return rulesRepo.findById(id).map(rule -> {
            ApiResponse<Rule> response = new ApiResponse<>(200, "OK", "Rule retrieved successfully.", rule);
            return ResponseEntity.ok(response); // HTTP 200 with the retrieved rule
        }).orElseGet(() -> {
            ApiResponse<Rule> response = new ApiResponse<>(404, "NOT_FOUND", "Rule not found.", null);
            return ResponseEntity.status(404).body(response); // HTTP 404 if Rule not found
        });
    }

//     @GetMapping("/rules/{id}")
// public ResponseEntity<Rule> getRuleById(@PathVariable("id") Integer id) {
// if (id == null || id <= 0) {
// return ResponseEntity.badRequest().body(null); // Return HTTP 400 for invalid ID
// }
// return rulesRepo.findById(id).map(ResponseEntity::ok) // HTTP 200 with the Rule
// .orElse(ResponseEntity.notFound().build()); // HTTP 404 if Rule not found
// }
// @PutMapping("/rule/{id}")
// public ResponseEntity<Rule> updateRule(@PathVariable("id") Integer id, @RequestBody Rule updatedRule) {
// 	if (id == null || id <= 0 || updatedRule == null) {
// 		return ResponseEntity.badRequest().build(); // HTTP 400 for invalid ID or payload
// 	}
// 	return rulesRepo.findById(id).map(existingRule -> {
// 		// Update fields of the existing rule with values from updatedRule
// 		existingRule.setIfcondition(updatedRule.getIfcondition()); // Example: updating 'name' field
// 		existingRule.setThencondition(updatedRule.getThencondition()); // Example: updating 'description' field
//         existingRule.setFortype(updatedRule.getFortype());
//         existingRule.setVersion(updatedRule.getVersion());
// 		// Add additional field updates as necessary
// 		rulesRepo.save(existingRule); // Save the updated rule
// 		return ResponseEntity.ok(existingRule); // HTTP 200 with the updated rule
// 	}).orElse(ResponseEntity.notFound().build()); // HTTP 404 if Rule not found
// }
// @DeleteMapping("/rule/{id}")
// public ResponseEntity<Object> deleteRule(@PathVariable("id") Integer id) {
// 	if (id == null || id <= 0) {
// 		return ResponseEntity.badRequest().build(); // HTTP 400 for invalid ID
// 	}
// 	return rulesRepo.findById(id).map(rule -> {
// 		rulesRepo.delete(rule); // Delete the rule
// 		return ResponseEntity.noContent().build(); // HTTP 204 for successful deletion
// 	}).orElse(ResponseEntity.notFound().build()); // HTTP 404 if Rule not found
// }
// }
    @PutMapping("/rule/{id}")
    public ResponseEntity<ApiResponse<Rule>> updateRule(@PathVariable("id") Integer id, @RequestBody Rule updatedRule) {
        if (id == null || id <= 0 || updatedRule == null) {
            ApiResponse<Rule> response = new ApiResponse<>(400, "BAD_REQUEST", "Invalid ID or payload.", null);
            return ResponseEntity.badRequest().body(response);
        }

        return rulesRepo.findById(id).map(existingRule -> {
            // Update fields of the existing rule with values from updatedRule
            existingRule.setIfcondition(updatedRule.getIfcondition());
            existingRule.setThencondition(updatedRule.getThencondition());
            existingRule.setFortype(updatedRule.getFortype());
            existingRule.setVersion(updatedRule.getVersion());
            // Add more fields as necessary

            Rule savedRule = rulesRepo.save(existingRule); // Save the updated rule
            ApiResponse<Rule> response = new ApiResponse<>(200, "OK", "Rule updated successfully.", savedRule);
            return ResponseEntity.ok(response); // HTTP 200 with the updated rule
        }).orElseGet(() -> {
            ApiResponse<Rule> response = new ApiResponse<>(404, "NOT_FOUND", "Rule not found.", null);
            return ResponseEntity.status(404).body(response); // HTTP 404 if Rule not found
        });
    }

    @DeleteMapping("/rule/{id}")
    public ResponseEntity<ApiResponse> deleteRule(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            ApiResponse response = new ApiResponse<>(400, "BAD_REQUEST", "Invalid ID provided.", null);
            return ResponseEntity.badRequest().body(response);
        }

        return rulesRepo.findById(id).map(rule -> {
            rulesRepo.delete(rule); // Delete the rule
            ApiResponse response = new ApiResponse<>(204, "NO_CONTENT", "Rule deleted successfully.", null);
            return ResponseEntity.status(204).body(response); // Return 204 status with response
        }).orElseGet(() -> {
            ApiResponse response = new ApiResponse<>(404, "NOT_FOUND", "Rule not found.", null);
            return ResponseEntity.status(404).body(response); // Return 404 status with response
        });
    }

// @PostMapping("/upload")
// public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
//     try {
//         InputStream inputStream = file.getInputStream();
//         Workbook workbook = WorkbookFactory.create(inputStream);
//         Sheet sheet = workbook.getSheetAt(0); // Assume first sheet contains the rules
//         List<ExcelRule> rules = new ArrayList<>();
//         for (Row row : sheet) {
//             if (row.getRowNum() == 0) continue; // Skip header row
//             ExcelRule rule = new ExcelRule();
//             rule.setRuleName(row.getCell(0).getStringCellValue());
//             rule.setFortype(row.getCell(4).getStringCellValue());
//             // Parse the conditions dynamically
//             String conditionString = row.getCell(1).getStringCellValue();
//             String[] conditionArray = conditionString.split("AND"); // Split by "AND"
//             List<RuleCondition> conditions = new ArrayList<>();
//             for (String conditionText : conditionArray) {
//                 RuleCondition ruleCondition = new RuleCondition();
//                 ruleCondition.setValue(conditionText.trim()); // Set each condition
//                 // Associate the rule with this condition
//                 ruleCondition.setRule(rule);
//                 conditions.add(ruleCondition);
//             }
//             rule.setConditions(conditions); // Set the list of conditions for the rule
//             // Parse the actions
//             rule.setActionKey(row.getCell(2).getStringCellValue());
//             rule.setActionValue(String.valueOf(row.getCell(3).getStringCellValue()));
//             // rule.setActionValue("10");
//             // Save the rule (which will save the conditions as well due to cascading)
//             rules.add(rule);
//         }
//         excelRulesRepo.saveAll(rules); // Save all rules, including their conditions
//         workbook.close();
//         return ResponseEntity.ok("Rules uploaded and saved successfully!");
//     } catch (Exception e) {
//         e.printStackTrace();
//         return ResponseEntity.status(500).body("Failed to upload rules: " + e.getMessage());
//     }
// }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assume first sheet contains the rules

            List<ExcelRule> rules = new ArrayList<>();
            System.out.println("--------------------------------------------------1");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }
                if (row.getCell(0) == null
                        || row.getCell(0).getCellType() == CellType.BLANK
                        || (row.getCell(0).getCellType() == CellType.STRING && row.getCell(0).getStringCellValue().trim().isEmpty())
                        || (row.getCell(0).getCellType() == CellType.NUMERIC && row.getCell(0).getNumericCellValue() == 0)) {

                    System.out.println("--------------------------------------------------if");
                    continue;
                }

                System.out.println("--------------------------------------------------8" + row.getCell(0));
                // ExcelRule rule = new ExcelRule();

                // Get Rule Name
                String ruleName = getCellValue(row.getCell(0));
                //   rule.setRuleName(ruleName);

                // Get Fortype
                String fortype = getCellValue(row.getCell(4));
                //   rule.setFortype(fortype);

                // Check if rule already exists by ruleName
                Optional<ExcelRule> existingRuleOpt = excelRulesRepo.findByRuleName(ruleName);

                System.out.println("--------------------------------------------------2" + existingRuleOpt.toString());
                ExcelRule rule;
                String conditionString = getCellValue(row.getCell(1)).replace("\u8221", "\"");

                System.out.println("--------------------------------------------------re[aaaa] " +  getCellValue(row.getCell(1)).replace("\u8221", "\"")) ;

             

String originalString = conditionString; // Your original string

try {
    // Convert the string to a byte array using UTF-8 encoding
    byte[] utf8Bytes = originalString.getBytes("UTF-8");

    // If you want to print or check the byte array
     char[] ch=new String(utf8Bytes, "UTF-8").toCharArray(); 
     for (int i = 0; i < ch.length; i++) {
        int code = (int) ch[i];  // Cast char to int to get the ASCII/Unicode code point]
        System.out.println("Character: " + ch[i] + " | Code: " + code);
        if(code==8220 || code==8221){
            ch[i]='\"';

        }
          

    }// This will display the string in UTF-8

      System.out.println("--------------------------------------------------reneww "+ new String(ch));
      originalString=new String(ch);


} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}

// System.out.println("-------------------------------------------string replace----" + originalString);

                String[] conditionArray = originalString.split("AND"); // Split by "AND"

                List<RuleCondition> conditions = new ArrayList<>();
                if (existingRuleOpt.isPresent()) {
                    rule = existingRuleOpt.get();
                    System.out.println("--------------------------------------------------3");
                    int checkCond = 0;
                    for (String conditionText : conditionArray) {

                        RuleCondition ruleCondition = new RuleCondition();
                        ruleCondition.setValue(conditionText.trim()); // Set each condition

                        // Check if the condition already exists for this rule
                        Optional<RuleCondition> existingConditionOpt = ruleConditionRepository.findByRuleAndValue(rule, conditionText.trim());

                        if (existingConditionOpt.isPresent()) {
                            checkCond++;
                        } 

                            System.out.println("----------------sslast--------------------"+ rule.toString());
                            ruleCondition.setRule(rule);
                            System.out.println("----------------sslast1--------------------"+ ruleCondition.getRule());


                        
                        rule.getConditions().add(ruleCondition);


                        // conditions.add(ruleCondition);

                    }

                    if (checkCond == conditionArray.length) {

                        return ResponseEntity.ok("Rules already present!");
                    } else {

            
                        System.out.println(rule.toString());
                        excelRulesRepo.save(rule);
                        return ResponseEntity.ok(" was already present.... just updated");

                    }

                } else {
                    System.out.println("--------------------------------------------------4");
                    ExcelRule r = new ExcelRule();
                    r.setRuleName(ruleName);
                    r.setFortype(fortype);

                    for (String conditionText : conditionArray) {

                        RuleCondition ruleCondition = new RuleCondition();
                        ruleCondition.setValue(conditionText.trim());
                        System.out.println("----------------slast--------------------"+ r.toString());
                        ruleCondition.setRule(r);

                        conditions.add(ruleCondition);

                    }

                    r.setConditions(conditions); // Set the list of conditions for the rule

                    // Parse the actions
                    String actionKey = getCellValue(row.getCell(2));
                    r.setActionKey(actionKey);

                    String actionValue = getCellValue(row.getCell(3));
                    r.setActionValue(actionValue);
                    System.out.println(row.getCell(0) + " ----  " + row.getCell(1) + "-----" + r.getConditions() + " ----  " + row.getCell(2));
                    // Save the rule (which will save the conditions as well due to cascading)
                    rules.add(r);
                }

            }
            if (rules.size() != 0) {

                System.out.println("----------------last--------------------" + rules.get(0).getRuleName() + rules.get(0).getConditions().get(0).getRule()+  rules.get(0).getConditions().get(1).getRule());
                excelRulesRepo.saveAll(rules);
                
            } else {
                workbook.close();
                return ResponseEntity.ok("not added, maybe Rules already present!");
            }

            // Save all rules, including their conditions
            workbook.close();
            return ResponseEntity.ok("Rules uploaded and saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload rules: " + e.getMessage());
        }
    }

// Utility method to get the cell value as a string
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        String cellValue = "";

        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString(); // Handle date values
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue()); // Handle numeric values
                }
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()); // Handle boolean values
                break;
            case FORMULA:
                cellValue = cell.getCellFormula(); // Handle formulas if necessary
                break;
            default:
                cellValue = ""; // Handle unsupported or empty cells
                break;
        }

        return cellValue;
    }



    @PostMapping("/uploadStringCondition")
    public ResponseEntity<String> uploadExcelStringCondition(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("--------------------------------------------------1");
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assume first sheet contains the rules

            List<ExcelRuleStringCondition> rules = new ArrayList<>();
            System.out.println("--------------------------------------------------1");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }
                if (row.getCell(0) == null
                        || row.getCell(0).getCellType() == CellType.BLANK
                        || (row.getCell(0).getCellType() == CellType.STRING && row.getCell(0).getStringCellValue().trim().isEmpty())
                        || (row.getCell(0).getCellType() == CellType.NUMERIC && row.getCell(0).getNumericCellValue() == 0)) {

                    System.out.println("--------------------------------------------------if");
                    continue;
                }

                // System.out.println("--------------------------------------------------8" + row.getCell(0));
                // ExcelRule rule = new ExcelRule();

                // Get Rule Name
                String ruleName = getCellValue(row.getCell(0));
                //   rule.setRuleName(ruleName);

                // Get Fortype
                String fortype = getCellValue(row.getCell(4));
                //   rule.setFortype(fortype);

                // Check if rule already exists by ruleName
                Optional<ExcelRuleStringCondition> existingRuleOpt = excelRuleStringRepo.findByRuleName(ruleName);

                System.out.println("--------------------------------------------------2" + existingRuleOpt.toString());
                ExcelRuleStringCondition rule;
                String conditionString = getCellValue(row.getCell(1));

                // System.out.println("--------------------------------------------------re[aaaa] " +  getCellValue(row.getCell(1)).replace("\u8221", "\"")) ;

             

String originalString = conditionString; // Your original string

try {
    // Convert the string to a byte array using UTF-8 encoding
    byte[] utf8Bytes = originalString.getBytes("UTF-8");

    // If you want to print or check the byte array
     char[] ch=new String(utf8Bytes, "UTF-8").toCharArray(); 
     for (int i = 0; i < ch.length; i++) {
        int code = (int) ch[i];  // Cast char to int to get the ASCII/Unicode code point]
        System.out.println("Character: " + ch[i] + " | Code: " + code);
        if(code==8220 || code==8221){
            ch[i]='\"';

        }
          

    }// This will display the string in UTF-8

      System.out.println("--------------------------------------------------reneww "+ new String(ch));
      originalString=new String(ch);


} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}

// System.out.println("-------------------------------------------string replace----" + originalString);

                originalString=originalString.replace("AND", " && ").replace("OR", " || "); // Split by "AND"

                List<RuleCondition> conditions = new ArrayList<>();
                if (existingRuleOpt.isPresent()) {
                    rule = existingRuleOpt.get();
                    System.out.println("--------------------------------------------------3");
                    int checkCond = 0;
                    if(originalString.equals(rule.getConditions())){

                        return ResponseEntity.ok("Rules already present!");
                    }else{

                        rule.setConditions(originalString);
                        return ResponseEntity.ok(" was already present.... just updated");
                    }

                        // RuleCondition ruleCondition = new RuleCondition();
                        // ruleCondition.setValue(conditionText.trim()); // Set each condition

                        // Check if the condition already exists for this rule
                        // Optional<RuleCondition> existingConditionOpt = ruleConditionRepository.findByRuleAndValue(rule, conditionText.trim());

                        // if (existingConditionOpt.isPresent()) {
                        //     checkCond++;
                        // } 

                            // System.out.println("----------------sslast--------------------"+ rule.toString());
                            // ruleCondition.setRule(rule);
                            // System.out.println("----------------sslast1--------------------"+ ruleCondition.getRule());


                        
                        // rule.getConditions().add(ruleCondition);


                        // conditions.add(ruleCondition);

                    }

                    // if (checkCond == conditionArray.length) {

                    //     return ResponseEntity.ok("Rules already present!");
                    // } else {

            
                        // System.out.println(rule.toString());
                        // excelRulesRepo.save(rule);
                        // return ResponseEntity.ok(" was already present.... just updated");

                    

                 else {
                    System.out.println("--------------------------------------------------4");
                    ExcelRuleStringCondition r = new ExcelRuleStringCondition();
                    r.setRuleName(ruleName);
                    r.setFortype(fortype);
                    r.setConditions(originalString);
                    
                    // for (String conditionText : conditionArray) {

                    //     RuleCondition ruleCondition = new RuleCondition();
                    //     ruleCondition.setValue(conditionText.trim());
                    //     System.out.println("----------------slast--------------------"+ r.toString());
                    //     ruleCondition.setRule(r);

                    //     conditions.add(ruleCondition);

                    // }

                    // r.setConditions(conditions); // Set the list of conditions for the rule

                    // Parse the actions
                    String actionKey = getCellValue(row.getCell(2));
                    r.setActionKey(actionKey);

                    String actionValue = getCellValue(row.getCell(3));
                    r.setActionValue(actionValue);
                    // System.out.println(row.getCell(0) + " ----  " + row.getCell(1) + "-----" + r.getConditions() + " ----  " + row.getCell(2));
                    // Save the rule (which will save the conditions as well due to cascading)
                    rules.add(r);
                }

            }
            if (rules.size() != 0) {

                // System.out.println("----------------last--------------------" + rules.get(0).getRuleName() + rules.get(0).getConditions().get(0).getRule()+  rules.get(0).getConditions().get(1).getRule());
                excelRuleStringRepo.saveAll(rules);
                
            } else {
                workbook.close();
                return ResponseEntity.ok("not added, maybe Rules already present!");
            }

            // Save all rules, including their conditions
            workbook.close();
            return ResponseEntity.ok("Rules uploaded and saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload rules: " + e.getMessage());
        }
    }

}
