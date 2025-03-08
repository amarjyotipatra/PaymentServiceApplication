package com.example.paymentserviceapplication.paymentgateways;

public interface IPaymentGateway {

    public String getPaymentLink(String email,String name, String phoneNumber,String orderId, Long amount);
}
