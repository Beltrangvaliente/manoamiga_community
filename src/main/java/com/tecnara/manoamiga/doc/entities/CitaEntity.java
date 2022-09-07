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
@Table(name = "doc_citas")

public class CitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fechaDeCita;

    public String horaDeCita;

    public Long idCentroDeAtencion;

    public String motivoConsulta;//Formacion, Ayudas Economicas, Orientación
    
    public String explicacionConsulta;

    public Long idCurso;

    public Long idAlumno;

    public String estado; //Vacio, Pendiente, Aceptado, Rechazado

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.DATE)
    public Date fechaActualizacion;
    public String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaDeCita() {
        return fechaDeCita;
    }

    public void setFechaDeCita(Date fechaDeCita) {
        this.fechaDeCita = fechaDeCita;
    }

    public String getHoraDeCita() {
        return horaDeCita;
    }

    public void setHoraDeCita(String horaDeCita) {
        this.horaDeCita = horaDeCita;
    }

    public Long getIdCentroDeAtencion() {
        return idCentroDeAtencion;
    }

    public void setIdCentroDeAtencion(Long idCentroDeAtencion) {
        this.idCentroDeAtencion = idCentroDeAtencion;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getExplicacionConsulta() {
        return explicacionConsulta;
    }

    public void setExplicacionConsulta(String explicacionConsulta) {
        this.explicacionConsulta = explicacionConsulta;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

}

