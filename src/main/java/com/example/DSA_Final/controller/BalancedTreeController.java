package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller managing balanced BST operations.
 * Provides an endpoint to balance an existing unbalanced BST and an endpoint to retrieve all balanced trees.
 */
@RestController
@RequestMapping("/api")
public class BalancedTreeController {

    private final TreeService treeService;

    /**
     * Constructs the controller with the required TreeService.
     *
     * @param treeService Service managing tree creation and balancing.
     */
    public BalancedTreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    /**
     * Balances an existing unbalanced BST given its ID.
     * If the original TreeRecord is not found, returns HTTP 404.     *
     * Endpoint: POST /api/balance/{treeId}
     *
     * @param treeId ID of the unbalanced tree to be balanced.
     * @return HTTP 200 with the BalancedTreeRecord or HTTP 404 if not found.
     */
    @PostMapping("/balance/{treeId}")
    public ResponseEntity<BalancedTreeRecord> balanceTree(@PathVariable Long treeId) {
        try {
            BalancedTreeRecord balancedRecord = treeService.balanceExistingTree(treeId);
            return ResponseEntity.ok(balancedRecord);
        } catch (RuntimeException ex) {
            // Return 404 Not Found if the tree record doesn't exist.
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all balanced BST records.     *
     * Endpoint: GET /api/balanced
     *
     * @return HTTP 200 with an Iterable of all BalancedTreeRecord objects.
     */
    @GetMapping("/balanced")
    public ResponseEntity<Iterable<BalancedTreeRecord>> getAllBalancedTrees() {
        Iterable<BalancedTreeRecord> balancedTrees = treeService.getAllBalancedTrees();
        return ResponseEntity.ok(balancedTrees);
    }
}