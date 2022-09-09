package com.Udea.Ciclo3.services;


import com.Udea.Ciclo3.modelos.Employee;
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


}
