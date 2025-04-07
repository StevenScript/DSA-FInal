package com.example.DSA_Final.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "balanced_trees")
@Getter
@Setter
@NoArgsConstructor
public class BalancedTreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long originalTreeId;
    private String inputNumbers;

    @Lob
    private String balancedTreeJson;
}
