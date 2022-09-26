package com.Udea.Ciclo3.repository;

import com.Udea.Ciclo3.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //metodo para filtrar movimientos por empleado
    @Query(value = "SELECT * FROM transaction WHERE employee_id=?1", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEmployee(Long id);
    //metodo par filtrar movimientos por empresa

    @Query(value = "SELECT * FROM transaction WHERE employee_id in (select id from employee where enterprise_id=?1)", nativeQuery = true)
    public abstract ArrayList<Transaction> findByEnterprise(Long id);

    //Metodo para ver la suma de todos los movimientos
    @Query (value="select sum(amount) from transaction", nativeQuery = true)
    public abstract Long SumarMonto();//

    //metodo para ver la suma de los montos por empleado
    @Query(value="Select sum(amount) from transaction where employee_id=?1",nativeQuery = true)
    public abstract Long MontosPorEmpleado(Long id); //id del empleado

    //Metodo para ver la suma de los movimientos por empresa

    @Query(value="select sum(amount) from transaction where employee_id in (select id from employee where enterprise_id=?1)",nativeQuery = true)
    public abstract Long MontosPorEmpresa(Long id);//id de la empresa

    //Metodo que me trae el id de un usuario cuando tengo su correo
    //@Query(value="select id from employee where email=?1",nativeQuery = true)
    //public abstract Integer IdPorCorreo(String email);

}

