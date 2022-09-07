/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.dto;

import java.util.Date;
import javax.persistence.Column;

public class NoticiaDto {

    private Long id;
    private String nombreTrabajador;
    private String titulo;
    private Date fechaInicio;
    private Date fechaFin;
    private String hora;
    private String fija;
    private String pantalla;
    private String area;
    private String color;
    private String espacio;
    private String ubicacion;
    public String estado;
    
    private String contenido1;
    private String contenido2;
    private String contenido3;

    private Long idImagen1;
    private Long idImagen2;
    private Long idImagen3;
    
    private String imagenFit1;
    private String imagenFit2;
    private String imagenFit3;

    private Long idPlantilla;
    
    private PlantillaDto plantilla;

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFija() {
        return fija;
    }

    public void setFija(String fija) {
        this.fija = fija;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getContenido1() {
        return contenido1;
    }

    public void setContenido1(String contenido1) {
        this.contenido1 = contenido1;
    }

    public String getContenido2() {
        return contenido2;
    }

    public void setContenido2(String contenido2) {
        this.contenido2 = contenido2;
    }

    public String getContenido3() {
        return contenido3;
    }

    public void setContenido3(String contenido3) {
        this.contenido3 = contenido3;
    }

    public Long getIdImagen1() {
        return idImagen1;
    }

    public void setIdImagen1(Long idImagen1) {
        this.idImagen1 = idImagen1;
    }

    public Long getIdImagen2() {
        return idImagen2;
    }

    public void setIdImagen2(Long idImagen2) {
        this.idImagen2 = idImagen2;
    }

    public Long getIdImagen3() {
        return idImagen3;
    }

    public void setIdImagen3(Long idImagen3) {
        this.idImagen3 = idImagen3;
    }

    public PlantillaDto getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(PlantillaDto plantilla) {
        this.plantilla = plantilla;
    }

    public Long getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Long idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getImagenFit1() {
        return imagenFit1;
    }

    public void setImagenFit1(String imagenFit1) {
        this.imagenFit1 = imagenFit1;
    }

    public String getImagenFit2() {
        return imagenFit2;
    }

    public void setImagenFit2(String imagenFit2) {
        this.imagenFit2 = imagenFit2;
    }

    public String getImagenFit3() {
        return imagenFit3;
    }

    public void setImagenFit3(String imagenFit3) {
        this.imagenFit3 = imagenFit3;
    }


    
    
    
}
