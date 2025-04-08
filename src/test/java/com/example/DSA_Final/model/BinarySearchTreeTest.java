package com.example.DSA_Final.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the BinarySearchTree class.
 * Verifies insertion, JSON serialization, and that key values appear in the output.
 */
public class BinarySearchTreeTest {

    @Test
    void testInsertAndToJson() {
        // Arrange: Create a new BST instance.
        BinarySearchTree bst = new BinarySearchTree();

        // Act: Insert some numbers
        bst.insert(5);
        bst.insert(2);
        bst.insert(7);
        String json = bst.toJson();

        // Assert: Check if JSON contains all inserted values
        Assertions.assertTrue(json.contains("\"value\":5"));
        Assertions.assertTrue(json.contains("\"value\":2"));
        Assertions.assertTrue(json.contains("\"value\":7"));
    }
}
