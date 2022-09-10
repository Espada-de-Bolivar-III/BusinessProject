package com.Udea.Ciclo3.controller;

import com.Udea.Ciclo3.modelos.Employee;
import com.Udea.Ciclo3.modelos.Enterprise;
import com.Udea.Ciclo3.modelos.Profile;
import com.Udea.Ciclo3.modelos.Transaction;
import com.Udea.Ciclo3.services.EmployeeService;
import com.Udea.Ciclo3.services.EnterpriseService;
import com.Udea.Ciclo3.services.ProfileService;
import com.Udea.Ciclo3.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    //Autowired
    @Autowired
    EnterpriseService empresaService;

    @Autowired
    TransactionService transactionService;
    @Autowired
    ProfileService profileService;
    @Autowired
    EmployeeService employeeService;

    //EMPRESAS
    @GetMapping("/enterprises")
    public List<Enterprise> verEmpresas(){
        return empresaService.getALLEmpresas();

    }
    @PostMapping("/enterprises")
    public Enterprise guardarEmpresa(@RequestBody Enterprise emp){
        return this.empresaService.saveOrUpdateEmpresa(emp);
    }

    @GetMapping(path ="/enterprises/{id}")
    public Enterprise empresaPorID(@PathVariable("id") Long id){
        return this.empresaService.getEmpresaById(id);
    }

    @PatchMapping("/enterprises/{id}")
    public Enterprise actualizarEmpresa(@PathVariable("id")Long id,@RequestBody Enterprise empresa){
        Enterprise emp =empresaService.getEmpresaById(id);
        emp.setName(empresa.getName());
        emp.setDocument(empresa.getDocument());
        emp.setPhone(empresa.getPhone());
        emp.setAddress(empresa.getAddress());
        emp.setCreatedAt(empresa.getCreatedAt());
        emp.setUpdateAt(empresa.getUpdateAt());


        return empresaService.saveOrUpdateEmpresa(emp);
    }

    @DeleteMapping(path="enterprises/{id}")//eliminar registro de la bd
    public String DeleteEmpresa(@PathVariable("id")Long id){
        boolean respuesta = this.empresaService.deleteEmpresa(id);
        if (respuesta){ //si respuesta es true?
            return "se elimino la respuesta con id" +id;

        }
        else{
            return "no se pudo eliminar empresa con id"+id;
        }
    }

    //TRANSACTIONS - MOVIMIENTOS

    @GetMapping("/transactions")
    public List<Transaction> verTransactions(){
        return transactionService.getAllTransactions();
    }

    @PostMapping("/transactions")
    public Transaction guardarTransaction(@RequestBody Transaction transaction){
        return this.transactionService.saveOrUpdateTransaction(transaction);
    }

    @GetMapping(path ="/transactions/{id}")
    public Transaction transactionPorID(@PathVariable("id") Long id){
        return this.transactionService.getTransactionById(id);
    }

    @PatchMapping("/transactions/{id}")
    public Transaction actualizarTransaction(@PathVariable("id")Long id,@RequestBody Transaction transaction){
        Transaction mov = transactionService.getTransactionById(id);
        mov.setConcept(transaction.getConcept());
        mov.setAmount(transaction.getAmount());
        mov.setUser(transaction.getUser());
        return transactionService.saveOrUpdateTransaction(mov);
    }

    @DeleteMapping(path="transactions/{id}")
    public String DeleteTransaction(@PathVariable("id")Long id){
        boolean respuesta = this.transactionService.deleteTransaction(id);
        if (respuesta){
            return "se elimino la respuesta con id" +id;
        }
        return "no se pudo eliminar empresa con id"+id;
    }


    @GetMapping("/employees/{id}/transactions")
    public List<Transaction> getTransactionsByEmployee(@PathVariable("id") Long id){
        return transactionService.getByEmployee(id);
    }

    @GetMapping("/enterprises/{id}/transactions")
    public List<Transaction> getTransactionsByEnterprise(@PathVariable("id") Long id){
        return transactionService.getByEnterprise(id);
    }
    //Empleados

    @GetMapping("/profile")
    public List<Profile> verProfile(){
        return profileService.getAllProfile();
    }

    @PostMapping("/profile")
    public Profile guardarProfile(@RequestBody Profile profile){
        return this.profileService.saveOrUpdateProfile(profile);
    }

    @GetMapping(path ="/profile/{id}")
    public Profile profilePorID(@PathVariable("id") Long id){
        return this.profileService.getProfileById(id);
    }

    @PatchMapping("/profile/{id}")
    public Profile actualizarProfile(@PathVariable("id")Long id,@RequestBody Profile profile){
        Profile prf = profileService.getProfileById(id);
        prf.setId(profile.getId());
        prf.setUser(profile.getUser());
        prf.setPhone(profile.getPhone());
        prf.setCreatedAt(profile.getCreatedAt());
        prf.setUpdateAt(profile.getUpdateAt());
        return profileService.saveOrUpdateProfile(prf);
    }

    @DeleteMapping(path="profile/{id}")
    public String DeleteProfile(@PathVariable("id")Long id){
        boolean respuesta = this.profileService.deleteProfile(id);
        if (respuesta){
            return "se elimino la respuesta con id" +id;
        }
        return "no se pudo eliminar empresa con id"+id;
    }

    @GetMapping("/employees/{id}/profile")
    public List<Profile> getProfileByEmployee(@PathVariable("id") Long id){
        return profileService.getByEmployee(id);
    }

    @GetMapping("/enterprises/{id}/profile")
    public ArrayList<Transaction> getProfileByEnterprise(@PathVariable("id") Long id){
        return profileService.getByEnterprise(id);
    }

    //empleados


    @GetMapping("/employee") //ver json de todos los empleados
    public List<Employee> verEmpleados() {
        return employeeService.getAllEmployee();
    }

    @PostMapping("/employee")
    public Employee guardarEmpleado(@RequestBody Employee user){
        return this.employeeService.saveOrUpdateEmployee(user);

    }

    @GetMapping (path= "employee/{id}")//consultar empleado por ID
        public Employee EmpleadoPorId(@PathVariable("id") Long id){
            return this.employeeService.getEmployeeById(id);
        }


    @GetMapping("/enterprises/{id}/employee") //consultar empleados por empresa
    public ArrayList<Employee> EmpleadoPorEmpresa(@PathVariable("id")Long id){
        return this.employeeService.getByEnterprise(id);
    }

    @PatchMapping("/employee/{id}")
    public Employee actualizarEmpleado(@PathVariable("id")Long id, @RequestBody Employee empleado){
        Employee user= employeeService.getEmployeeById(id);
        user.setEmail(empleado.getEmail());
        user.setRol(empleado.getRol());
        user.setCreatedAt(empleado.getCreatedAt());
        user.setUpdateAt(empleado.getUpdateAt());
        return employeeService.saveOrUpdateEmployee(user);
    }

    @DeleteMapping("/employee/{id}")
    public String DeleteEmpleado (@PathVariable ("id")Long id){
        boolean respuesta = employeeService.deleteEmployee(id);//eliminanos usando el servicio de nuestro service
        if(respuesta){//si la respuesta booleana es true, si se elimino
            return "se puedo eliminar correctamente el empleado con id"+id;
        }//si la respuesta booleana es false, no se elimina
        return "No se pudo eliminar correctamente el empleado con id"+id;

    }




}
