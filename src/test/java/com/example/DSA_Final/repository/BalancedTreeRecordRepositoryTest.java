package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.BalancedTreeRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BalancedTreeRecordRepositoryTest {

    @Autowired
    private BalancedTreeRecordRepository repo;

    @Test
    void testSaveAndRetrieveBalancedTree() {
        BalancedTreeRecord balancedRecord = new BalancedTreeRecord();
        balancedRecord.setOriginalTreeId(1L);
        balancedRecord.setInputNumbers("1,2,3,4,5");
        balancedRecord.setBalancedTreeJson("{\"value\":3, ...}");

        BalancedTreeRecord saved = repo.save(balancedRecord);
        Assertions.assertNotNull(saved.getId());

        BalancedTreeRecord found = repo.findById(saved.getId()).orElse(null);
        Assertions.assertNotNull(found);
        Assertions.assertEquals("1,2,3,4,5", found.getInputNumbers());
        Assertions.assertTrue(found.getBalancedTreeJson().contains("\"value\":3"));
    }
}
