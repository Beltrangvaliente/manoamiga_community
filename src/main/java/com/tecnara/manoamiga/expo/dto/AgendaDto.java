package com.tecnara.manoamiga.expo.dto;

import java.util.List;

public class AgendaDto {

    private List<EventoDto> hoy;
    private List<AgendaDiaDto> semana;

    public List<EventoDto> getHoy() {
        return hoy;
    }

    public void setHoy(List<EventoDto> hoy) {
        this.hoy = hoy;
    }

    public List<AgendaDiaDto> getSemana() {
        return semana;
    }

    public void setSemana(List<AgendaDiaDto> semana) {
        this.semana = semana;
    }
    
}
