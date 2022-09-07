/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.emp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "emp_trabajador_entity")
@Table(name="emp_trabajadores")
public class TrabajadorEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    
    private Long idUsuario;
    
    private String horaSalida;

    private String obligacionRegistroHorario;
    
    private String activarPanelGrupal;
    private String activarPanelIndividual;
    
    private String convenio;
    private Integer horasSegunContrato;
    private String sede;
    
    private String puedeRegistrarPermiso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getObligacionRegistroHorario() {
        return obligacionRegistroHorario;
    }

    public void setObligacionRegistroHorario(String obligacionRegistroHorario) {
        this.obligacionRegistroHorario = obligacionRegistroHorario;
    }

    public String getActivarPanelGrupal() {
        return activarPanelGrupal;
    }

    public void setActivarPanelGrupal(String activarPanelGrupal) {
        this.activarPanelGrupal = activarPanelGrupal;
    }

    public String getActivarPanelIndividual() {
        return activarPanelIndividual;
    }

    public void setActivarPanelIndividual(String activarPanelIndividual) {
        this.activarPanelIndividual = activarPanelIndividual;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public Integer getHorasSegunContrato() {
        return horasSegunContrato;
    }

    public void setHorasSegunContrato(Integer horasSegunContrato) {
        this.horasSegunContrato = horasSegunContrato;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getPuedeRegistrarPermiso() {
        return puedeRegistrarPermiso;
    }

    public void setPuedeRegistrarPermiso(String puedeRegistrarPermiso) {
        this.puedeRegistrarPermiso = puedeRegistrarPermiso;
    }


    
  
    
}
