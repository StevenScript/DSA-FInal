package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller responsible for handling operations related to unbalanced BST records.
 * Provides endpoints to create a new TreeRecord and to retrieve all stored TreeRecords.
 */
@RestController
@RequestMapping("/api/trees")
public class TreeController {

    @Autowired
    private TreeService treeService;

    /**
     * Creates a new unbalanced BST record.
     * Expects a JSON payload with a "numbers" property.
     * Rejects the request if numbers are missing or empty.
     * Example request body: { "numbers": "5,2,7" }
     *
     * @param payload map containing the user input under key "numbers"
     * @return HTTP 200 with the saved TreeRecord, or 400 if input is invalid
     */
    @PostMapping
    public ResponseEntity<TreeRecord> createTree(@RequestBody Map<String, String> payload) {
        // "numbers" is the key in the incoming JSON
        String numbers = payload.get("numbers");
        if (numbers == null || numbers.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        TreeRecord saved = treeService.createAndStoreTree(numbers);
        return ResponseEntity.ok(saved);
    }

    /**
     * Retrieves all unbalanced BST records from the database.
     *
     * @return HTTP 200 with an Iterable of all TreeRecord objects.
     */
    @GetMapping
    public ResponseEntity<Iterable<TreeRecord>> getAllTrees() {
        Iterable<TreeRecord> allRecords = treeService.getAllTrees();
        return ResponseEntity.ok(allRecords);
    }
}
