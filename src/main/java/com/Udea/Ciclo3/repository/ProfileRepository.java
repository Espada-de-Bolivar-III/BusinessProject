package com.Udea.Ciclo3.repository;



import com.Udea.Ciclo3.modelos.Profile;
import com.Udea.Ciclo3.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query(value = "SELECT * FROM profile WHERE enterprise_id = ?1", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEnterpriseId(Long enterpriseId);

    @Query(value = "SELECT * FROM profile WHERE employee_id = ?1", nativeQuery = true)
    public abstract ArrayList<Profile> findByEmployeeId(Long userId);
}
