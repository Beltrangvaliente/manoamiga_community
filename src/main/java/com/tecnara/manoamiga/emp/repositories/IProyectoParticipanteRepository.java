package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoParticipanteEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProyectoParticipanteRepository extends JpaRepository<ProyectoParticipanteEntity, Long>{
    
}
