package com.example.paymentserviceapplication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    String email;
    String name;
    String phoneNumber;
    String orderId;
    Long amount;
}
