
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.AreaEntity;
import com.tecnara.manoamiga.expo.entities.EspacioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAreaRepository extends JpaRepository<AreaEntity, Long>{

     public List<AreaEntity>findByAreaContainingIgnoreCase(String area);
    
}
