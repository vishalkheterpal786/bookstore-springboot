package com.katachallenge.bookstore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a CustomerOrder entity in the system.
 * This class contains information about the CustomerOrder.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer user;

    private double totalPrice;


}
