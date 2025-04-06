package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TreeRecord entity.
 */
@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {
}
