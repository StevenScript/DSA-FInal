package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.TreeRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * This test uses @DataJpaTest, which starts
 * an in-memory database (H2) and configures JPA.
 */
@DataJpaTest
public class TreeRecordRepositoryTest {

    @Autowired
    private TreeRecordRepository repository;

    @Test
    void testSaveAndRetrieve() {
        // Arrange: create a new TreeRecord
        TreeRecord record = new TreeRecord();
        record.setInputNumbers("5,2,7");
        record.setTreeJson("{\"value\":5,\"left\":{\"value\":2},\"right\":{\"value\":7}}");

        // Act: save it in the repository
        TreeRecord savedRecord = repository.save(record);

        // Assert: confirm it has an ID and was saved correctly
        Assertions.assertNotNull(savedRecord.getId(), "Saved record should have an auto-generated ID");

        // Retrieve from DB
        TreeRecord foundRecord = repository.findById(savedRecord.getId()).orElse(null);
        Assertions.assertNotNull(foundRecord, "Should find the record by ID");
        Assertions.assertEquals("5,2,7", foundRecord.getInputNumbers());
        Assertions.assertEquals("{\"value\":5,\"left\":{\"value\":2},\"right\":{\"value\":7}}", foundRecord.getTreeJson());
    }
}
