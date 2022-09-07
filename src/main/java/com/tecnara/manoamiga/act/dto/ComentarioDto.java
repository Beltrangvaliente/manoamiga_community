
package com.tecnara.manoamiga.act.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


public class ComentarioDto {
    private Long id;
    private String nombreCompletoBeneficiario;
    private String nombreCompletoTutor;
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    public Date  fechaComentario;
    public String horaComentario;

    public String responsableActividad;
    public String nombreActividad;
    public String observacion;
    public String leido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompletoBeneficiario() {
        return nombreCompletoBeneficiario;
    }

    public void setNombreCompletoBeneficiario(String nombreCompletoBeneficiario) {
        this.nombreCompletoBeneficiario = nombreCompletoBeneficiario;
    }

    public String getNombreCompletoTutor() {
        return nombreCompletoTutor;
    }

    public void setNombreCompletoTutor(String nombreCompletoTutor) {
        this.nombreCompletoTutor = nombreCompletoTutor;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public String getHoraComentario() {
        return horaComentario;
    }

    public void setHoraComentario(String horaComentario) {
        this.horaComentario = horaComentario;
    }

    public String getResponsableActividad() {
        return responsableActividad;
    }

    public void setResponsableActividad(String responsableActividad) {
        this.responsableActividad = responsableActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }

    
    
}
