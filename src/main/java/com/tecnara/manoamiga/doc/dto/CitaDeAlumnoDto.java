package com.tecnara.manoamiga.doc.dto;


public class CitaDeAlumnoDto {

    public Long id;

    //Cita Entity
    public String estado;
    public String fechaDeCita;
    public String horaDeCita;

    //Centro De Atencion Entity
    public String nombreCentroDeAtencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaDeCita() {
        return fechaDeCita;
    }

    public void setFechaDeCita(String fechaDeCita) {
        this.fechaDeCita = fechaDeCita;
    }

    public String getHoraDeCita() {
        return horaDeCita;
    }

    public void setHoraDeCita(String horaDeCita) {
        this.horaDeCita = horaDeCita;
    }

    public String getNombreCentroDeAtencion() {
        return nombreCentroDeAtencion;
    }

    public void setNombreCentroDeAtencion(String nombreCentroDeAtencion) {
        this.nombreCentroDeAtencion = nombreCentroDeAtencion;
    }
    
}
