package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.dto.ActivityDto;
import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IActividadRepository extends JpaRepository<ActividadEntity, Long> {

    public List<ActividadEntity> findByNombreActividadContainingIgnoreCase(String nombreActividad);

    @Query(value = "select id from act_actividades where id=:id ORDER BY id", nativeQuery = true)
    public List<Map> consultarDatos(Long id);

    @Query(value = "select id from act_actividades where id=:id ORDER BY id \\n-- #pageable\\n",
            countQuery = "SELECT count(*) FROM act_actividades ",
            nativeQuery = true)
    public List<Map> consultarDatos2(Long id, Pageable pageable);

    @Query(value = "SELECT a.id, a.tipo_actividad tipoActividad ,a.nombre_actividad nombreActividad, a.lugar_actividad lugarActividad, a.plaza_actividad plazaActividad, a.fecha_actualizacion fechaActualizacion,a.responsable_actividad responsableActividad,\n"
            + "	REPLACE(GROUP_CONCAT(\n"
            + "		IF(h.tipo_actividad='Periodica',\n"
            + "			CONCAT(CASE\n"
            + "					WHEN dia_semana=1 THEN 'Lunes ' \n"
            + "					WHEN dia_semana=2 THEN 'Martes '\n"
            + "					WHEN dia_semana=3 THEN 'Miércoles '\n"
            + "					WHEN dia_semana=4 THEN 'Jueves '\n"
            + "					WHEN dia_semana=5 THEN 'Viernes '\n"
            + "					WHEN dia_semana=6 THEN 'Sábado '\n"
            + "					WHEN dia_semana=7 THEN 'Domingo '\n"
            + "				END, \n"
            + "				h.horario_inicio, ' a ', h.horario_fin ),\n"
            + "			CONCAT('Dia ', h.fecha_actividad, ' (', h.horario_inicio, ' a ', h.horario_fin,')' )\n"
            + "		)\n"
            + "	),',','<br/>\\n') horario\n"
            + "FROM act_actividades a\n"
            + "LEFT JOIN act_horarios h ON (h.id_actividad = a.id)\n"
            + "INNER JOIN (SELECT :tipoActividad tipo_actividad, CONCAT('%',:nombreActividad,'%') nombre_actividad, :fechaDesde fecha_desde, :fechaHasta fecha_hasta, CONCAT('%',:responsable,'%')  responsable_actividad) params\n"
            + "WHERE (params.tipo_actividad='' OR a.tipo_actividad = params.tipo_actividad)\n"
            + "AND a.nombre_actividad LIKE params.nombre_actividad\n"
            + "AND (params.fecha_desde IS NULL OR a.fecha_actualizacion>=params.fecha_desde)\n"
            + "AND (params.fecha_hasta IS NULL OR a.fecha_actualizacion<=params.fecha_hasta)\n"
            + "AND a.responsable_actividad LIKE params.responsable_actividad "
            + " GROUP BY a.id, a.tipo_actividad ,a.nombre_actividad , a.lugar_actividad , a.plaza_actividad , a.fecha_actualizacion ,a.responsable_actividad "
            + " limit 1001", nativeQuery = true)

    public List<Map> adminList(String tipoActividad, String nombreActividad, Date fechaDesde, Date fechaHasta, String responsable);

    @Query(value = "SELECT DISTINCT a.id, a.tipo_actividad ,a.nombre_actividad, a.lugar_actividad  , a.plaza_actividad, a.fecha_actualizacion,a.responsable_actividad\n"
            + "FROM act_actividades a\n"
            + "INNER JOIN act_participaciones_monitores apm ON (apm.id_actividad=a.id)\n"
            + "INNER JOIN (SELECT :tipoActividad tipo_actividad, CONCAT('%',:nombreActividad,'%') nombre_actividad, :fechaDesde fecha_desde, :fechaHasta fecha_hasta, CONCAT('%',:responsable,'%')  responsable_actividad, :idMonitor id_monitor) params\n"
            + "WHERE (params.tipo_actividad='' OR a.tipo_actividad = params.tipo_actividad)\n"
            + "AND a.nombre_actividad LIKE params.nombre_actividad\n"
            + "AND (params.fecha_desde IS NULL OR a.fecha_actualizacion>=params.fecha_desde)\n"
            + "AND (params.fecha_hasta IS NULL OR a.fecha_actualizacion<=params.fecha_hasta)\n"
            + "AND (params.id_monitor = apm.id_monitor)\n"
            + "AND a.responsable_actividad LIKE params.responsable_actividad limit 1001", nativeQuery = true)

    public List<Map> monitorList(String tipoActividad, String nombreActividad, Date fechaDesde, Date fechaHasta, String responsable, Long idMonitor);

    @Query(value = "SELECT aa.*\n"
            + "FROM act_actividades aa\n"
            + "INNER JOIN act_participaciones_beneficiarios apb ON(apb.id_actividad= aa.id)\n"
            + "INNER JOIN act_participaciones_monitores apm ON (apm.id_actividad=aa.id)\n"
            + "INNER JOIN (SELECT :idBeneficiario idBeneficiario, CONCAT('%',:actividad,'%') actividad, :idMonitor idMonitor)params\n"
            + "WHERE(apb.id_beneficiario = params.idBeneficiario) \n"
            + "AND (apm.id_monitor = params.idMonitor)\n"
            + "AND (aa.nombre_actividad LIKE params.actividad)", nativeQuery = true)
    public List<ActividadEntity> monitorBuscadorFormBuscadorBeneficiario(String actividad, Long idBeneficiario, Long idMonitor);

    @Query(value = "SELECT ben.id_beneficiario, ho.fecha_inicio, ho.horario_fin, ho.horario_inicio, act.lugar_actividad, act.nombre_actividad, ho.tipo_actividad\n"
            + "FROM act_horarios ho \n"
            + "INNER JOIN act_actividades act ON(act.id=ho.id_actividad) \n"
            + "INNER JOIN act_participaciones_beneficiarios ben ON(ben.id_actividad=act.id) \n"
            + "WHERE ben.id_beneficiario=:id_beneficiario\n"
            + "AND((ho.tipo_actividad='Puntual' AND ho.fecha_actividad=CURDATE())\n"
            + "	OR(ho.tipo_actividad='Periódica' AND ho.dia_semana=DAYOFWEEK(CURDATE())-1\n"
            + "		AND ho.fecha_inicio <=CURDATE() AND ho.fecha_fin >=CURDATE()))\n"
            + "ORDER BY  ho.horario_inicio", nativeQuery = true)

    public List<Map> beneficiarioListHoy(Long id_beneficiario);

    @Query(value = "SELECT ev.id,act.nombre_actividad , ev.fecha_de_evaluacion , ev.puntuacion\n"
            + "FROM act_evaluaciones_beneficiarios ev\n"
            + "INNER JOIN act_actividades act ON ( act.id = ev.id_actividad)\n"
            + "INNER JOIN act_beneficiarios b ON(b.id= ev.id_beneficiario)\n"
            + "WHERE ev.id_beneficiario= :idBeneficiario\n"
            + "ORDER BY ev.fecha_de_evaluacion DESC", nativeQuery = true)
    public List<Map> beneficiarioListEvaluacion(Long idBeneficiario);

    @Query(value = " SELECT DISTINCT dia, ho.id, tFechas.fechas fecha, ho.horario_fin, ho.horario_inicio, act.lugar_actividad, act.nombre_actividad\n"
            + "FROM act_horarios ho\n"
            + "INNER JOIN act_actividades act ON(act.id=ho.id_actividad)\n"
            + "INNER JOIN act_participaciones_beneficiarios ben ON(ben.id_actividad=act.id)\n"
            + "INNER JOIN (\n"
            + "	SELECT CURDATE() fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 1 DAY)fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 2 DAY)fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 3 DAY)fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 4 DAY)fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 5 DAY)fechas FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(), INTERVAL 6 DAY)fechas FROM DUAL \n"
            + ")tFechas\n"
            + "INNER JOIN (\n"
            + "	SELECT 'Lunes' dia, 1 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Martes' dia, 2 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Miércoles' dia, 3 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Jueves' dia, 4 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Viernes' dia, 5 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Sábado' dia, 6 numDia FROM DUAL UNION ALL\n"
            + "	SELECT 'Domingo' dia, 0 numDia FROM DUAL \n"
            + ") tDias ON (DAYOFWEEK(tFechas.fechas)-1 = tDias.numDia)\n"
            + "WHERE ben.id_beneficiario=:idBeneficiario\n"
            + "AND((ho.tipo_actividad='Puntual' AND ho.fecha_actividad=tFechas.fechas)\n"
            + "	OR(ho.tipo_actividad='Periódica' AND ho.dia_semana=DAYOFWEEK(tFechas.fechas)-1\n"
            + "		AND ho.fecha_inicio <=tFechas.fechas AND ho.fecha_fin >=tFechas.fechas))\n"
            + "ORDER BY ho.tFechas.fechas, ho.horario_fin, ho.horario_inicio, act.lugar_actividad, act.nombre_actividad ", nativeQuery = true)

    public List<Map> beneficiarioListSemanal(Long idBeneficiario);

    @Query(value = "SELECT a.id, a.tipo_actividad tipoActividad, a.nombre_actividad nombreActividad, a.responsable_actividad responsableActividad, a.lugar_actividad lugarActividad,\n"
            + "	REPLACE(GROUP_CONCAT(\n"
            + "		IF(h.tipo_actividad='Periodica',\n"
            + "			CONCAT(CASE\n"
            + "					WHEN dia_semana=1 THEN 'Lunes ' \n"
            + "					WHEN dia_semana=2 THEN 'Martes '\n"
            + "					WHEN dia_semana=3 THEN 'Miércoles '\n"
            + "					WHEN dia_semana=4 THEN 'Jueves '\n"
            + "					WHEN dia_semana=5 THEN 'Viernes '\n"
            + "					WHEN dia_semana=6 THEN 'Sábado '\n"
            + "					WHEN dia_semana=7 THEN 'Domingo '\n"
            + "				END, \n"
            + "				h.horario_inicio, ' a ', h.horario_fin ),\n"
            + "			CONCAT('Dia ', h.fecha_actividad, ' (', h.horario_inicio, ' a ', h.horario_fin,')' )\n"
            + "		)\n"
            + "	),',','<br/>\\n') horario\n"
            + "FROM act_participaciones_monitores pm\n"
            + "INNER JOIN act_actividades a ON (a.id = pm.id_actividad)\n"
            + "LEFT JOIN act_horarios h ON (h.id_actividad = a.id)\n"
            + "WHERE pm.id_monitor = :idMonitor\n"
            + "GROUP BY a.id, a.tipo_actividad, a.nombre_actividad, a.responsable_actividad, a.lugar_actividad limit 1001", nativeQuery = true)
    public List<Map> monitorList(Long idMonitor);

    @Query(value = "SELECT evabene.id, b.nombre nombre_beneficiario, CONCAT(b.apellido1, ' ', b.apellido2) apellidos_beneficiario\n"
            + "FROM act_evaluaciones_beneficiarios evabene \n"
            + "INNER JOIN act_beneficiarios b ON(b.id=evabene.id_beneficiario)\n"
            + "WHERE id_actividad = :idActividad", nativeQuery = true)
    public List<Map> monitorFormEvaluaciones(Long idActividad);

    @Query(value = "SELECT solevamon.id, m.id idMonitor, m.nombre nombreMonitor, CONCAT(m.apellido1, ' ', m.apellido2) apellidosMonitor, a.nombre_actividad nombreActividad\n"
            + "FROM act_monitores m\n"
            + "INNER JOIN act_participaciones_monitores partmon ON(partmon.id_monitor=m.id)\n"
            + "INNER JOIN act_actividades a ON(a.id=partmon.id_actividad)\n"
            + "INNER JOIN act_participaciones_beneficiarios partben ON(partben.id_actividad=a.id)\n"
            + "INNER JOIN act_solicitudes_evaluaciones_monitores solevamon ON(solevamon.id=a.id)\n"
            + "LEFT JOIN act_evaluaciones_monitores evamon ON(m.id=evamon.id_monitor AND evamon.id_beneficiario=partben.id_beneficiario AND evamon.id_actividad=a.id)\n"
            + "WHERE partben.id_beneficiario = :id_beneficiario\n"
            + "AND IFNULL(fecha_inicio,CURDATE()) <=CURDATE() AND IFNULL(fecha_fin,'2200-01-01') >= CURDATE()\n"
            + "AND evamon.valoracion IS NULL", nativeQuery = true)

    public List<Map> beneficiarioListMonitores(Long id_beneficiario);

    
    @Query(value = "SELECT*\n"
            + "FROM act_actividades aa\n"
            + "WHERE publicar='si'", nativeQuery = true)
    public List<Map> tutorList();

}
