package com.example.paymentserviceapplication.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Override
    public String intiatePayment(String email,String name, String phoneNumber,String orderId, Long amount) {
        return "";
    }
}
