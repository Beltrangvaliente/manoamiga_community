package com.tecnara.manoamiga.doc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="doc_alumnos_necesidades_formacion")

public class AlumnoNecesidadFormacionEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    
    public Long idAlumno;
    public Long idNecesidadFormacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdNecesidadFormacion() {
        return idNecesidadFormacion;
    }

    public void setIdNecesidadFormacion(Long idNecesidadFormacion) {
        this.idNecesidadFormacion = idNecesidadFormacion;
    }
    
}
