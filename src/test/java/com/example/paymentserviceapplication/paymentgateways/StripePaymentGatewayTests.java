package com.example.paymentserviceapplication.paymentgateways;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StripePaymentGatewayTests {

    private StripePaymentGateway stripePaymentGateway;

    @BeforeEach
    void setUp() {
        stripePaymentGateway = new StripePaymentGateway();
        // Set the API key field
        ReflectionTestUtils.setField(stripePaymentGateway, "stripeKey", "sk_test_12345");
    }

    @Test
    void testGetPaymentLink_Success() throws StripeException {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String phoneNumber = "1234567890";
        String orderId = "order123";
        Long amount = 1000L;

        // Mock Price and PaymentLink
        Price mockPrice = Mockito.mock(Price.class);
        PaymentLink mockPaymentLink = Mockito.mock(PaymentLink.class);

        // Configure mocks
        Mockito.when(mockPrice.getId()).thenReturn("price_123456");
        Mockito.when(mockPaymentLink.getUrl()).thenReturn("https://stripe.com/pay/link123");

        try (MockedStatic<Price> mockedPrice = Mockito.mockStatic(Price.class);
             MockedStatic<PaymentLink> mockedPaymentLink = Mockito.mockStatic(PaymentLink.class)) {

            // Mock Price.create to return our mock Price for ANY PriceCreateParams
            mockedPrice.when(() -> Price.create(any(PriceCreateParams.class)))
                    .thenReturn(mockPrice);

            // Mock PaymentLink.create to return our mock PaymentLink
            mockedPaymentLink.when(() -> PaymentLink.create(any(PaymentLinkCreateParams.class)))
                    .thenReturn(mockPaymentLink);

            // Act
            String result = stripePaymentGateway.getPaymentLink(email, name, phoneNumber, orderId, amount);

            // Assert
            assertEquals("https://stripe.com/pay/link123", result);
        }
    }

    @Test
    void testGetPaymentLink_ExceptionHandling() throws StripeException {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String phoneNumber = "1234567890";
        String orderId = "order123";
        Long amount = 1000L;

        // Create a custom StripeException for testing
        StripeException stripeException = new StripeException("Payment link creation failed", null, null, 0) {};

        try (MockedStatic<Price> mockedPrice = Mockito.mockStatic(Price.class)) {
            // Mock Price.create to throw our StripeException
            mockedPrice.when(() -> Price.create(any(PriceCreateParams.class)))
                    .thenThrow(stripeException);

            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                stripePaymentGateway.getPaymentLink(email, name, phoneNumber, orderId, amount);
            });

            // The exception message should match what's thrown in the implementation
            assertEquals("Payment link creation failed", exception.getMessage());
        }
    }
}
