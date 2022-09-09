package com.Udea.Ciclo3.repository;


import com.Udea.Ciclo3.modelos.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository//Anotación que le dice a Spring que esta clase es un repositorio

public interface EnterpriseRepository extends JpaRepository <Enterprise, Long> {
}
