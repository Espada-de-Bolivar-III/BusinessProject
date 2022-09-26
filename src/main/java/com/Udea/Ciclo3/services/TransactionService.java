package com.Udea.Ciclo3.services;

import com.Udea.Ciclo3.modelos.Transaction;
import com.Udea.Ciclo3.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    //1. metodo que muestra todas las transacciones
    public List<Transaction> getAllTransactions(){
        List<Transaction> movimientosList= new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> movimientosList.add(transaction));
        return movimientosList;
    }
    //2. metodo que muestra todas las transacciones por id
    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).get();
    }


    //3. guardar o actualizar elementos
    public boolean saveOrUpdateTransacion(Transaction transaction) {
        Transaction mov = transactionRepository.save(transaction);
        if (transactionRepository.findById(mov.getId()) != null) {
            return true;
        }
        return false;
    }


    //4. Eliminar movimiento por id

    public boolean deleteTransaction(Long id){//Eliminar movimiento por ir
        transactionRepository.deleteById(id);//Eliminamos usando el metodo que ofrece el repository
        if (this.transactionRepository.findById(id).isPresent()){//si buscamos el movimiento y lo encontramos, no se elimino(FALSE)
            return false;
        }
        return true;
    }

    //5. Obtener teniendo en cuenta el id del empleado
    public ArrayList<Transaction> getByEmployee(Long id){
        return transactionRepository.findByEmployee(id);
    }

    //6.Obtener movimientos teniendo en cuenta el id de la empresa a la que pertenecen los empleados que la registraron

    public ArrayList<Transaction> getByEnterprise(Long id){

        return transactionRepository.findByEnterprise(id);
    }

    //7. servicio para ver la suma de todos los montos
    public Long ObtenerSumaMontos(){
        return transactionRepository.SumarMonto();
    }


    //8. servicio para ver la suma de los montos por empleado
    public Long MontosPorEmpleado(Long id){
        return transactionRepository.MontosPorEmpleado(id);
    }


    //9. servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Long id){
        return transactionRepository.MontosPorEmpresa(id);

    }


    //10. servicio para ver el id de un empleado si tenemos su correo

    //public Integer IdPorCorreo(String email){
      //  return transactionRepository.IdPorCorreo(email);
   // }



}
