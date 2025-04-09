package com.example.DSA_Final.service;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.model.BinarySearchTree;
import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.BalancedTreeRecordRepository;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to manage BST creation and balancing.
 * Uses two repositories:
 * - treeRepo: stores unbalanced BST records.
 * - balancedRepo: stores balanced BST records.
 */
@Service
public class TreeService {

    private final TreeRecordRepository treeRepo;
    private final BalancedTreeRecordRepository balancedRepo;

    /**
     * Constructs the service with both repositories.
     *
     * @param treeRepo      Repository for unbalanced BSTs.
     * @param balancedRepo  Repository for balanced BSTs.
     */
    public TreeService(TreeRecordRepository treeRepo,
                       BalancedTreeRecordRepository balancedRepo) {
        this.treeRepo = treeRepo;
        this.balancedRepo = balancedRepo;
    }

    /**
     * Creates an unbalanced BST from a comma-separated string of numbers.
     * Inserts the numbers into a BST, converts the tree to JSON, and saves it.
     *
     * @param numbers comma-separated integers (e.g., "5,2,7")
     * @return the saved TreeRecord
     */
    public TreeRecord createAndStoreTree(String numbers) {
        String[] parts = numbers.split(",");
        BinarySearchTree bst = new BinarySearchTree();
        for (String part : parts) {
            int value = Integer.parseInt(part.trim());
            bst.insert(value);
        }
        String treeJson = bst.toJson();
        TreeRecord record = new TreeRecord();
        record.setInputNumbers(numbers);
        record.setTreeJson(treeJson);
        return treeRepo.save(record);
    }

    /**
     * Retrieves all unbalanced BST records.
     *
     * @return Iterable of TreeRecord
     */
    public Iterable<TreeRecord> getAllTrees() {
        return treeRepo.findAll();
    }

    /**
     * Balances an existing unbalanced BST (identified by treeId) by
     * rebuilding the tree and saving it as a new BalancedTreeRecord.
     *
     * @param treeId ID of the original TreeRecord
     * @return the saved BalancedTreeRecord
     * @throws RuntimeException if the original record is not found
     */
    public BalancedTreeRecord balanceExistingTree(Long treeId) {
        Optional<TreeRecord> opt = treeRepo.findById(treeId);
        if (opt.isEmpty()) {
            throw new RuntimeException("TreeRecord not found for id " + treeId);
        }
        TreeRecord original = opt.get();

        // Rebuild the BST from the stored numbers.
        BinarySearchTree bst = new BinarySearchTree();
        String[] parts = original.getInputNumbers().split(",");
        for (String part : parts) {
            bst.insert(Integer.parseInt(part.trim()));
        }

        // Balance the tree and get JSON representation.
        bst.balance();
        String balancedJson = bst.toJson();

        // Create and save the balanced record linked to the original.
        BalancedTreeRecord balancedRec = new BalancedTreeRecord();
        balancedRec.setOriginalTreeId(original.getId());
        balancedRec.setInputNumbers(original.getInputNumbers());
        balancedRec.setBalancedTreeJson(balancedJson);
        return balancedRepo.save(balancedRec);
    }

    /**
     * Retrieves all balanced BST records.
     *
     * @return Iterable of BalancedTreeRecord
     */
    public Iterable<BalancedTreeRecord> getAllBalancedTrees() {
        return balancedRepo.findAll();
    }
}