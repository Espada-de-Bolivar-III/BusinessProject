package com.BussinessProject.udea.controller;

import com.BussinessProject.udea.modelos.Enterprise;
import com.BussinessProject.udea.services.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class Controller {

    //Autowired
    @Autowired
    EnterpriseService empresaService;


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
}
