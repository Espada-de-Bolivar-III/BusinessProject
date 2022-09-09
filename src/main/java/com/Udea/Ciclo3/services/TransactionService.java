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

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactionsList = new ArrayList<>();
        transactionRepository.findAll().forEach(transaction -> transactionsList.add(transaction));
        return transactionsList;
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).get();
    }

    public Transaction saveOrUpdateTransaction(Transaction transaction){
        Transaction mov = transactionRepository.save(transaction);
        return mov;
    }

    public boolean deleteTransaction(Long id){
        transactionRepository.deleteById(id);
        if (this.transactionRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<Transaction> getByEmployee(Long id){
        return transactionRepository.findByEmployeeId(id);
    }

    public ArrayList<Transaction> getByEnterprise(Long id){
        return transactionRepository.findByEnterpriseId(id);
    }


}
