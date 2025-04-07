package com.example.DSA_Final.service;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.model.BinarySearchTree;
import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.BalancedTreeRecordRepository;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreeService {

    private final TreeRecordRepository treeRepo;
    private final BalancedTreeRecordRepository balancedRepo;

    /**
     * Primary constructor that accepts both repositories:
     * - treeRepo for unbalanced BST records
     * - balancedRepo for balanced BST records
     */
    public TreeService(TreeRecordRepository treeRepo,
                       BalancedTreeRecordRepository balancedRepo) {
        this.treeRepo = treeRepo;
        this.balancedRepo = balancedRepo;
    }

    /**
     * Creates an unbalanced BST by parsing the input numbers,
     * building a BinarySearchTree, converting to JSON,
     * and storing as a TreeRecord in treeRepo.
     */
    public TreeRecord createAndStoreTree(String numbers) {
        // 1) Parse input numbers
        String[] parts = numbers.split(",");
        BinarySearchTree bst = new BinarySearchTree();

        // 2) Insert each number into the BST
        for (String part : parts) {
            int value = Integer.parseInt(part.trim());
            bst.insert(value);
        }

        // 3) Convert BST to JSON
        String treeJson = bst.toJson();

        // 4) Create entity & save
        TreeRecord record = new TreeRecord();
        record.setInputNumbers(numbers);
        record.setTreeJson(treeJson);

        return treeRepo.save(record);
    }

    /**
     * Retrieves all unbalanced BST records from the database.
     */
    public Iterable<TreeRecord> getAllTrees() {
        return treeRepo.findAll();
    }

    /**
     * Takes an existing unbalanced BST (by ID),
     * rebuilds & balances it, and then stores a new BalancedTreeRecord.
     *
     * @param treeId The ID of the existing unbalanced TreeRecord
     * @return A new BalancedTreeRecord referencing the original tree
     */
    public BalancedTreeRecord balanceExistingTree(Long treeId) {
        // 1) Find the unbalanced TreeRecord
        Optional<TreeRecord> opt = treeRepo.findById(treeId);
        if (opt.isEmpty()) {
            throw new RuntimeException("TreeRecord not found for id " + treeId);
        }
        TreeRecord original = opt.get();

        // 2) Build a BST from the inputNumbers
        BinarySearchTree bst = new BinarySearchTree();
        String[] parts = original.getInputNumbers().split(",");
        for (String part : parts) {
            bst.insert(Integer.parseInt(part.trim()));
        }

        // 3) Balance the BST
        bst.balance();
        String balancedJson = bst.toJson();

        // 4) Create BalancedTreeRecord referencing the original ID
        BalancedTreeRecord balancedRec = new BalancedTreeRecord();
        balancedRec.setOriginalTreeId(original.getId());
        balancedRec.setInputNumbers(original.getInputNumbers());
        balancedRec.setBalancedTreeJson(balancedJson);

        // 5) Save & return
        return balancedRepo.save(balancedRec);
    }

    /**
     * Retrieves all balanced BST records from the database.
     */
    public Iterable<BalancedTreeRecord> getAllBalancedTrees() {
        return balancedRepo.findAll();
    }
}
