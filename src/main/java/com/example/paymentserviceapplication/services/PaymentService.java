package com.example.paymentserviceapplication.services;

import com.example.paymentserviceapplication.paymentgateways.IPaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    private IPaymentGateway paymentGateway;

    public PaymentService(IPaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public String intiatePayment(String email,String name, String phoneNumber,String orderId, Long amount) {
        return paymentGateway.getPaymentLink(email,name,phoneNumber,orderId,amount);
    }

}
