package com.tecnara.manoamiga.doc.dto;
 

public class ProfesorDto {

    public Long id;
    public String nombreProf;
    public String apellido1Prof;
    public String apellido2Prof;
    public String usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
