package com.example.DSA_Final.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

    @Test
    void testInsertAndToJson() {
        // Arrange: We create the (nonexistent) BST
        BinarySearchTree bst = new BinarySearchTree();

        // Act: Insert some numbers
        bst.insert(5);
        bst.insert(2);
        bst.insert(7);

        // Convert to JSON (this method doesn't exist yet, so we'll create it soon)
        String json = bst.toJson();

        // Assert: Check if JSON contains the right structure
        Assertions.assertTrue(json.contains("\"value\":5"));
        Assertions.assertTrue(json.contains("\"value\":2"));
        Assertions.assertTrue(json.contains("\"value\":7"));
    }
}
