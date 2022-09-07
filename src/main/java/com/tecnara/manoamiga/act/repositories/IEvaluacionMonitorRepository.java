package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.EvaluacionMonitorEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEvaluacionMonitorRepository extends JpaRepository<EvaluacionMonitorEntity, Long> {

    public List<EvaluacionMonitorEntity> findByIdBeneficiario(Long idBeneficiario);

    public List<EvaluacionMonitorEntity> findByIdMonitor(Long idMonitor);

    public List<EvaluacionMonitorEntity> findByIdMonitorAndIdBeneficiario(Long idMonitor, Long idBeneficiario);

    public List<EvaluacionMonitorEntity> findByIdMonitorAndIdBeneficiarioOrderByFechaComentarioDesc(Long idValidado, Long idBeneficiario);

    public Optional<EvaluacionMonitorEntity> findByIdSolicitudEvaluacionMonitorAndIdMonitorAndIdBeneficiario(Long idSolicitudEvaluacionMonitor, Long idMonitor, Long idBeneficiario);

    @Query(value = "SELECT em.id, em.comentario, em.valoracion, em.fecha_comentario fechaComentario, ac.nombre_actividad nombreActividad, em.id_monitor\n"
            + "FROM act_evaluaciones_monitores em\n"
            + "INNER JOIN act_actividades ac ON (ac.id = em.id_actividad)\n"
            + "INNER JOIN (SELECT :idMonitor id_monitor) params\n"
            + "WHERE em.id_monitor = params.id_monitor\n"
            + "ORDER BY em.fecha_comentario DESC", nativeQuery = true)
    public List<Map> monitorList(Long idMonitor);

}
