package com.Udea.Ciclo3.modelos;

import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name="enterprise")
public class Enterprise {
    //el Id permite generar el identificador para el PK de la base de datos en postgresSQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String document;
    private String phone;
    private String address;

    private Date createdAt;
    private Date updateAt;

    //jpa
    @OneToMany(mappedBy = "enterprise")
    private List<Employee> employees;

    @OneToMany(mappedBy = "enterprise")
    private List<Transaction> transactions;

    public Enterprise() {
    }

    public Enterprise(String name, String document, String phone, String address, Date createdAt, Date updateAt, List<Employee> employees, List<Transaction> transactions) {
        this.name = name;
        this.document = document;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.employees = employees;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
