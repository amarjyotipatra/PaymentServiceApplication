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
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping
    public String intiatiatePayment(@RequestBody PaymentDTO paymentDto){

        return paymentService.intiatePayment(paymentDto.getEmail(),paymentDto.getName(),paymentDto.getPhoneNumber(),paymentDto.getOrderId(),paymentDto.getAmount());
    }

}
