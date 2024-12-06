package com.springproject.droolEngineProject.controller;

import com.springproject.droolEngineProject.model.Order;
import com.springproject.droolEngineProject.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;

@RestController
public class OrderController {

    private final OrderService orderService;

    

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/getDiscount")
    public Order getDiscount(@RequestBody Order order) throws FileNotFoundException{
        return orderService.getDiscountForOrderV2(order);
    }

    @PostMapping("/getExcelDiscount")
    public Order getExcelDiscount(@RequestBody Order order) throws FileNotFoundException{
        return orderService.getDiscountForOrderExcelRule(order);
    }


    @PostMapping("/getExcelStringDiscount")
    public Order getExcelStringConditionDiscount(@RequestBody Order order) throws FileNotFoundException{
        return orderService.getDiscountForOrderExcelRuleString(order);
    }
}
