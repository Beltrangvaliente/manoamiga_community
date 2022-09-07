
package com.tecnara.manoamiga.emp.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name = "emp_tecnicos")

public class TecnicoEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsuario;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String recibirEmailRegistroParticipante;
    
    private String superAdministrador;

    private String horaSalida;    
    
    private String obligacionRegistroHorario;

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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecibirEmailRegistroParticipante() {
        return recibirEmailRegistroParticipante;
    }

    public void setRecibirEmailRegistroParticipante(String recibirEmailRegistroParticipante) {
        this.recibirEmailRegistroParticipante = recibirEmailRegistroParticipante;
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

    public String getSuperAdministrador() {
        return superAdministrador;
    }

    public void setSuperAdministrador(String superAdministrador) {
        this.superAdministrador = superAdministrador;
    }

    

    

    
}
