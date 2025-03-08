package com.example.paymentserviceapplication.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/stripeWebhook")
    public String receiveEvents(@RequestBody String event) {
        System.out.println(event);
        return event;
    }
}
