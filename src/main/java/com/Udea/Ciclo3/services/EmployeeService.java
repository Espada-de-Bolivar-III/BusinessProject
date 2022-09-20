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
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    //1. Metodo para ver todos los empleados registrados

    public List<Employee> getAllEmployee(){
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
        return employeeList;

    }

    //2. metodo para buscar empleados por ID

    public Optional<Employee>getEmployeebyId(Long id){
        return employeeRepository.findById(id);

    }

    //3. Metodo para buscar empleados por empresa

    public ArrayList<Employee> obtenerPorEmpresa(Long id){
        return employeeRepository.findByEnterpriseId(id);
    }


    //4.Metodo para guardar o actualizar registros en Empleados

    public boolean saveOrUpdateEmpleado(Employee empl){
        Employee emp =employeeRepository.save(empl);
        if (employeeRepository.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }
    //5. Metodo para eliminar un registro de Empleado por ID

    public boolean deleteEmployee(Long id){
        employeeRepository.deleteById(id);
        if(this.employeeRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }


}
