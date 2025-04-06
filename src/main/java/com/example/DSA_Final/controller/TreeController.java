package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    @Autowired
    private TreeService treeService;

    /**
     * POST /api/trees
     * Expects JSON like: { "numbers": "5,2,7" }
     * Returns the saved TreeRecord (including ID and treeJson).
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
     * GET /api/trees
     * Returns an array (or list) of all stored TreeRecords.
     */
    @GetMapping
    public ResponseEntity<Iterable<TreeRecord>> getAllTrees() {
        Iterable<TreeRecord> allRecords = treeService.getAllTrees();
        return ResponseEntity.ok(allRecords);
    }
}
