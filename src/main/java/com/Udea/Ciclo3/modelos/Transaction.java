package com.Udea.Ciclo3.modelos;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private float amount;
    private String concept;
    private Date updateAt;
    private Date createdAt;

    private Employee user;
    private Enterprise enterprise;


}
