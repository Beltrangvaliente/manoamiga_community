package com.tecnara.manoamiga.act.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
 
@Entity 
@Table(name = "act_actividades")

public class ActividadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoActividad;
    private String nombreActividad;
    private String responsableActividad;
    private Integer plazaActividad;
    private String observacion;
    private String lugarActividad;
    private String direccion;
    private String mail;
    private String localidad;
    private String nacionalidad;
    private String publicar; //Si -> Estas actividades las verán los padres/madres/tutores.

    public String getPublicar() {
        return publicar;
    }

    public void setPublicar(String publicar) {
        this.publicar = publicar;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    public Date fechaActualizacion;

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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLugarActividad() {
        return lugarActividad;
    }

    public void setLugarActividad(String lugarActividad) {
        this.lugarActividad = lugarActividad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
}
