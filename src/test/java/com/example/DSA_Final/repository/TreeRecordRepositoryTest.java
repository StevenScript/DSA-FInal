package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.TreeRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Tests for TreeRecordRepository.
 * Verifies that a TreeRecord can be saved and later retrieved with the expected data.
 */
@DataJpaTest
public class TreeRecordRepositoryTest {

    @Autowired
    private TreeRecordRepository repository;

    @Test
    void testSaveAndRetrieve() {
        // Create a new TreeRecord with sample input numbers and JSON representation.
        TreeRecord record = new TreeRecord();
        record.setInputNumbers("5,2,7");
        record.setTreeJson("{\"value\":5,\"left\":{\"value\":2},\"right\":{\"value\":7}}");

        // Save the record and verify it gets an auto-generated ID.
        TreeRecord savedRecord = repository.save(record);
        Assertions.assertNotNull(savedRecord.getId(), "Saved record should have an auto-generated ID");

        // Retrieve the record and verify the input numbers and JSON match the expected values.
        TreeRecord foundRecord = repository.findById(savedRecord.getId()).orElse(null);
        Assertions.assertNotNull(foundRecord, "Should find the record by ID");
        Assertions.assertEquals("5,2,7", foundRecord.getInputNumbers());
        Assertions.assertEquals("{\"value\":5,\"left\":{\"value\":2},\"right\":{\"value\":7}}", foundRecord.getTreeJson());
    }
}
