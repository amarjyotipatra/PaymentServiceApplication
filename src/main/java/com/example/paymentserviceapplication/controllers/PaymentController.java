package com.example.paymentserviceapplication.controllers;

import com.example.paymentserviceapplication.dtos.PaymentDTO;
import com.example.paymentserviceapplication.services.IPaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private IPaymentService paymentService;

    @PostMapping
    public String intiatiatePayment(@RequestBody PaymentDTO paymentDTO){

        return paymentService.intiatePayment();
    }
}
