package com.katachallenge.bookstore.controller;

import com.katachallenge.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
/**
 * REST Controller for managing customer orders.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to place a new order for the authenticated user.
     */
    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(Principal principal) {
        try {
            // Place the order for the current user
            orderService.placeOrder(principal.getName());

            // Return a success message if the order is placed successfully
            return ResponseEntity.ok("Order placed successfully.");
        } catch (IllegalArgumentException e) {
            // Handle business logic issues (e.g., cart is empty)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error placing order: " + e.getMessage());
        } catch (Exception e) {
            // Handle general exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while placing the order.");
        }
    }
}