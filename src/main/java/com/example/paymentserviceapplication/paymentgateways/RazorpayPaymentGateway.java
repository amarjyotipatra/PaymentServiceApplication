package com.example.paymentserviceapplication.paymentgateways;

import com.razorpay.*;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.util.Optional;

@Component
public class RazorpayPaymentGateway implements IPaymentGateway {

    private RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String getPaymentLink(String email,String name, String phoneNumber,String orderId, Long amount) {
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", true);
            paymentLinkRequest.put("first_min_partial_amount", 100);
            paymentLinkRequest.put("expire_by", 1744034195);
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "Payment for policy no #23456");
            JSONObject customer = new JSONObject();
            customer.put("name", phoneNumber);
            customer.put("contact", name);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
            notes.put("policy_name", "Jeevan Bima");
            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "https://61e1d01eaf98a20d1787e232--amarjyotipatra-portfolio.netlify.app/#");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest) ;
            return payment.get("short_url").toString();
        } catch(RazorpayException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

}
