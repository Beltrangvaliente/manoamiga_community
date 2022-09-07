
package com.tecnara.manoamiga.emp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emp_formaciones_participantes")
public class FormacionParticipanteEntity {
  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idParticipante;
    private Long idFormacion;
    private String estado;//Candidato,En espera,Aceptado,Rechazado
    
    private String evaluacion; // Apto, No Apto, Inacabado

    private Long idFichero;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Long getIdFormacion() {
        return idFormacion;
    }

    public void setIdFormacion(Long idFormacion) {
        this.idFormacion = idFormacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Long getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(Long idFichero) {
        this.idFichero = idFichero;
    }

   
    
    
}
