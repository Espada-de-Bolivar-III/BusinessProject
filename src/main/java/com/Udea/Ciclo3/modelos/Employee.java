package com.Udea.Ciclo3.modelos;

import javax.persistence.*;

@Entity
@Table (name ="employee")
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private Enum_RoleName rol;
    private Transaction transactions;
    private String updateAt;
    private String createdAt;
    private Enterprise enterprise;

    public Employee() {
    }

    public Employee(Long id, String email, Enum_RoleName rol, Transaction transactions, String updateAt, String createdAt, Enterprise enterprise) {
        this.id = id;
        this.email = email;
        this.rol = rol;
        this.transactions = transactions;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
        this.enterprise = enterprise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Enum_RoleName getRol() {
        return rol;
    }

    public void setRol(Enum_RoleName rol) {
        this.rol = rol;
    }

    public Transaction getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transactions) {
        this.transactions = transactions;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
