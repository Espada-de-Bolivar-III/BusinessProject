package com.Udea.Ciclo3.repository;

import com.Udea.Ciclo3.modelos.Employee;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query(value = "SELECT * FROM employee WHERE enterprise_id = ?1", nativeQuery = true)
    public abstract ArrayList<Employee> findByEnterpriseId(Long enterpriseId);
    public abstract ArrayList<Employee> findByEnterprise(Long id);

    Employee findByEmail(String email);
}

