package com.katachallenge.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Represents a Book entity in the system.
 * This class is mapped to the "books" table in the database and contains
 * information about the book such as its title, author, and price.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, max = 25, message = "Title must be between 2 and 25 characters")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    @Size(min = 2, max = 25, message = "Author name must be between 2 and 25 characters")
    private String author;
    @NotNull
    private double price;
}