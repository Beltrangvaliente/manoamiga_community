package com.tecnara.manoamiga.act.dto;

public class MonitoresDeLaActividadDto {

    public Long id;
    public String nombre;
    public String apellido1;
    public String apellido2;
    public String telefono;
    public String correo;
    public String nombreTutor;
    public String propietarioDeActividad;

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPropietarioDeActividad() {
        return propietarioDeActividad;
    } 

    public void setPropietarioDeActividad(String propietarioDeActividad) {
        this.propietarioDeActividad = propietarioDeActividad;
    }

    
    
}
