package com.tecnara.manoamiga.emp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emp_formaciones_previas")
 
public class FormacionPreviaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idParticipante;
    private String nombre;
    private String centro;
    private Integer anyo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public FormacionPreviaEntity(Long id, Long idParticipante, String nombre, String centro, Integer anyo) {
        this.id = id;
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.centro = centro;
        this.anyo = anyo;
    }

    public FormacionPreviaEntity() {
    }

}
