package com.katachallenge.bookstore.controller;

import com.katachallenge.bookstore.model.CartItem;
import com.katachallenge.bookstore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    // Test for the addToCart method
    @Test
    @WithMockUser(username = "testUser") // Simulates an authenticated user
    public void testAddToCartSuccess() throws Exception {
        Long bookId = 1L;
        int quantity = 2;

        // Mocking Principal
        Principal mockPrincipal = () -> "testUser";

        // No need to mock addToCart as it doesn't return anything
        doNothing().when(cartService).addToCart(bookId, "testUser", quantity);

        mockMvc.perform(post("/api/cart/addToCart")
                        .param("bookId", String.valueOf(bookId))
                        .param("quantity", String.valueOf(quantity))
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().string("Book successfully added to cart")); // Expect success message

        verify(cartService, times(1)).addToCart(bookId, "testUser", quantity);
    }

    // Test for the getCart method (non-empty cart)
    @Test
    @WithMockUser(username = "testUser") // Simulates an authenticated user
    public void testGetCartSuccess() throws Exception {
        Principal mockPrincipal = () -> "testUser";

        // Create some mock cart items
        List<CartItem> mockCartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        mockCartItems.add(cartItem);

        when(cartService.getCartItems("testUser")).thenReturn(mockCartItems);

        mockMvc.perform(get("/api/cart/getCart")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$").isArray()); // Expect the result to be a list

        verify(cartService, times(1)).getCartItems("testUser");
    }

    // Test for the getCart method (empty cart)
    @Test
    @WithMockUser(username = "testUser") // Simulates an authenticated user
    public void testGetCartEmpty() throws Exception {
        Principal mockPrincipal = () -> "testUser";

        // Mock an empty cart
        when(cartService.getCartItems("testUser")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/cart/getCart")
                        .principal(mockPrincipal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expect HTTP 204 NO CONTENT

        verify(cartService, times(1)).getCartItems("testUser");
    }
}