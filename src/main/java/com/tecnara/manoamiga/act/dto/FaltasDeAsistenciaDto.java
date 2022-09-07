
package com.tecnara.manoamiga.act.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class FaltasDeAsistenciaDto {
    public Long id;
    public String beneficiario;
    public String monitor;
    public String profesor;
    public String motivo;
    public String observacion;
    public String persona; 
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    public Date fechaFaltaAsistencia;

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Date getFechaFaltaAsistencia() {
        return fechaFaltaAsistencia;
    }

    public void setFechaFaltaAsistencia(Date fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }
   
            
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fechaFaltaAsistencia;
    }

    public void setFecha(Date fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }
    
}
