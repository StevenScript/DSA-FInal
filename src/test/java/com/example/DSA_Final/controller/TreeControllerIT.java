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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TreeControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TreeRecordRepository repository;

    @Test
    void testCreateAndGetTrees() {
        // 1) POST /api/trees with input numbers
        String numbers = "5,2,7";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send a JSON object with "numbers" property
        String requestBody = "{\"numbers\":\"" + numbers + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // 2) Send POST request
        ResponseEntity<TreeRecord> postResponse =
                restTemplate.postForEntity("/api/trees", request, TreeRecord.class);

        // 3) Verify POST response
        Assertions.assertEquals(HttpStatus.OK, postResponse.getStatusCode(), "POST should return 200 OK");
        TreeRecord created = postResponse.getBody();
        Assertions.assertNotNull(created, "POST response body should not be null");
        Assertions.assertEquals(numbers, created.getInputNumbers(), "TreeRecord should match input numbers");
        Assertions.assertTrue(created.getTreeJson().contains("\"value\":5"));

        // 4) GET /api/trees to verify it's in the DB
        ResponseEntity<TreeRecord[]> getResponse =
                restTemplate.getForEntity("/api/trees", TreeRecord[].class);

        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        TreeRecord[] allRecords = getResponse.getBody();
        Assertions.assertNotNull(allRecords, "GET /api/trees should return an array of TreeRecord");
        Assertions.assertTrue(allRecords.length >= 1, "Should have at least one record stored");

        // 5) Verify with the repository
        long countInRepo = repository.count();
        Assertions.assertTrue(countInRepo >= 1, "Repository should contain at least one record");
    }
}
