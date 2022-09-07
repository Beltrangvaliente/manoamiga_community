package com.tecnara.manoamiga.act.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "act_participaciones_monitores")

public class ParticipacionMonitorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idActividad;
    public Long idMonitor;
    public String propietarioDeActividad; //Si,No

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }

    public String getPropietarioDeActividad() {
        return propietarioDeActividad;
    }

    public void setPropietarioDeActividad(String propietarioDeActividad) {
        this.propietarioDeActividad = propietarioDeActividad;
    }
    
    
}
