package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.BalancedTreeRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests for BalancedTreeRecordRepository.
 * Ensures that BalancedTreeRecord entities are stored and retrieved with correct data.
 */
@DataJpaTest
public class BalancedTreeRecordRepositoryTest {

    @Autowired
    private BalancedTreeRecordRepository repo;

    @Test
    void testSaveAndRetrieveBalancedTree() {
        // Create a balanced BST record with sample values.
        BalancedTreeRecord balancedRecord = new BalancedTreeRecord();
        balancedRecord.setOriginalTreeId(1L);
        balancedRecord.setInputNumbers("1,2,3,4,5");
        balancedRecord.setBalancedTreeJson("{\"value\":3, ...}");

        // Save the record and confirm an ID is assigned.
        BalancedTreeRecord saved = repo.save(balancedRecord);
        Assertions.assertNotNull(saved.getId());

        // Retrieve the record and verify the data.
        BalancedTreeRecord found = repo.findById(saved.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals("1,2,3,4,5", found.getInputNumbers());
        Assertions.assertTrue(found.getBalancedTreeJson().contains("\"value\":3"));
    }
}
