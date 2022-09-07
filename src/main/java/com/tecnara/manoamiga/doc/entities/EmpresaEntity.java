package com.tecnara.manoamiga.doc.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity //importado a Entity(java.persistence)
@Table(name = "doc_empresas") //cambiado de "tareas" a "empresas"

public class EmpresaEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public String nombre; 
    public String direccion;
    
    public String nif; // cambiado NIF de mayuscula a minuscula, cambiado de Integer a String, para poder poner la letra
    public String codigoPostal;  // cambiado de Integer a String
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.DATE)
    public Date fechaActualizacion; // cambiado de DateTimeFormat a Date, e importado a Date(javautil)
    
    public String webEmpresa; //puesta la primera letra en minuscula

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getWebEmpresa() {
        return webEmpresa;
    }

    public void setWebEmpresa(String webEmpresa) {
        this.webEmpresa = webEmpresa;
    }

}
