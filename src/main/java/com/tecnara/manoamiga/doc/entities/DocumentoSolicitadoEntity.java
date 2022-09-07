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
@Table(name = "doc_documentos_solicitados")
public class DocumentoSolicitadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idAlumno;
    private Long idMatricula;
    private Long idDocumentoRequerido;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fecha_limite;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaLimiteInicial;

    private String estado; //Pendiente,Aceptado,Rechazado
    private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public Date getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(Date fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Long getIdDocumentoRequerido() {
        return idDocumentoRequerido;
    }

    public void setIdDocumentoRequerido(Long idDocumentoRequerido) {
        this.idDocumentoRequerido = idDocumentoRequerido;
    }


    public Date getFechaLimiteInicial() {
        return fechaLimiteInicial;
    }

    public void setFechaLimiteInicial(Date fechaLimiteInicial) {
        this.fechaLimiteInicial = fechaLimiteInicial;
    }

}
