
package com.tecnara.manoamiga.act.dto;

import java.util.Date;


public class ParticipacionMonitorDto {
    
    private Long id;    //El id de la participación
    private String nombreActividad;
    private Date fechaInicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
