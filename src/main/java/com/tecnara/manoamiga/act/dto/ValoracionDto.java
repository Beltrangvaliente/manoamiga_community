/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.dto;

import java.util.Date;

/**
 *
 * @author tecnara
 */
public class ValoracionDto {
    public String nombre;
    public Integer puntuacion;
    public Date fechaDeEvaluacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFechaDeEvaluacion() {
        return fechaDeEvaluacion;
    }

    public void setFechaDeEvaluacion(Date fechaDeEvaluacion) {
        this.fechaDeEvaluacion = fechaDeEvaluacion;
    }
    
    
}
