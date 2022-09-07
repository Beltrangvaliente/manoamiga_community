package com.tecnara.manoamiga.doc.entities;

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
@Table(name = "doc_titulos")
public class TituloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 

    public Long idAlumno; 

    public String titulacion;
    public String nivel;
    public String duracion;
    public Integer anyoExpedicion;
    public String homologacion; //sí o no
    public String paisExpedicion;
    public String centroExpedicion;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.DATE)
    public Date fechaActualizacion;
    
    public String comentarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Integer getAnyoExpedicion() {
        return anyoExpedicion;
    }

    public void setAnyoExpedicion(Integer anyoExpedicion) {
        this.anyoExpedicion = anyoExpedicion;
    }

    public String getHomologacion() {
        return homologacion;
    }

    public void setHomologacion(String homologacion) {
        this.homologacion = homologacion;
    }

    public String getPaisExpedicion() {
        return paisExpedicion;
    }

    public void setPaisExpedicion(String paisExpedicion) {
        this.paisExpedicion = paisExpedicion;
    }

    public String getCentroExpedicion() {
        return centroExpedicion;
    }

    public void setCentroExpedicion(String centroExpedicion) {
        this.centroExpedicion = centroExpedicion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
