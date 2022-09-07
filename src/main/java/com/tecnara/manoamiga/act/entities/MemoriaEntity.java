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
@Table(name = "act_memorias")

public class MemoriaEntity {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idBeneficiario;
    public Long idMonitor;
   
    public String comentarios;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
 
   
 }
