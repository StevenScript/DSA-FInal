package com.example.DSA_Final.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a balanced BST record.
 * Stores the JSON representation of the balanced BST along with a reference
 * to the original unbalanced tree record.
 */
@Entity
@Table(name = "balanced_trees")
@Getter
@Setter
@NoArgsConstructor
public class BalancedTreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Original TreeRecord ID from which this balanced BST was derived.
    private Long originalTreeId;

    // The original input numbers.
    private String inputNumbers;

    // JSON representation of the balanced BST.
    @Lob
    private String balancedTreeJson;
}
