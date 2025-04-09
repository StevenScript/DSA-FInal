package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for TreeRecord entities.
 * Enables CRUD operations for unbalanced BST records.
 */
@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {
}
