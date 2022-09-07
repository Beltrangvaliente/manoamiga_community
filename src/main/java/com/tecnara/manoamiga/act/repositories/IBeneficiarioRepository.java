package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.ActividadEntity;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.act.dto.BeneficiarioDto;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IBeneficiarioRepository extends JpaRepository<BeneficiarioEntity, Long> {

    public List<BeneficiarioEntity> findByIdUsuario(Long idUsuario);

    public List<BeneficiarioEntity> findByIdTutor(Long idTutor);

    public List<BeneficiarioEntity> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT b.id, b.nombre nombre, b.apellido1 apellido1, b.apellido2 apellido2, b.documento documento, CONCAT(t.nombre, ' ', t.apellido, ' ') tutor\n"
            + "FROM act_beneficiarios b\n"
            + "LEFT JOIN act_tutores t ON (t.id = b.id_tutor)\n"
            + "INNER JOIN (SELECT CONCAT ('%', :filtro, '%') filtro) params\n"
            + "WHERE ( b.nombre LIKE params.filtro "
            + "OR b.apellido1 LIKE params.filtro "
            + "OR b.apellido2 LIKE params.filtro "
            + "OR b.documento LIKE params.filtro "
            + "OR CONCAT(t.nombre, ' ', t.apellido, ' ') LIKE params.filtro) limit 1001", nativeQuery = true)
    public List<Map> adminList(String filtro);

    @Query(value = "SELECT b.id, b.nombre, b.apellido1, b.apellido2, b.documento, CONCAT(t.nombre, ' ', t.apellido, ' ') tutor\n"
            + "  FROM act_beneficiarios b\n"
            + "  LEFT JOIN act_tutores t ON (t.id = b.id_tutor)\n"
            + "  INNER JOIN (SELECT CONCAT ('%',:filtro, '%') filtro) params\n"
            + "  WHERE ( b.nombre LIKE params.filtro \n"
            + "  OR b.apellido1 LIKE params.filtro \n"
            + "  OR b.apellido2 LIKE params.filtro \n"
            + "  OR b.documento LIKE params.filtro \n"
            + "  OR CONCAT(t.nombre, ' ', t.apellido, ' ') LIKE params.filtro)limit 1001", nativeQuery = true)
    public List<Map> monitorList(String filtro); //Nota, los monitores deben poder añadir alumnos que no son suyos, en principio no, pero habrá que evaluar si puede acceder a todos los beneficiarios para buscar.

    @Query(value = "SELECT b.*, (\n"
            + "	(SELECT COUNT(1) FROM act_comentarios WHERE id_beneficiario = b.id AND (leido_por_tutor IS NULL OR leido_por_tutor='No') ) +\n"
            + "	(SELECT COUNT(1) FROM act_faltas_asistencia WHERE id_beneficiario = b.id AND (leido_por_tutor IS NULL OR leido_por_tutor='No')) +\n"
            + "	(SELECT COUNT(1) FROM act_evaluaciones_beneficiarios WHERE id_beneficiario = b.id AND (leido_por_tutor IS NULL OR leido_por_tutor='No'))\n"
            + ") notificaciones\n"
            + "FROM act_beneficiarios b\n"
            + "INNER JOIN (SELECT :idTutor id_tutor) params\n"
            + "WHERE b.id_tutor=params.id_tutor", nativeQuery = true)
    public List<Map> notificacionSinLeer(Long idTutor);

    @Modifying
    @Transactional
    @Query(value = "UPDATE act_comentarios\n"
            + "SET leido_por_tutor='Si'\n"
            + "WHERE id_beneficiario = :idBeneficiario\n"
            + "AND id_beneficiario IN (\n"
            + "	SELECT id_beneficiario\n"
            + "	FROM act_beneficiarios b\n"
            + "	WHERE b.id_tutor=:idTutor\n"
            + ")", nativeQuery = true)

    public void tutorFormComentarioMarcarLeido(Long idBeneficiario, Long idTutor);

    @Modifying
    @Transactional
    @Query(value = "UPDATE act_evaluaciones_beneficiarios\n"
            + "SET leido_por_tutor='Si'\n"
            + "WHERE id_beneficiario = :idBeneficiario\n"
            + "AND id_beneficiario IN (\n"
            + "	SELECT id_beneficiario\n"
            + "	FROM act_beneficiarios b\n"
            + "	WHERE b.id_tutor=:idTutor\n"
            + ")", nativeQuery = true)

    public void tutorFormEvaluacionesMarcarLeido(Long idBeneficiario, Long idTutor);

    @Modifying
    @Transactional
    @Query(value = "UPDATE act_faltas_asistencia\n"
            + "SET leido_por_tutor='Si'\n"
            + "WHERE id_beneficiario = :idBeneficiario\n"
            + "AND id_beneficiario IN (\n"
            + "	SELECT id_beneficiario\n"
            + "	FROM act_beneficiarios b\n"
            + "	WHERE b.id_tutor=:idTutor\n"
            + ")", nativeQuery = true)

    public void tutorFormFaltaAsistenciaMarcarLeido(Long idBeneficiario, Long idTutor);

    public List<BeneficiarioEntity> findByNombreContainingOrApellido1ContainingOrApellido2ContainingOrDocumentoContaining(String nombre, String apellido1, String apellido2, String documento);

    @Query(value = "SELECT ab.id, CONCAT(IFNULL(ab.nombre,''), ' ', IFNULL(ab.apellido1,''),' ', IFNULL(ab.apellido2,'')) nombreCompleto\n"
            + "FROM act_beneficiarios ab\n"
            + "INNER JOIN act_participaciones_beneficiarios afa ON (afa.id_beneficiario = ab.id)\n"
            + "INNER JOIN act_actividades aa ON (aa.id = afa.id_actividad)\n"
            + "INNER JOIN act_participaciones_monitores pm ON (aa.id = pm.id_actividad)\n"
            + "INNER JOIN(SELECT :idActividad idActividad, CONCAT('%',:nombreCompleto,'%') nombre_completo, :idMonitor idMonitor)params\n"
            + "WHERE afa.id_actividad = params.idActividad\n"
            + "AND pm.id_monitor = params.idMonitor\n"
            + "AND (ab.nombre LIKE params.nombre_completo OR ab.apellido1 LIKE params.nombre_completo OR ab.apellido2 LIKE params.nombre_completo )", nativeQuery = true)
    public List<Map> monitorBuscadorFormBuscadorActividad(Long idActividad, String nombreCompleto, Long idMonitor);

    @Query(value = "SELECT p.id_beneficiario, DAYOFWEEK(tFechas.fecha)-1 'Dia', h.tipo_actividad, tFechas.fecha 'Fecha', CONCAT (b.nombre,' ',b.apellido1,' ',b.apellido2)'Beneficiario', a.nombre_actividad 'Actividad', h.horario_inicio 'Hora', h.horario_fin 'Hasta'\n"
            + "FROM act_actividades a\n"
            + "INNER JOIN act_participaciones_beneficiarios p ON (p.id_actividad = a.id)\n"
            + "INNER JOIN act_horarios h ON (h.id_actividad = a.id)\n"
            + "INNER JOIN act_beneficiarios b ON (b.id = p.id_beneficiario)\n"
            + "INNER JOIN act_tutores t ON (t.id=b.id_tutor)\n"
            + "INNER JOIN (\n"
            + "	SELECT CURDATE() fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 1 DAY) fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 2 DAY) fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 3 DAY) fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 4 DAY) fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 5 DAY) fecha FROM DUAL UNION ALL\n"
            + "	SELECT DATE_ADD(CURDATE(),INTERVAL 6 DAY) fecha FROM DUAL \n"
            + "\n"
            + ") tFechas\n"
            + "WHERE b.id_tutor = :idTutor\n"
            + "AND ( (h.tipo_actividad = 'Puntual' AND h.fecha_actividad=tFechas.fecha) OR \n"
            + "(h.tipo_actividad='Periodica' AND h.dia_semana=DAYOFWEEK(tFechas.fecha)-1 AND h.fecha_inicio <= tFechas.fecha AND h.fecha_fin >= tFechas.fecha\n"
            + ") )\n"
            + "ORDER BY tFechas.fecha, h.horario_inicio", nativeQuery = true)

    public List<Map> tutorListProximasActividades(Long idTutor);

    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN act_beneficiarios tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_ACT_Beneficiario'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)

    public List<Map> adminFromBuscador(String filtro, Long id);

    @Query(value = "SELECT DISTINCT b.*\n"
            + "FROM act_beneficiarios b \n"
            + "INNER JOIN act_clases_formativas cf ON(b.id = cf.id_beneficiario)\n"
            + "INNER JOIN (SELECT :idProfesor id_profesor, CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE cf.id_profesor=params.id_profesor\n"
            + "AND (b.nombre LIKE params.filtro OR b.apellido1 LIKE params.filtro OR b.apellido2 LIKE params.filtro)",
            nativeQuery = true)
    public List<BeneficiarioEntity> profesorList(Long idProfesor, String filtro);


    
    @Query(value = "SELECT count(1) \n"
            + "FROM aaa_usuarios u\n"
            + "INNER JOIN act_beneficiarios tra ON (u.id=tra.id_usuario)\n"
            + "WHERE rol='ROLE_ACT_Beneficiario'\n"
            + "AND u.estado='Pendiente'", nativeQuery = true)
    public Long adminContarUsuariosPendientes();


    @Query(value = "SELECT c.id, c.fecha_comentario, c.hora_comentario, c.texto, \n"
            + "	CONCAT(IFNULL(b.nombre,''), ' ', IFNULL(b.apellido1,''), ' ', IFNULL(b.apellido2,'')) beneficiario,\n"
            + "	CONCAT(\n"
            + "		IF(c.id_monitor IS NULL, '', CONCAT('Monitor-', IFNULL(m.nombre,''), ' ', IFNULL(m.apellido1,''), ' ', IFNULL(m.apellido2,''))),\n"
            + "		IF(c.id_profesor IS NULL, '', CONCAT('Profesor-', IFNULL(p.nombre,''), ' ', IFNULL(p.apellido1,''), ' ', IFNULL(p.apellido2,''))),\n"
            + "		IF(c.id_tutor IS NULL, '', CONCAT('Padre/Madre/Tutor-', IFNULL(t.nombre,''), ' ', IFNULL(t.apellido,'')))\n"
            + "	) persona\n"
            + "FROM act_comentarios c\n"
            + "INNER JOIN act_beneficiarios b ON (c.id_beneficiario = b.id)\n"
            + "INNER JOIN act_participaciones_beneficiarios pb ON (pb.id_beneficiario = b.id)\n"
            + "INNER JOIN act_actividades a ON (pb.id_actividad = a.id)\n"
            + "INNER JOIN act_participaciones_monitores pm ON (pm.id_actividad = a.id)\n"
            + "INNER JOIN act_monitores mon ON (mon.id = pm.id_monitor)\n"
            + "\n"
            + "LEFT JOIN act_leido_comentarios_monitores lcm ON (lcm.id_comentario = c.id AND lcm.id_monitor = mon.id)\n"
            + "\n"
            + "LEFT JOIN act_monitores m ON (c.id_monitor = m.id)\n"
            + "LEFT JOIN act_profesores p ON (c.id_profesor = p.id)\n"
            + "LEFT JOIN act_tutores t ON (c.id_monitor = t.id)\n"
            + "\n"
            + "INNER JOIN (SELECT 2 id_monitor) params\n"
            + "WHERE mon.id = params.id_monitor\n"
            + "AND lcm.id IS NULL -- Los que no están leidos", nativeQuery = true)
    public Long notificacionMonitor(Long id);



}
