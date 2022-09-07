package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.CentroAtencionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICentroAtencionRepository extends JpaRepository<CentroAtencionEntity,Long>{
    public List<CentroAtencionEntity> findByNombreContainingIgnoreCase(String nombre);
           
}
