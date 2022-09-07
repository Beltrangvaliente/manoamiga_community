package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.NecesidadFormacionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface INecesidadFormacionRepository extends JpaRepository<NecesidadFormacionEntity, Long>{ //en el 1er object se pone el entity que corresponda y en el 2do Long

    public List<NecesidadFormacionEntity> findByNombreContaining(String filtro);

    public List<NecesidadFormacionEntity> findByNombreContainingOrIdiomaContainingOrEdadContaining(String nombre, String idioma, String edad);

    
}
