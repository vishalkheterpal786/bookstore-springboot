package com.katachallenge.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password");

        Customer registeredCustomer = new Customer();
        registeredCustomer.setUsername("testuser");

        when(userService.registerUser(customer)).thenReturn(registeredCustomer);

        // Act & Assert
        mockMvc.perform(post("/api/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).registerUser(customer);
    }

    @Test
    void testLoginCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password");

        String jwtToken = "dummy-jwt-token";

        when(userService.loginUser(customer)).thenReturn(jwtToken);

        // Act & Assert
        mockMvc.perform(post("/api/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Login successful"))
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken));

        verify(userService, times(1)).loginUser(customer);
    }
}