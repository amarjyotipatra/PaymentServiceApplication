package com.example.paymentserviceapplication.controllers;

import com.example.paymentserviceapplication.dtos.PaymentDTO;
import com.example.paymentserviceapplication.services.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitiatePayment_ShouldReturnPaymentLink() throws Exception {
        // Arrange
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setEmail("test@example.com");
        paymentDTO.setName("Test User");
        paymentDTO.setPhoneNumber("1234567890");
        paymentDTO.setOrderId("order123");
        paymentDTO.setAmount(1000L);

        String expectedPaymentLink = "https://payment.example.com/link123";

        when(paymentService.intiatePayment(
                anyString(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(expectedPaymentLink);

        // Act & Assert
        mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedPaymentLink));
    }
}
