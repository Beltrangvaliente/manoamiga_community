package com.tecnara.manoamiga.expo.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "expo_imagenes_decorativas")

public class ImagenDecorativaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idImagen; 
    public Integer prioridad;
    public String titulo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE) 
    public Date fechaImagen;

    public ImagenDecorativaEntity(Long id, Long idImagen, Integer prioridad, String titulo, Date fechaImagen) {
        this.id = id;
        this.idImagen = idImagen;
        this.prioridad = prioridad;
        this.titulo = titulo;
        this.fechaImagen = fechaImagen;
    }

    public ImagenDecorativaEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaImagen() {
        return fechaImagen;
    }

    public void setFechaImagen(Date fechaImagen) {
        this.fechaImagen = fechaImagen;
    }



   
     
    }
    

