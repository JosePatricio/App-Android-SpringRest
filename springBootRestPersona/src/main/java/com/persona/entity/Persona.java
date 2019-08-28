package com.persona.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persona")
public class Persona {

	// TODO: Generate getters and setters...

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
     private Integer id;
        @Column(name = "nombre")
     private String nombre;
        @Column(name = "apellido")
     private String apellido;
        @Column(name = "ruc")
     private int ruc;
        @Column(name = "direccion")
     private String direccion;
        @Column(name = "telefono")
     private String telefono;
        @Column(name = "imagen")
     private String imagen;
        @Column(name = "estado")
     private boolean estado;

    public Persona() {
    }

    public Persona(String nombre, String apellido, int ruc, String direccion, String telefono, String imagen, boolean estado) {
       this.nombre = nombre;
       this.apellido = apellido;
       this.ruc = ruc;
       this.direccion = direccion;
       this.telefono = telefono;
       this.imagen = imagen;
       this.estado = estado;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getRuc() {
        return this.ruc;
    }
    
    public void setRuc(int ruc) {
        this.ruc = ruc;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
    public boolean isEstado() {
        return this.estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }




}


