package com.tecnara.manoamiga.emp.entities;

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

@Entity(name = "emp_documentos_participante")
@Table(name = "emp_documentos_participantes")
public class DocumentoParticipanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idFichero;

    private String nombreDocumento;
    
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fechaSubido;
    
    private Long idParticipante;
    
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

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

}
