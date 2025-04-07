package com.example.DSA_Final.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple Binary Search Tree model class
 * that inserts values and can output the tree as JSON.
 */
@NoArgsConstructor
public class BinarySearchTree {

    private Node root;

    /**
     * A static inner class to represent each node.
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
        return current;
    }

    /**
     * Balances the current BST by:
     * 1) Gathering all values via in-order traversal.
     * 2) Rebuilding a balanced tree from the sorted list.
     */
    public void balance() {
        // 1) In-order traversal to get sorted list of values
        List<Integer> values = new ArrayList<>();
        inOrder(root, values);

        // 2) Build a balanced BST from the sorted list
        root = buildBalanced(values, 0, values.size() - 1);
    }

    private void inOrder(Node node, List<Integer> list) {
        if (node == null) return;
        inOrder(node.getLeft(), list);
        list.add(node.getValue());
        inOrder(node.getRight(), list);
    }

    private Node buildBalanced(List<Integer> values, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node newNode = new Node(values.get(mid));
        newNode.setLeft(buildBalanced(values, start, mid - 1));
        newNode.setRight(buildBalanced(values, mid + 1, end));
        return newNode;
    }

    /**
     * Converts the BST root node to a JSON string.
     * Jackson's ObjectMapper does this.
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
