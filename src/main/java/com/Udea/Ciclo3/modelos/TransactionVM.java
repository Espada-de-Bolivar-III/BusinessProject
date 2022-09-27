package com.Udea.Ciclo3.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TransactionVM {

    private long id;
    private long amount;
    private String concept;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    public TransactionVM(long id,long amount, String concept, Date fecha) {
        this.id = id;
        this.amount = amount;
        this.concept = concept;

        this.fecha = fecha;
    }
    public TransactionVM() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
