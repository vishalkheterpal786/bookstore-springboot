package com.katachallenge.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a CartItem entity in the system.
 * This class contains information about the cart .
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Book book;

    @ManyToOne
    @JsonIgnore
    @NotNull
    private Customer user;
    @NotNull
    private int quantity;

}
