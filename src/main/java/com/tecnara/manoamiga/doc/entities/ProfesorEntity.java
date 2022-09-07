package com.tecnara.manoamiga.doc.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity(name = "doc_profesor_entity")
@Table(name = "doc_profesor")
public class ProfesorEntity implements Serializable  {
     
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idUsuario;

    public String nombreProf;
    public String apellido1Prof;
    public String apellido2Prof;  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreProf() {
        return nombreProf;
    }

    public void setNombreProf(String nombreProf) {
        this.nombreProf = nombreProf;
    }

    public String getApellido1Prof() {
        return apellido1Prof;
    }

    public void setApellido1Prof(String apellido1Prof) {
        this.apellido1Prof = apellido1Prof;
    }

    public String getApellido2Prof() {
        return apellido2Prof;
    }

    public void setApellido2Prof(String apellido2Prof) {
        this.apellido2Prof = apellido2Prof;
    }
    
    
}
