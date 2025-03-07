package com.example.paymentserviceapplication.services;

import org.springframework.stereotype.Component;

@Component
public interface IPaymentService {

    String intiatePayment(String email,String name, String phoneNumber,String orderId, Long amount);

    String intiateUpiPayment(String email, String name, String phoneNumber, String orderId, Long amount, String upiId);
}
