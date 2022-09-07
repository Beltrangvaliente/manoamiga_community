
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import com.tecnara.manoamiga.act.entities.SolicitudEvaluacionMonitorEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PostMapping;





public interface ISolicitudEvaluacionMonitorRepository extends JpaRepository<SolicitudEvaluacionMonitorEntity, Long>{ 
       
    @Query(value = "SELECT *\n"
            + "FROM act_solicitudes_evaluaciones_monitores solevamon \n"
            + "WHERE id_actividad = :idActividad", nativeQuery = true)
    public List<SolicitudEvaluacionMonitorEntity> listDataSolicitudEvaluacion(Long idActividad);
   

}
