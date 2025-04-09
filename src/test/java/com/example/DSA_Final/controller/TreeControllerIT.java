package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.TreeRecord;
import com.example.DSA_Final.repository.TreeRecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;

/**
 * Integration tests for TreeController using a real application context and in-memory database.
 * Ensures that creating and retrieving unbalanced BST records works end-to-end.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TreeControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TreeRecordRepository repository;

    @Test
    void testCreateAndGetTrees() {
        // Prepare the request to create a new tree.
        String numbers = "5,2,7";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"numbers\":\"" + numbers + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Send a POST request and verify the response.
        ResponseEntity<TreeRecord> postResponse =
                restTemplate.postForEntity("/api/trees", request, TreeRecord.class);
        Assertions.assertEquals(HttpStatus.OK, postResponse.getStatusCode(), "POST should return 200 OK");
        TreeRecord created = postResponse.getBody();
        Assertions.assertNotNull(created, "POST response body should not be null");
        Assertions.assertEquals(numbers, created.getInputNumbers(), "TreeRecord should match input numbers");
        Assertions.assertTrue(created.getTreeJson().contains("\"value\":5"));

        // Send a GET request to verify the record was saved
        ResponseEntity<TreeRecord[]> getResponse =
                restTemplate.getForEntity("/api/trees", TreeRecord[].class);
        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        TreeRecord[] allRecords = getResponse.getBody();
        Assertions.assertNotNull(allRecords, "GET /api/trees should return an array of TreeRecord");
        Assertions.assertTrue(allRecords.length >= 1, "Should have at least one record stored");

        // Further verify by checking repository count.
        long countInRepo = repository.count();
        Assertions.assertTrue(countInRepo >= 1, "Repository should contain at least one record");
    }
}
