
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
@Table(name = "expo_aspectos")
public class AspectoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String colorFondo;
    private String colorTexto;
    private String tipoLetra;
    private String colorTitulosFondo;
    private String colorTitulosLetra;
    private Integer prioridad;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    private Long idImagen;

    public AspectoEntity(Long id, String nombre, String colorFondo, String colorTexto, String tipoLetra, Date fechaInicio, Date fechaFin, Long idImagen) {
        this.id = id;
        this.nombre = nombre;
        this.colorFondo = colorFondo;
        this.colorTexto = colorTexto;
        this.tipoLetra = tipoLetra;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idImagen = idImagen;
    }

    public AspectoEntity() {
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

    public String getColorFondo() {
        if (colorFondo==null){
            return "#FFF";
        }
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }

    public String getColorTexto() {
        if (colorTexto==null){
            return "#000";
        }
        return colorTexto;
    }

    public void setColorTexto(String colorTexto) {
        this.colorTexto = colorTexto;
    }

    public String getTipoLetra() {
        return tipoLetra;
    }

    public void setTipoLetra(String tipoLetra) {
        this.tipoLetra = tipoLetra;
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

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getColorTitulosFondo() {
        if (colorTitulosFondo==null){
            return "#FFF";
        }
        return colorTitulosFondo;
    }

    public void setColorTitulosFondo(String colorTitulosFondo) {
        this.colorTitulosFondo = colorTitulosFondo;
    }

    public String getColorTitulosLetra() {
        if (colorTitulosLetra==null){
            return "#000";
        }
        return colorTitulosLetra;
    }

    public void setColorTitulosLetra(String colorTitulosLetra) {
        this.colorTitulosLetra = colorTitulosLetra;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    
    
}   
