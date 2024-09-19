package com.katachallenge.bookstore.service;

import com.katachallenge.bookstore.exceptions.ResourceNotFoundException;
import com.katachallenge.bookstore.exceptions.UserNotFoundException;
import com.katachallenge.bookstore.model.Book;
import com.katachallenge.bookstore.model.CartItem;
import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.repository.BookRepository;
import com.katachallenge.bookstore.repository.CartItemRepository;
import com.katachallenge.bookstore.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling business logic related to the cart.
 * This service interacts with repositories to manage cart items, including adding items to the cart,
 * retrieving cart items, and clearing the cart for a specific user.
 */
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CustomerRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, CustomerRepository userRepository, BookRepository bookRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Adds a book to the user's cart.
     * This method retrieves the customer by username and the book by its ID. It then creates
     * a cart item for the user and saves it to the database.
     *
     * @param bookId   the ID of the book to be added to the cart.
     * @param username the username of the customer adding the book.
     * @param quantity the quantity of the book to be added.
     * @throws UserNotFoundException if the user does not exist.
     * @throws ResourceNotFoundException if the user does not exist.
     */
    @Transactional
    public void addToCart(Long bookId, String username, int quantity) {
        // Fetch the customer (user) by username, throw exception if not found
        Customer user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        // Fetch the book by its ID, throw exception if not found
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setUser(user);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

    }

    /**
     * Retrieves all cart items for a specific user by their username.
     * This method fetches the customer by their username and retrieves all cart items associated with that customer.
     *
     * @param username the username of the customer whose cart items are to be retrieved.
     * @return a list of {@link CartItem} objects representing the customer's cart items.
     * @throws UserNotFoundException if the user does not exist.
     */
    public List<CartItem> getCartItems(String username) {
        // Fetch the customer (user) by username, throw exception if not found
        Customer user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(("User not found: " + username)));
        // Return all cart items for the given user
        return cartItemRepository.findByUser(user);
    }

    /**
     * Clears all items in the cart for a specific customer.
     * This method removes all cart items associated with the provided user.
     *
     * @param customer the customer whose cart should be cleared.
     */
    public void clearCart(Customer customer) {
        List<CartItem> cartItems = cartItemRepository.findByUser(customer);
        cartItemRepository.deleteAll(cartItems);
    }
}