/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "emp_documentos_formaciones")
public class DocumentoFormacionEntity {
 
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idFichero;
    private Long idFormacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(Long idFichero) {
        this.idFichero = idFichero;
    }

    public Long getIdFormacion() {
        return idFormacion;
    }

    public void setIdFormacion(Long idFormacion) {
        this.idFormacion = idFormacion;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public Date getFechaSubido() {
        return fechaSubido;
    }

    public void setFechaSubido(Date fechaSubido) {
        this.fechaSubido = fechaSubido;
    }
    private String nombreDocumento;
    @DateTimeFormat (pattern = "yyyy-MM-dd") 
    @Column(columnDefinition ="date")
    public Date fechaSubido;

    public DocumentoFormacionEntity(Long id, Long idFichero, Long idFormacion, String nombreDocumento, Date fechaSubido) {
        this.id = id;
        this.idFichero = idFichero;
        this.idFormacion = idFormacion;
        this.nombreDocumento = nombreDocumento;
        this.fechaSubido = fechaSubido;
    }

    public DocumentoFormacionEntity() {
    }
    
}
