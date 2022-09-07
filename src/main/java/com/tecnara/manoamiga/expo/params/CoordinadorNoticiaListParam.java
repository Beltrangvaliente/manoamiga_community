/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.params;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class CoordinadorNoticiaListParam {
    private String estado;
    private String filtro;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaDesde;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaHasta;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
    
}
