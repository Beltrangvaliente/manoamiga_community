package com.tecnara.manoamiga.doc.dto;


public class CitaDto {

    public Long id;
    public String fechaDeCita;
    public String horaDeCita;
    public String nombreCentro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }
    

}
