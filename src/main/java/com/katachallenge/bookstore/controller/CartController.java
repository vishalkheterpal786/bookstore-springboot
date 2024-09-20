package com.katachallenge.bookstore.controller;

import com.katachallenge.bookstore.model.CartItem;
import com.katachallenge.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
/**
 * REST Controller for managing shopping cart-related operations.
 * This controller handles adding items to the cart and retrieving
 * cart items. All endpoints are prefixed with '/api/cart'.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    /**
     * Constructor-based dependency injection for CartService.
     *
     * @param cartService the service responsible for cart-related operations.
     */
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Endpoint to add an item to the cart.
     */
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestParam Long bookId,
                                            @RequestParam int quantity,
                                            Principal principal) {

            cartService.addToCart(bookId, principal.getName(), quantity);
            return ResponseEntity.ok("Book successfully added to cart.");
    }

    /**
     * Endpoint to retrieve the authenticated user's cart items.
     * <p>
     * This method handles GET requests to '/api/cart/getCart' and returns the items in
     * the user's shopping cart.
     *
     * @param principal the authenticated user making the request.
     * @return ResponseEntity with a list of cart items and HTTP status:
     *         - 200 (OK) if cart items are retrieved successfully.
     *         - 204 (NO CONTENT) if the cart is empty.
     */
    @GetMapping("/getCart")
    public ResponseEntity<List<CartItem>> getCart(Principal principal) {
        List<CartItem> cartItems = cartService.getCartItems(principal.getName());

        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 NO CONTENT if cart is empty
        }
          return new ResponseEntity<>(cartItems, HttpStatus.OK); // 200 OK with the list of cart items
    }
}