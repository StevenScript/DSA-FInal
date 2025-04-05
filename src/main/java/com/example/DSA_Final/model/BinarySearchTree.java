package com.example.DSA_Final.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * A simple Binary Search Tree model class
 * that inserts values and can output the tree as JSON.
 */
@NoArgsConstructor
public class BinarySearchTree {

    private Node root;

    /**
     * We'll use a static inner class to represent each node.
     * Lombok can help generate getters and setters, but let's keep it minimal.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    static class Node {
        private int value;
        private Node left;
        private Node right;

        Node(int value) {
            this.value = value;
        }
    }

    /**
     * Insert a new value into the BST.
     */
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.getValue()) {
            current.setLeft(insertRec(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRec(current.getRight(), value));
        }
        // If equal, do nothing or handle duplicates as you prefer
        return current;
    }

    /**
     * Converts the BST root node to a JSON string.
     * We rely on Jackson's ObjectMapper for this.
     */
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // We'll serialize the root node
            return mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            return "{}"; // fallback if serialization fails
        }
    }
}
