package com.tecnara.manoamiga.emp.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity 
@Table(name = "emp_proyectos")
public class ProyectoEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id; 
    public String nombre;
    public String aceptar;

    public String getAceptar() {
        return aceptar;
    }

    public void setAceptar(String aceptar) {
        this.aceptar = aceptar;
    }
    
    @DateTimeFormat (pattern = "yyyy-MM-dd") 
    @Column(columnDefinition ="date")
    public Date fechaInicio;

    @DateTimeFormat (pattern = "yyyy-MM-dd") 
    @Column(columnDefinition ="date")
    public Date fechaFin;

    public Long idEmpresa;
    public String estado;
    @Column(columnDefinition = "text")
    public String explicacionHtml;
    public Long idImagen;
    private String imagenFit1;
    private String moverIzda;

    public String getMoverIzda() {
        return moverIzda;
    }

    public void setMoverIzda(String moverIzda) {
        this.moverIzda = moverIzda;
    }

    public String getImagenFit1() {
        return imagenFit1;
    }

    public void setImagenFit1(String imagenFit1) {
        this.imagenFit1 = imagenFit1;
    }

    public ProyectoEntity(Long id, String nombre, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public ProyectoEntity() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getExplicacionHtml() {
        return explicacionHtml;
    }

    public void setExplicacionHtml(String explicacionHtml) {
        this.explicacionHtml = explicacionHtml;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }
    
    
   
    

}

   




