/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.dto;
public class BeneficiarioConApellidoUnidoDto {
    private Long id;
    private String nombre;
    private String nombreCompletoTutor;
    private String telefono;
    private String usuario;
    private String apellido; //Juntar apellido1 y apellido2

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

    public String getNombreCompletoTutor() {
        return nombreCompletoTutor;
    }

    public void setNombreCompletoTutor(String nombreCompletoTutor) {
        this.nombreCompletoTutor = nombreCompletoTutor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
}
