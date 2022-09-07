package com.tecnara.manoamiga.doc.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "doc_documentos_subidos_fichero")
public class DocumentoSubidoFicheroEntity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public Long idDocumentoSubido;
    

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.DATE)
    public Date fechaSubido;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Temporal(TemporalType.DATE)
    public Date fechaVerificacion;
    
    public Long idUsuarioVerificacion;  //Usuario que ha verificado el fichero
    
    @Lob
    @Column(columnDefinition="LONGBLOB")    
    private byte[] datos;    
    
    private String nombreFichero;
    
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

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }
    
    
    
}
