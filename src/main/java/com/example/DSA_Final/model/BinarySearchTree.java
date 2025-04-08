package com.example.DSA_Final.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * Model representing a Binary Search Tree (BST).
 * Provides methods to insert values, balance the tree,
 * and serialize the tree structure to JSON.
 */
@NoArgsConstructor
public class BinarySearchTree {

    private Node root;

    /**
     * Represents a single node within the BST.
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
     * Inserts a value into the BST, maintaining the BST property.
     * Duplicates are ignored to preserve tree uniqueness.
     *
     * @param value integer to insert into the tree
     */
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node current, int value) {
        if (current == null) {
            return new Node(value); // New leaf node
        }
        if (value < current.getValue()) {
            current.setLeft(insertRec(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRec(current.getRight(), value));
        }
        return current; // return unchanged node if duplicate
    }

    /**
     * Balances the current BST by:
     *   1. Collect node values using in-order traversal (producing a sorted list).
     *   2. Rebuild a balanced BST from the sorted list.
     */
    public void balance() {
        List<Integer> values = new ArrayList<>();
        inOrder(root, values);
        root = buildBalanced(values, 0, values.size() - 1);
    }

    // Recursively collects node values in ascending order.
    private void inOrder(Node node, List<Integer> list) {
        if (node == null) return;
        inOrder(node.getLeft(), list);
        list.add(node.getValue());
        inOrder(node.getRight(), list);
    }

    // Rebuilds a balanced BST from sorted values
    private Node buildBalanced(List<Integer> values, int start, int end) {
        if (start > end) {
            return null; // No nodes left for this subtree.
        }
        int mid = (start + end) / 2;
        Node newNode = new Node(values.get(mid));
        newNode.setLeft(buildBalanced(values, start, mid - 1));
        newNode.setRight(buildBalanced(values, mid + 1, end));
        return newNode;
    }

    /**
     * Serializes the BST structure into a JSON string.
     * Used for frontend visualization or API responses.
     *
     * @return JSON representation of the BST.
     */
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // We'll serialize the root node
            return mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            return "{}"; // Return empty JSON on serialization failure.
        }
    }
}
