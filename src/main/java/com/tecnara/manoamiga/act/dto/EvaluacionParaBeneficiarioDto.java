package com.tecnara.manoamiga.act.dto;

import java.util.Date;

public class EvaluacionParaBeneficiarioDto {
    public Long id;
    public String evaluador;
    public Long puntuacion;
    public Date fechaDeEvaluacion;

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

    public Long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Long puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFechaDeEvaluacion() {
        return fechaDeEvaluacion;
    }

    public void setFechaDeEvaluacion(Date fechaDeEvaluacion) {
        this.fechaDeEvaluacion = fechaDeEvaluacion;
    }


}
