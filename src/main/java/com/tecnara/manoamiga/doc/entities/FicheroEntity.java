 
package com.tecnara.manoamiga.doc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="doc_ficheros")
public class FicheroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    public byte [] fichero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFichero() {
        return fichero;
    }

    public void setFichero(byte[] fichero) {
        this.fichero = fichero;
    }

    public void setNombre(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
