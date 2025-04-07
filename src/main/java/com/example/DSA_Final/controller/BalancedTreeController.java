package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BalancedTreeController {

    private final TreeService treeService;

    public BalancedTreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    /**
     * Balances an existing unbalanced BST by its ID.
     * Endpoint: POST /api/balance/{treeId}
     *
     * @param treeId The ID of the unbalanced TreeRecord to balance.
     * @return A ResponseEntity containing the new BalancedTreeRecord, or 404 if not found.
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
     * Retrieves all balanced BST records.
     * Endpoint: GET /api/balanced
     *
     * @return A ResponseEntity containing an Iterable of all BalancedTreeRecord objects.
     */
    @GetMapping("/balanced")
    public ResponseEntity<Iterable<BalancedTreeRecord>> getAllBalancedTrees() {
        Iterable<BalancedTreeRecord> balancedTrees = treeService.getAllBalancedTrees();
        return ResponseEntity.ok(balancedTrees);
    }
}