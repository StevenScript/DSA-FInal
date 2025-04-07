package com.example.DSA_Final.repository;

import com.example.DSA_Final.model.BalancedTreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalancedTreeRecordRepository extends JpaRepository<BalancedTreeRecord, Long> {
}
