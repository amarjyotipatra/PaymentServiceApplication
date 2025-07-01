package com.example.paymentserviceapplication.services;

import com.example.paymentserviceapplication.paymentgateways.IPaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentServiceTests {

    @Mock
    private IPaymentGateway paymentGateway;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(paymentGateway);
    }

    @Test
    void testInitiatePayment_ShouldCallPaymentGateway() {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String phoneNumber = "1234567890";
        String orderId = "order123";
        Long amount = 1000L;
        String expectedPaymentLink = "https://payment.example.com/link123";

        when(paymentGateway.getPaymentLink(email, name, phoneNumber, orderId, amount))
                .thenReturn(expectedPaymentLink);

        // Act
        String actualPaymentLink = paymentService.intiatePayment(email, name, phoneNumber, orderId, amount);

        // Assert
        assertEquals(expectedPaymentLink, actualPaymentLink);
        verify(paymentGateway, times(1)).getPaymentLink(email, name, phoneNumber, orderId, amount);
    }
}
