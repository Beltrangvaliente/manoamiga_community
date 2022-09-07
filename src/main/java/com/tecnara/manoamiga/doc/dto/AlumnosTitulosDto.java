package com.tecnara.manoamiga.doc.dto;


public class AlumnosTitulosDto {
    
    public Long id;
    public Long idAlumno;
    public String titulacion;
    public String nivel;
    public String duracion;
   
    public String homologacion; //sí o no
    public String paisExpedicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getHomologacion() {
        return homologacion;
    }

    public void setHomologacion(String homologacion) {
        this.homologacion = homologacion;
    }

    public String getPaisExpedicion() {
        return paisExpedicion;
    }

    public void setPaisExpedicion(String paisExpedicion) {
        this.paisExpedicion = paisExpedicion;
    }
   
}
