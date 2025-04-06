package com.example.DSA_Final.service;

import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test the TreeService with a mock repository,
 * verifying the service builds a BST, converts it to JSON,
 * and saves a TreeRecord.
 */
public class TreeServiceTest {

    @Test
    void testCreateAndStoreTree() {
        // 1) Mock the repository
        TreeRecordRepository mockRepo = Mockito.mock(TreeRecordRepository.class);

        // 2) Instruct mock to return whatever entity is passed to .save(...)
        Mockito.when(mockRepo.save(Mockito.any(TreeRecord.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // 3) Create the service
        TreeService service = new TreeService(mockRepo);

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
}
