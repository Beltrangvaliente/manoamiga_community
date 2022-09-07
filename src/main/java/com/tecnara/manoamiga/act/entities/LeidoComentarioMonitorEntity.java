
package com.tecnara.manoamiga.act.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "act_leido_comentarios_monitores")

public class LeidoComentarioMonitorEntity {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long id;
    public Long idComentario;
    public Long idMonitor;


   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Long getIdMonitor() {
        return idMonitor;
    }

    public void setIdMonitor(Long idMonitor) {
        this.idMonitor = idMonitor;
    }


    
    
    
}
