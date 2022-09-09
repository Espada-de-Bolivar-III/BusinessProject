package com.Udea.Ciclo3.services;

import com.Udea.Ciclo3.modelos.Enterprise;
import com.Udea.Ciclo3.repository.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseRepository empresaRepository;//creamos un objeto tipo empresa para poder usar los metodos que dicha clase hereda
    //metodo que retornará la lista de empresas usando metodos heredados del jpaRepository
    public List<Enterprise> getALLEmpresas(){
        List<Enterprise> empresaList = new ArrayList<>();
        empresaRepository.findAll().forEach(empresa -> empresaList.add(empresa));//recorremos el iterable que regresa el metodo finAll del JPA y lo guardamos en la lista
        return empresaList;
    }
    //METODO QUE ME TRAE UN OBJETO DE TIPO EMPRESA CUANDO CUENTA CON EL ID DE LA MISMA
    //se usa metodo optional para retornar la empresa
    public Enterprise getEmpresaById(Long id){
        Optional<Enterprise> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()){
            return empresa.get();
        }
        return new Enterprise();

    }

    //metodo para guardar o actualizar objetos de tipo empresa

    public Enterprise saveOrUpdateEmpresa(Enterprise empresa) {
        Enterprise emp = empresaRepository.save(empresa);
        return emp;
    }

    //metodo para eliminar empresas registradas
    // servicio para eliminar empresas modificado de la clase :D
    public boolean deleteEmpresa(Long id){
        empresaRepository.deleteById(id); //eliminar
        if (empresaRepository.findById(id)!=null){//verificacion del servicio de eliminacion
            return true;
        }
        return false;
    }
}
