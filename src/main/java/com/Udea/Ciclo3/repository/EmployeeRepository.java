package com.Udea.Ciclo3.repository;

import com.Udea.Ciclo3.modelos.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository

public interface EmployeeRepository extends JpaRepository <Employee, Long> {
    @Query(value = "SELECT * FROM employee WHERE enterprise_id = ?1", nativeQuery = true)
    public abstract ArrayList<Employee> findByEnterpriseId(Long enterpriseId);

    @Query(value = "SELECT * FROM employee WHERE transaction_id = ?1", nativeQuery = true)
    public abstract ArrayList<Employee> findByTransactionID(Long transactionId);
}
