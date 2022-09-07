
package com.tecnara.manoamiga.act.dto;


public class ActivityDto {
    
    private Long id;
    private String tipoActividad;
    private String nombreActividad;
    private String responsableActividad;
    private Integer plazaActividad;
    private String lugarActividad;

    private String horario;

    public String getLugarActividad() {
        return lugarActividad;
    }

    public void setLugarActividad(String lugarActividad) {
        this.lugarActividad = lugarActividad;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getResponsableActividad() {
        return responsableActividad;
    }

    public void setResponsableActividad(String responsableActividad) {
        this.responsableActividad = responsableActividad;
    }

    public Integer getPlazaActividad() {
        return plazaActividad;
    }

    public void setPlazaActividad(Integer plazaActividad) {
        this.plazaActividad = plazaActividad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    
    
}
