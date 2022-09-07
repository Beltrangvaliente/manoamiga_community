
package com.tecnara.manoamiga.act.dto;


public class FaltaAsistenciaParaBeneficiarioDto {
    private Long id;
    private String evaluador;
    private String actividad;
    private String observacion;
    private String fechaFaltaAsistencia;
    private String persona;
    private String motivo;
    private String idProfesor;

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPersona() {
        return persona;
    }

    
    private String nombreBeneficiario;

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }
    
    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(String evaluador) {
        this.evaluador = evaluador;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaFaltaAsistencia() {
        return fechaFaltaAsistencia;
    }

    public void setFechaFaltaAsistencia(String fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }
   
    
}
