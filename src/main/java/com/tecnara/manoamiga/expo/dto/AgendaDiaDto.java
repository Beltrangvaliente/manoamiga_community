/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.dto;

import java.util.Date;
import java.util.List;


public class AgendaDiaDto {

    private Long idImagen;
    private Date fecha;
    private String diaSemana;
    private List<EventoDto> eventos; 

    public List<EventoDto> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDto> eventos) {
        this.eventos = eventos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    
    
    
}
