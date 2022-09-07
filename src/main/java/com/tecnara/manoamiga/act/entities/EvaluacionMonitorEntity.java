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
@Table(name = "act_evaluaciones_monitores")

public class EvaluacionMonitorEntity {

   @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
   public Long id;
   
   public Long idBeneficiario;
   public Long idMonitor;
   public Long idSolicitudEvaluacionMonitor;
   public Long idActividad;

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }
   
   public Integer valoracion;
   public String comentario;
   
   @DateTimeFormat (pattern = "yyyy-MM-dd")
   @Temporal(TemporalType.DATE)
   public Date fechaComentario;

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

    public Long getIdSolicitudEvaluacionMonitor() {
        return idSolicitudEvaluacionMonitor;
    }

    public void setIdSolicitudEvaluacionMonitor(Long idSolicitudEvaluacionMonitor) {
        this.idSolicitudEvaluacionMonitor = idSolicitudEvaluacionMonitor;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
    
   
    
  
   
}
