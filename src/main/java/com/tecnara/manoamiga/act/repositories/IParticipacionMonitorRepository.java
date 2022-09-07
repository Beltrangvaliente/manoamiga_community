package com.tecnara.manoamiga.act.repositories;


import com.tecnara.manoamiga.act.entities.ParticipacionMonitorEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParticipacionMonitorRepository extends JpaRepository<ParticipacionMonitorEntity, Long> {

    public List<ParticipacionMonitorEntity> findByIdActividad(Long idActividad);

    public List<ParticipacionMonitorEntity> findByIdMonitor(Long idMonitor);
    
}

