package com.tecnara.manoamiga.aaa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;   
  
@Entity 
@Table(name = "aaa_usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Column(unique=true)
    @NotBlank()
    public String usuario;
    
    @Column(unique=true)
    public String email;
    
    public String password;
    public String estado;   //Pendiente de verificacion,Pendiente,Aceptado,Rechazado,Bloqueado
    
    @Column(unique=true)
    public String codigoVerificacion;
    
    public String rol;  //Especifica el tipo de rol que tiene este usuario. El rol se indicará con Modulo_Tipo. Ej: Act_Monitor, Act_Admin, Expo_Coordinador
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}

    
    
