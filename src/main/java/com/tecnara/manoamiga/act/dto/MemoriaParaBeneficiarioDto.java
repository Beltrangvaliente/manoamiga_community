package com.tecnara.manoamiga.act.dto;

import java.util.Date;


public class MemoriaParaBeneficiarioDto {
    private String monitor;
    private String comentarios;
    private Date fechaComentario;

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
    
    
}
