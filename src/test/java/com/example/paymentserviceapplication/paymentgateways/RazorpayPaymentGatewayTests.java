package com.example.paymentserviceapplication.paymentgateways;

import com.razorpay.PaymentLink;
import com.razorpay.PaymentLinkClient;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RazorpayPaymentGatewayTests {

    @Mock
    private RazorpayClient razorpayClient;

    @Mock
    private PaymentLinkClient paymentLinkClient;

    @Mock
    private PaymentLink paymentLink;

    private RazorpayPaymentGateway razorpayPaymentGateway;

    @BeforeEach
    void setUp() {
        // Set the paymentLink field on the razorpayClient mock
        ReflectionTestUtils.setField(razorpayClient, "paymentLink", paymentLinkClient);

        // Create the gateway with the mocked client
        razorpayPaymentGateway = new RazorpayPaymentGateway(razorpayClient);
    }

    @Test
    void testGetPaymentLink_Success() throws RazorpayException {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String phoneNumber = "1234567890";
        String orderId = "order123";
        Long amount = 1000L;

        // Create a mock response
        JSONObject responseJson = new JSONObject();
        responseJson.put("short_url", "https://rzp.io/i/abcdef");

        // Set up the mock to return our test PaymentLink
        when(paymentLinkClient.create(any(JSONObject.class))).thenReturn(paymentLink);
        when(paymentLink.get("short_url")).thenReturn("https://rzp.io/i/abcdef");

        // Act
        String result = razorpayPaymentGateway.getPaymentLink(email, name, phoneNumber, orderId, amount);

        // Assert
        assertEquals("https://rzp.io/i/abcdef", result);
        verify(paymentLinkClient, times(1)).create(any(JSONObject.class));
    }

    @Test
    void testGetPaymentLink_ExceptionHandling() throws RazorpayException {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        String phoneNumber = "1234567890";
        String orderId = "order123";
        Long amount = 1000L;

        // Set up the mock to throw an exception
        when(paymentLinkClient.create(any(JSONObject.class)))
            .thenThrow(new RazorpayException("Payment link creation failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            razorpayPaymentGateway.getPaymentLink(email, name, phoneNumber, orderId, amount);
        });

        assertEquals("Payment link creation failed", exception.getMessage());
    }
}
