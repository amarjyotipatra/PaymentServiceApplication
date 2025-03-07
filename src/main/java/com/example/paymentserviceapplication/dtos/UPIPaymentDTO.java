package com.example.paymentserviceapplication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UPIPaymentDTO {
    String name;
    String email;
    String phoneNumber;
    String orderId;
    Long amount;
    String upiId;
}
