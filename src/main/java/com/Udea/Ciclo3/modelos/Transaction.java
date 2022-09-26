package com.Udea.Ciclo3.modelos;

import org.springframework.format.annotation.DateTimeFormat;

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
    private long amount;
    private String concept;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee usuario;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha;
    @ManyToOne
    private Enterprise enterprise;




    //constructor

    public Transaction() {
    }

    public Transaction(long amount, String concept, Employee user, Date fecha) {
        this.amount = amount;
        this.concept = concept;
        this.usuario = user;
        this.fecha = fecha;
    }

//getters and setters


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Employee getUsuario() {
        return usuario;
    }

    public void setUsuario(Employee usuario) {
        this.usuario = usuario;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
