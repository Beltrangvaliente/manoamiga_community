package com.tecnara.manoamiga.doc.services;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUtilidadesService {
    
    public void guardarObjetoValoresNoNulos(JpaRepository repositorio, Object objeto);
    

    public Long getIdAlumnoValidado();
    public Long getIdProfesorValidado();
    public Long getIdUsuarioValidado();
    public String formatearFecha(Date d);

    public<T> T findById(JpaRepository repo, Long id, Class<T> type);
    
}
