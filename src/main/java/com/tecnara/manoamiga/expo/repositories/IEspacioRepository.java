
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEspacioRepository extends JpaRepository<EspacioEntity, Long>{
    
    public List<EspacioEntity>findByUbicacionContainingIgnoreCase(String ubicacion);
    
}
