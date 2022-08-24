package com.Udea.Ciclo3.modelos;

import javax.persistence.*;

@Entity
@Table (name="Empresa")
public class Enterprise {
    //el Id permite generar el identificador para el PK de la base de datos en postgresSQL
    @Id 
    //generatedValue permite generar la estrategia para actualizar en la DB
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String NIT;

    //constructor vacio
    public Enterprise() {
    }
    
    //constructor
    public Enterprise(String nombre, String direccion, String telefono, String NIT) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.NIT = NIT;
    }
    
    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }
}
