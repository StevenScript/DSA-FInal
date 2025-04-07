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
 * Test the TreeService with a mock repository,
 * verifying the service builds a BST, converts it to JSON,
 * and saves a TreeRecord.
 */
public class TreeServiceTest {

    @Test
    void testCreateAndStoreTree() {
        // 1) Mock the repositories
        TreeRecordRepository mockRepo = Mockito.mock(TreeRecordRepository.class);
        // Provide a dummy balanced repository
        BalancedTreeRecordRepository dummyBalancedRepo = Mockito.mock(BalancedTreeRecordRepository.class);

        // 2) Instruct mockRepo to return the entity passed to .save(...)
        Mockito.when(mockRepo.save(Mockito.any(TreeRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // 3) Create the service with both repositories
        TreeService service = new TreeService(mockRepo, dummyBalancedRepo);

        // 4) Execute the method
        String inputNumbers = "5,2,7";
        TreeRecord result = service.createAndStoreTree(inputNumbers);

        // 5) Verify the returned content
        Assertions.assertEquals(inputNumbers, result.getInputNumbers());
        Assertions.assertNotNull(result.getTreeJson());
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":5"));
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":2"));
        Assertions.assertTrue(result.getTreeJson().contains("\"value\":7"));

        // 6) Check that repository.save() was called
        Mockito.verify(mockRepo).save(Mockito.any(TreeRecord.class));
    }

    @Test
    void testBalanceExistingTree() {
        // 1) Mock BOTH repositories
        TreeRecordRepository mockTreeRepo = Mockito.mock(TreeRecordRepository.class);
        BalancedTreeRecordRepository mockBalancedRepo = Mockito.mock(BalancedTreeRecordRepository.class);

        // 2) Create the service with both repositories
        TreeService service = new TreeService(mockTreeRepo, mockBalancedRepo);

        // 3) Stub the .save() on the balanced repo so it returns the argument passed in
        Mockito.when(mockBalancedRepo.save(Mockito.any(BalancedTreeRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // 4) Mock an existing TreeRecord
        TreeRecord existing = new TreeRecord();
        existing.setId(10L);
        existing.setInputNumbers("1,2,3,4,5");
        existing.setTreeJson("some unbalanced chain in JSON"); // placeholder

        // 5) Stub the findById() on mockTreeRepo
        Mockito.when(mockTreeRepo.findById(10L)).thenReturn(Optional.of(existing));

        // 6) Call the new method to balance the existing tree
        BalancedTreeRecord balanced = service.balanceExistingTree(10L);

        // 7) Validate the results
        // The BalancedTreeRecord should reference the original tree's ID
        Assertions.assertEquals(10L, balanced.getOriginalTreeId());
        // It should also have the same input numbers
        Assertions.assertEquals("1,2,3,4,5", balanced.getInputNumbers());
        // Expect that, after balancing, the JSON includes a central value (e.g., "3")
        Assertions.assertTrue(balanced.getBalancedTreeJson().contains("\"value\":3"),
                "Expected the balanced JSON to include \"value\":3");

        // 8) Verify that balancedRepo.save() was called
        Mockito.verify(mockBalancedRepo).save(Mockito.any(BalancedTreeRecord.class));
    }
}
