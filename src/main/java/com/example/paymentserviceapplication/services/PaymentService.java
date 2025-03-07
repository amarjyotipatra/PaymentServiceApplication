package com.example.paymentserviceapplication.services;

import com.example.paymentserviceapplication.paymentgateways.RazorpayPaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    private RazorpayPaymentGateway razorpayPaymentGateway;

    public PaymentService(RazorpayPaymentGateway razorpayPaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    @Override
    public String intiatePayment(String email,String name, String phoneNumber,String orderId, Long amount) {
        return razorpayPaymentGateway.getPaymentLink(email,name,phoneNumber,orderId,amount);
    }

    @Override
    public String intiateUpiPayment(String email, String name, String phoneNumber, String orderId, Long amount, String upiId) {
        return razorpayPaymentGateway.createUpiPaymentOrder(email,name,phoneNumber,orderId,amount,upiId);
    }
}
