
package com.tecnara.manoamiga.aaa.dtos;

import java.util.Date;


public class EmailInfoDto {
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String usuario;
    private String email;
    private String email2;
    private String codigoUsuario;
    
    private String tituloEmail; 
    private String ficheroEmail;
    
    private Date fechaCita;
    private String horaCita;
    private String centroAtencionCita;
    
    private String nombreCurso;
    private Date fechaCurso;
    
    private String nombreDocumento;
    private Date fechaDocumento;

    private String nombreCentro;
    
    private String horario;

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public EmailInfoDto(String nombre, String apellido1, String apellido2, String usuario, String email, String codigoUsuario, String tituloEmail, String ficheroEmail) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.usuario = usuario;
        this.email = email;
        this.codigoUsuario = codigoUsuario;
        this.tituloEmail = tituloEmail;
        this.ficheroEmail = ficheroEmail;
    }

    public EmailInfoDto() {
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

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getTituloEmail() {
        return tituloEmail;
    }

    public void setTituloEmail(String tituloEmail) {
        this.tituloEmail = tituloEmail;
    }

    public String getFicheroEmail() {
        return ficheroEmail;
    }

    public void setFicheroEmail(String ficheroEmail) {
        this.ficheroEmail = ficheroEmail;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Date getFechaCurso() {
        return fechaCurso;
    }

    public void setFechaCurso(Date fechaCurso) {
        this.fechaCurso = fechaCurso;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getCentroAtencionCita() {
        return centroAtencionCita;
    }

    public void setCentroAtencionCita(String centroAtencionCita) {
        this.centroAtencionCita = centroAtencionCita;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    

    
    
}
