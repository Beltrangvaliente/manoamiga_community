
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
@Table (name = "emp_experiencias")

public class ExperienciaEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idParticipante;
    private String empresa;
    private Integer  anyoInicio;
    private Integer  anyoFin;
    private String puesto;
    private String verificado;

    public Long getId() {
        return id;
    }

    public String getVerificado() {
        return verificado;
    }

    public void setVerificado(String verificado) {
        this.verificado = verificado;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getAnyoInicio() {
        return anyoInicio;
    }

    public void setAnyoInicio(Integer anyoInicio) {
        this.anyoInicio = anyoInicio;
    }

    public Integer getAnyoFin() {
        return anyoFin;
    }

    public void setAnyoFin(Integer anyoFin) {
        this.anyoFin = anyoFin;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
            
    
    
    
    
}
