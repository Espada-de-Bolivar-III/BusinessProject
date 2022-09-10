package com.Udea.Ciclo3.modelos;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
//Entidad para la clase transaccion
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //atributos
    private long Id;
    private float amount;
    private String concept;
    private Date updateAt;
    private Date createdAt;
    @ManyToOne
    private Employee user;
    @ManyToOne
    private Enterprise enterprise;
    //constructor

    public Transaction() {
    }

    public Transaction(long id, float amount, String concept, Date updateAt, Date createdAt, Employee user, Enterprise enterprise) {
        Id = id;
        this.amount = amount;
        this.concept = concept;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
        this.user = user;
        this.enterprise = enterprise;
    }


    //getters and setters


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
