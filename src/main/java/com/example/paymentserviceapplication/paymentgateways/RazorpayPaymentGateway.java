package com.example.paymentserviceapplication.paymentgateways;

import com.razorpay.*;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.util.Optional;

@Component
public class RazorpayPaymentGateway {

    private RazorpayClient razorpayClient;
    public RazorpayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }


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
            paymentLinkRequest.put("callback_url", "https://example-callback-url.com/");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest) ;
            return payment.get("short_url").toString();
        } catch(RazorpayException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String createUpiPaymentOrder(String email, String name, String phoneNumber, String orderId, Long amount, String upiId) {
        try {
            // Step 1: Create the order
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Convert to paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", orderId);

            JSONObject orderNotes = new JSONObject();
            orderNotes.put("email", email);
            orderNotes.put("name", name);
            orderNotes.put("upi_id", upiId);
            orderRequest.put("notes", orderNotes);

            Order order = razorpayClient.orders.create(orderRequest);

            // Step 2: Create a UPI payment link
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount * 100); // Must match order amount
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("reference_id", orderId);
            paymentLinkRequest.put("description", "UPI Payment for order " + orderId);

            JSONObject customer = new JSONObject();
            customer.put("name", name);
            customer.put("contact", phoneNumber);
            customer.put("email", email);
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("reminder_enable", true);
            paymentLinkRequest.put("upi_link", true); // Reintroduce to enable UPI

            // Optionally restrict to UPI (if supported)
            JSONObject paymentMethods = new JSONObject();
            paymentMethods.put("upi", true);
            paymentLinkRequest.put("payment_methods", paymentMethods);

            JSONObject paymentNotes = new JSONObject();
            paymentNotes.put("upi_id", upiId);
            paymentLinkRequest.put("notes", paymentNotes);

            PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

            return paymentLink.get("short_url").toString();

        } catch (RazorpayException exception) {
            throw new RuntimeException("Failed to create UPI payment link: " + exception.getMessage());
        }
    }
}
