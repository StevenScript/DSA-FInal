package com.example.DSA_Final.service;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.BalancedTreeRecordRepository;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

/**
 * Unit tests for TreeService.
 * Tests check both unbalanced BST creation and the balancing of an existing tree.
 */
public class TreeServiceTest {

    @Test
    void testCreateAndStoreTree() {
        // Arrange: Create a mock repository for unbalanced trees and a dummy for balanced.
        TreeRecordRepository mockRepo = Mockito.mock(TreeRecordRepository.class);
        BalancedTreeRecordRepository dummyBalancedRepo = Mockito.mock(BalancedTreeRecordRepository.class);

        // When saving, return the passed TreeRecord.
        Mockito.when(mockRepo.save(Mockito.any(TreeRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TreeService service = new TreeService(mockRepo, dummyBalancedRepo);

        // Act: Create a new BST from input numbers.
        String inputNumbers = "5,2,7";
        TreeRecord result = service.createAndStoreTree(inputNumbers);

        // Assert: Confirm the record contains the expected input and JSON.
        Assertions.assertEquals(inputNumbers, result.getInputNumbers());
        Assertions.assertNotNull(result.getTreeJson());
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":5"));
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":2"));
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":7"));

        // Verify the save method was called.
        Mockito.verify(mockRepo).save(Mockito.any(TreeRecord.class));
    }

    @Test
    void testBalanceExistingTree() {
        // Arrange: Create mocks for both repositories.
        TreeRecordRepository mockTreeRepo = Mockito.mock(TreeRecordRepository.class);
        BalancedTreeRecordRepository mockBalancedRepo = Mockito.mock(BalancedTreeRecordRepository.class);

        TreeService service = new TreeService(mockTreeRepo, mockBalancedRepo);

        // Stub the save method for balanced records to return the provided record.
        Mockito.when(mockBalancedRepo.save(Mockito.any(BalancedTreeRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Create a sample unbalanced TreeRecord.
        TreeRecord existing = new TreeRecord();
        existing.setId(10L);
        existing.setInputNumbers("1,2,3,4,5");
        existing.setTreeJson("some unbalanced chain in JSON"); // placeholder

        // Stub findById to return our sample record.
        Mockito.when(mockTreeRepo.findById(10L)).thenReturn(Optional.of(existing));

        // Act: Balance the BST for treeId 10.
        BalancedTreeRecord balanced = service.balanceExistingTree(10L);

        // Assert: Verify the balanced record refers to the original tree and includes expected JSON.
        Assertions.assertEquals(10L, balanced.getOriginalTreeId());
        Assertions.assertEquals("1,2,3,4,5", balanced.getInputNumbers());
        Assertions.assertTrue(balanced.getBalancedTreeJson().contains("\"value\":3"),
                "Expected the balanced JSON to include \"value\":3");

        // Verify that the balanced record was saved.
        Mockito.verify(mockBalancedRepo).save(Mockito.any(BalancedTreeRecord.class));
    }
}
