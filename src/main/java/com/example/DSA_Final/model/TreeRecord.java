package com.example.DSA_Final.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing an unbalanced BST record.
 * Stores the original comma-separated numbers and the JSON representation of the BST.
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

    // User-provided numbers in comma-separated format.
    private String inputNumbers;

    // JSON representation of the unbalanced BST.
    @Lob
    private String treeJson;
}