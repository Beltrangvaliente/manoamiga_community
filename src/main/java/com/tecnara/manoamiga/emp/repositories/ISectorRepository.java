
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.SectorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISectorRepository extends JpaRepository<SectorEntity, Long>{
    public List <SectorEntity> findByid(Long id);
    public List <SectorEntity> findByNombreContainingIgnoreCase(String nombre);
}
