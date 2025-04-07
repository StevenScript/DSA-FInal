package com.example.DSA_Final.controller;

import com.example.DSA_Final.model.BalancedTreeRecord;
import com.example.DSA_Final.service.TreeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BalancedTreeController.class)
public class BalancedTreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TreeService treeService;

    @Test
    void testBalanceTreeSuccess() throws Exception {
        // Create a sample BalancedTreeRecord
        BalancedTreeRecord record = new BalancedTreeRecord();
        record.setId(1L);
        record.setOriginalTreeId(10L);
        record.setInputNumbers("1,2,3,4,5");
        record.setBalancedTreeJson("{\"value\":3,\"left\":{\"value\":1},\"right\":{\"value\":5}}");

        Mockito.when(treeService.balanceExistingTree(10L)).thenReturn(record);

        mockMvc.perform(post("/api/balance/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balancedTreeJson", containsString("\"value\":3")))
                .andExpect(jsonPath("$.originalTreeId").value(10));
    }

    @Test
    void testBalanceTreeNotFound() throws Exception {
        // If the service throws an exception, return 404
        Mockito.when(treeService.balanceExistingTree(anyLong()))
                .thenThrow(new RuntimeException("TreeRecord not found"));

        mockMvc.perform(post("/api/balance/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllBalancedTrees() throws Exception {
        // Create two sample BalancedTreeRecords
        BalancedTreeRecord record1 = new BalancedTreeRecord();
        record1.setId(1L);
        record1.setOriginalTreeId(10L);
        record1.setInputNumbers("1,2,3,4,5");
        record1.setBalancedTreeJson("{\"value\":3,\"left\":{\"value\":1},\"right\":{\"value\":5}}");

        BalancedTreeRecord record2 = new BalancedTreeRecord();
        record2.setId(2L);
        record2.setOriginalTreeId(20L);
        record2.setInputNumbers("5,6,7,8,9");
        record2.setBalancedTreeJson("{\"value\":7,\"left\":{\"value\":5},\"right\":{\"value\":9}}");

        Iterable<BalancedTreeRecord> balancedList = java.util.List.of(record1, record2);

        Mockito.when(treeService.getAllBalancedTrees()).thenReturn(balancedList);

        mockMvc.perform(get("/api/balanced")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].originalTreeId").value(10))
                .andExpect(jsonPath("$.[1].originalTreeId").value(20));
    }
}
