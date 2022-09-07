
package com.tecnara.manoamiga.act.dto;

import java.util.Date;


public class FaltaDto {
  private Long id;
  private String nombreTutor;
  private String nombreBeneficiario;
  private String nombreMonitor;
  private String motivo; 
  private String observacion; 
  private String persona;
  private Long idProfesor; 
  private String leidoPorTutor;
    private Long idMonitor;

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }
  public Date fechaFaltaAsistencia;

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaDeFalta() {
        return fechaDeFalta;
    }

    public void setFechaDeFalta(Date fechaDeFalta) {
        this.fechaDeFalta = fechaDeFalta;
    }
   private Date  fechaDeFalta; 

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Date getFechaFaltaAsistencia() {
        return fechaFaltaAsistencia;
    }

    public void setFechaFaltaAsistencia(Date fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }

    public void setNombreMonitor(String nombreMonitor) {
        this.nombreMonitor = nombreMonitor;
    }

    public String getLeidoPorTutor() {
        return leidoPorTutor;
    }

    public void setLeidoPorTutor(String leidoPorTutor) {
        this.leidoPorTutor = leidoPorTutor;
    }

   
   
   
}
 
