package com.example.DSA_Final.service;

import com.example.DSA_Final.model.BinarySearchTree;
import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class TreeService {

    private final TreeRecordRepository repository;

    // Constructor injection of the repository
    public TreeService(TreeRecordRepository repository) {
        this.repository = repository;
    }

    public TreeRecord createAndStoreTree(String numbers) {
        // Parse input numbers
        String[] parts = numbers.split(",");
        // Build BST
        BinarySearchTree bst = new BinarySearchTree();
        for (String part : parts) {
            int value = Integer.parseInt(part.trim());
            bst.insert(value);
        }

        // Convert BST to JSON
        String treeJson = bst.toJson();

        // Create entity
        TreeRecord record = new TreeRecord();
        record.setInputNumbers(numbers);
        record.setTreeJson(treeJson);

        // Save via repository
        TreeRecord saved = repository.save(record);

        return saved;
    }

    // Method to return multiple trees
    public Iterable<TreeRecord> getAllTrees() {
        return repository.findAll();
    }
}
