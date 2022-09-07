package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.HorarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IHorarioRepository extends JpaRepository<HorarioEntity, Long>{
    List<HorarioEntity> findByIdActividad(Long idActividad);
  
}
