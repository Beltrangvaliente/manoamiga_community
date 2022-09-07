package com.tecnara.manoamiga.act.entities;

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

@Entity
@Table(name = "act_evaluaciones_beneficiarios")

public class EvaluacionBeneficiarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idBeneficiario;
    public Long idProfesor;
    public Long idMonitor;
    public Long idActividad;

    public Integer puntuacion;
    public String comentarios;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fechaDeEvaluacion;
    
    

    private String leidoPorTutor; //Si,No/null

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaDeEvaluacion() {
        return fechaDeEvaluacion;
    }

    public void setFechaDeEvaluacion(Date fechaDeEvaluacion) {
        this.fechaDeEvaluacion = fechaDeEvaluacion;
    }

    public String getLeidoPorTutor() {
        return leidoPorTutor;
    }

    public void setLeidoPorTutor(String leidoPorTutor) {
        this.leidoPorTutor = leidoPorTutor;
    }
    
    
}
    
