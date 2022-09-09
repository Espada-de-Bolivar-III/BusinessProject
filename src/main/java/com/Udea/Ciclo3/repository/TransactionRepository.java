package com.Udea.Ciclo3.repository;

import com.Udea.Ciclo3.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transaction WHERE enterprise_id = ?1", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEnterpriseId(Long enterpriseId);

    @Query(value = "SELECT * FROM transaction WHERE employee_id = ?1", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEmployeeId(Long userId);
}

