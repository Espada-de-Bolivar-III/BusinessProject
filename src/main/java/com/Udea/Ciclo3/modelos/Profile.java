package com.Udea.Ciclo3.modelos;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class Profile {
    //el Id es el que permite generar el identificador para el PK de la base de datos en (postgresSQL)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //atributos
    private String Id;
    private String image;
    private String phone;
    @OneToOne
    private Employee user;
    private Date createdAt;
    private Date updateAt;
    //constructor vacio
    public Profile() {
    }
    //constructor
    public Profile( String Id, String image, String phone, Employee user, Date createdAt, Date updateAt ) {
        this.Id = Id;
        this.image = image;
        this.phone = phone;
        this.user = user;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
     //getters and setters

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
