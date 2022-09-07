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
public class NumeroFaltasDTO {
    public Integer numeroFaltas;

    public Date getFechaFaltaAsistencia() {
        return fechaFaltaAsistencia;
    }

    public void setFechaFaltaAsistencia(Date fechaFaltaAsistencia) {
        this.fechaFaltaAsistencia = fechaFaltaAsistencia;
    }
    public Date fechaFaltaAsistencia;
    

    public Integer getNumeroFaltas() {
        return numeroFaltas;
    }

    public void setNumeroFaltas(Integer numeroFaltas) {
        this.numeroFaltas = numeroFaltas;
    }
    
}
