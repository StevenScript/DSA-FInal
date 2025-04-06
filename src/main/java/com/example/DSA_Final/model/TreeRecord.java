package com.example.DSA_Final.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class to store the user input (numbers)
 * and the BST JSON structure.
 */
@Entity
@Table(name = "tree_records") // optional: specify a custom table name
@Getter
@Setter
@NoArgsConstructor
public class TreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputNumbers;

    @Lob
    private String treeJson;
}