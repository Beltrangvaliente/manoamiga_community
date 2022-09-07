/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.ComentarioEntity;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

    public List<ComentarioEntity> findByIdMonitor(Long idMonitor);

    public List<ComentarioEntity> findByIdTutor(Long idTutor);

    public List<ComentarioEntity> findByIdProfesor(Long idProfesor);

    public List<ComentarioEntity> findByIdMonitorAndIdBeneficiario(Long idMonitor, Long idBeneficiario);

    @Query(value = "SELECT DISTINCT c.*\n"
            + "FROM act_comentarios c\n"
            + "INNER JOIN act_beneficiarios b ON (c.id = b.id_tutor)\n"
            + "INNER JOIN act_participaciones_beneficiarios pb ON (b.id = pb.id_beneficiario)\n"
            + "INNER JOIN act_actividades a ON (a.id = pb.id_actividad)\n"
            + "INNER JOIN act_participaciones_monitores pm ON (pm.id_monitor = a.id)     \n"
            + "INNER JOIN (SELECT :idMonitor id_monitor) params\n"
            + "WHERE c.id_tutor IS NOT NULL\n"
            + "AND  pm.id_monitor LIKE params.id_monitor   \n"
            + "ORDER BY fecha_comentario DESC", nativeQuery = true)
    public List<Map> monitorList (Long idMonitor);
    
    public List<ComentarioEntity> findByIdBeneficiarioOrderByFechaComentarioDescHoraComentarioDesc(Long idBeneficiario);
}
