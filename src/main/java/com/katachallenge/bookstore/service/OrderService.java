package com.katachallenge.bookstore.service;

import com.katachallenge.bookstore.exceptions.UserNotFoundException;
import com.katachallenge.bookstore.model.CartItem;
import com.katachallenge.bookstore.model.CustomerOrder;
import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.repository.OrderRepository;
import com.katachallenge.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service class for handling the order placement process in the book-store.
 * This class is responsible for placing an order for a user, calculating the total price
 * based on the items in the user's cart, saving the order to the database, and clearing
 * the user's cart after the order is successfully placed.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CustomerRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, CustomerRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    /**
     * Places an order for the given user, calculates the total price of items in the user's cart,
     * and clears the cart after the order is placed.
     * <p>
     * This method first retrieves the user by their username and fetches the items from their cart.
     * It then calculates the total cost based on the price and quantity of each item in the cart.
     * The order is saved to the database, and the user's cart is cleared afterward.
     * </p>
     *
     * @param username the username of the user placing the order.
     * @throws UserNotFoundException if the user with the given username does not exist.
     */
    public void placeOrder(String username) {
        // Fetch the customer (user) by username; throw exception if not found
        Customer user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        // Retrieve the items in the user's cart
        List<CartItem> cartItems = cartService.getCartItems(username);

        // Calculate the total price for the order based on cart items
        double total = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();

        // Create a new order and set user and total price
        CustomerOrder order = new CustomerOrder();
        order.setUser(user);
        // Optional: Set the cart items to the order if needed
        // order.setItems(cartItems);
        order.setTotalPrice(total);

        // Save the order in the repository (database)
        orderRepository.save(order);

        // Clear the user's cart after the order is placed
        cartService.clearCart(user);
    }
}