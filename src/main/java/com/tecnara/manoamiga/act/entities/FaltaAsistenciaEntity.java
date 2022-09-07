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
@Table(name = "act_faltas_asistencia")

public class FaltaAsistenciaEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idBeneficiario;
    public Long idMonitor;
    public Long idProfesor;
    //public Long idTutor;    //En cuarentena
    public String motivo;
    public String observacion;

    private String leidoPorTutor; //Si,No/null
    public Long idActividad;

    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fechaFaltaAsistencia;
    
    
    
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

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
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

    public Date getFechaFaltaAsistencia() {
        return fechaFaltaAsistencia;
    }

    public void setFechaFaltaAsistencia(Date fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getLeidoPorTutor() {
        return leidoPorTutor;
    }

    public void setLeidoPorTutor(String leidoPorTutor) {
        this.leidoPorTutor = leidoPorTutor;
    }
    

}
    
