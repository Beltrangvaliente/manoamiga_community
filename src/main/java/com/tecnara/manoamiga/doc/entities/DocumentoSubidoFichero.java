package com.tecnara.manoamiga.doc.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "doc_documentos_subidos_fichero")
public class DocumentoSubidoFichero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public Long idDocumentoSubido;
    
    public String fichero;
    public String estadoVerificacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    
    public Date fechaSubido;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    
    public Date fechaVerificacion;
    public Long idUsuarioVerificacion;  //Usuario que ha verificado el fichero

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDocumentoSubido() {
        return idDocumentoSubido;
    }

    public void setIdDocumentoSubido(Long idDocumentoSubido) {
        this.idDocumentoSubido = idDocumentoSubido;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public String getEstadoVerificacion() {
        return estadoVerificacion;
    }

    public void setEstadoVerificacion(String estadoVerificacion) {
        this.estadoVerificacion = estadoVerificacion;
    }

    public Date getFechaSubido() {
        return fechaSubido;
    }

    public void setFechaSubido(Date fechaSubido) {
        this.fechaSubido = fechaSubido;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public Long getIdUsuarioVerificacion() {
        return idUsuarioVerificacion;
    }

    public void setIdUsuarioVerificacion(Long idUsuarioVerificacion) {
        this.idUsuarioVerificacion = idUsuarioVerificacion;
    }
    
}
