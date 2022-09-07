package com.tecnara.manoamiga.expo.entities;

import java.util.Date;
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
@Table(name = "expo_noticias")
public class NoticiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idTrabajador;
    private Long idArea;
    private Long idEspacio;
    private Long idPlantilla;
    private String titulo;
    private String nombreTrabajador;
    public String estado; //aprobado, rechazado, pendiente
    private Integer numeroTextos; //0,1,2,3
    private Integer numeroImagenes; //0,1,2,3

    public Integer getNumeroTextos() {
        return numeroTextos;
    }

    public void setNumeroTextos(Integer numeroTextos) {
        this.numeroTextos = numeroTextos;
    }

    public Integer getNumeroImagenes() {
        return numeroImagenes;
    }

    public void setNumeroImagenes(Integer numeroImagenes) {
        this.numeroImagenes = numeroImagenes;
    }
    
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    @Column(columnDefinition = "text")
    private String contenido1;

    @Column(columnDefinition = "text")
    private String contenido2;

    @Column(columnDefinition = "text")
    private String contenido3;

    private Long idImagen1;
    private String imagenFit1;
    private Long idImagen2;
    private String imagenFit2;
    private Long idImagen3;
    private String imagenFit3;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    public Long getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Long idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    private String hora;

    private String fija; //fija o no
    private String pantalla; //1,2, o Todo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Long idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public Long getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Long idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
