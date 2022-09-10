package com.Udea.Ciclo3.services;


import com.Udea.Ciclo3.modelos.Employee;
import com.Udea.Ciclo3.modelos.Enterprise;
import com.Udea.Ciclo3.modelos.Transaction;
import com.Udea.Ciclo3.repository.EmployeeRepository;
import com.Udea.Ciclo3.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee(){
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
        return employeeList;

    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public Employee saveOrUpdateEmployee(Employee employee){
        Employee user = employeeRepository.save(employee);
        return user;
    }

    public Employee saveOrUpdateEmpresa(Employee employee) {
        Employee user = employeeRepository.save(employee);
        return user;
    }

    //metodo para eliminar empresas registradas
    // servicio para eliminar empresas modificado de la clase :D
    public boolean deleteEmployee(Long id){
        employeeRepository.deleteById(id);
        if(this.employeeRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    //Metodo para buscar empleados por empresa
    //Metodo para consultar empresas por id
    public ArrayList<Employee> getByEnterprise(Long id) {
        return employeeRepository.findByEnterpriseId(id);
    }
}
