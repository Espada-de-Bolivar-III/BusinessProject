package com.Udea.Ciclo3.modelos;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private float amount;
    private String concept;
    private String updateAt;
    private String createdAt;

    private String user; // Hay que modificarlo porque debe hacer llamado a una Clase
    private Enterprise enterprise;


}
