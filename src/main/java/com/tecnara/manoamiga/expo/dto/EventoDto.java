 
package com.tecnara.manoamiga.expo.dto;

import java.util.Date;
  
public class EventoDto {
    
    private Long id;
    private String nombreActividad;
    private String tipoActividad;
    private String descripcion;
    private Date fechaActividad;
    private String hora;   
    private String area;
    private String ubicacion;
    private String nombreTrabajador;
    private String color;    
    public String estado; //Pendiente, Aprobado y Rechazado.
    private String colorTexto;
    private Long idImagen;
    private String imagenFit;

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

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorTexto() {
        return colorTexto;
    }

    public void setColorTexto(String colorTexto) {
        this.colorTexto = colorTexto;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getImagenFit() {
        return imagenFit;
    }

    public void setImagenFit(String imagenFit) {
        this.imagenFit = imagenFit;
    }

    
    
}
