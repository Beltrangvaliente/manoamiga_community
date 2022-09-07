
package com.tecnara.manoamiga.act.dto;
  
import java.util.Date;


public class ComentarioParaBeneficiarioDto {
    public Long id;
    public String persona;
    public String texto;
    public Date fechaComentario;
    public String horaComentario;
    private String motivo;
    private Long idProfesor;
    private Long idTutor;
    private String leidoPorTutor;

    
    

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Long getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Long idProfesor) {
        this.idProfesor = idProfesor;
    }


    

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public String getHoraComentario() {
        return horaComentario;
    }

    public void setHoraComentario(String horaComentario) {
        this.horaComentario = horaComentario;
    }

    public String getLeidoPorTutor() {
        return leidoPorTutor;
    }

    public void setLeidoPorTutor(String leidoPorTutor) {
        this.leidoPorTutor = leidoPorTutor;
    }

    
}
