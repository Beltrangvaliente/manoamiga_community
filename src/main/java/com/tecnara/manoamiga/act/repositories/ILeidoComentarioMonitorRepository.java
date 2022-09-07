
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.LeidoComentarioMonitorEntity;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ILeidoComentarioMonitorRepository extends JpaRepository<LeidoComentarioMonitorEntity, Long> {
    
public List<LeidoComentarioMonitorEntity>findByIdMonitor(Long idMonitor);

    public Optional<LeidoComentarioMonitorEntity> findByIdComentarioAndIdMonitor(long idComentario, long idMonitor);

}

