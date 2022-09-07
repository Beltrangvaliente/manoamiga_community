
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
@Table (name = "act_comentarios")

public class ComentarioEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    public String texto;
    
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date  fechaComentario;
    public String horaComentario;
    public Long idBeneficiario;
    public Long idMonitor;
    public Long idTutor;
    public Long idProfesor;
    

    public String leidoPorTutor; //Si o No/null


  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getLeidoPorTutor() {
        return leidoPorTutor;
    }

    public void setLeidoPorTutor(String leidoPorTutor) {
        this.leidoPorTutor = leidoPorTutor;
    }
      
    
    
 }
