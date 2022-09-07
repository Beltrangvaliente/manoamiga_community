package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMonitorRepository extends JpaRepository<MonitorEntity, Long> {

    public List<MonitorEntity> findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(String nombre, String apellido1, String apellido2);

    public List<MonitorEntity> findByIdUsuario(Long idUsuario);

    public List<MonitorEntity> findByNombreContainingIgnoreCase(String nombre);

    @Query(value = "SELECT m.id, a.usuario, m.nombre, m.apellido1, m.apellido2, m.telefono\n"
            + "	FROM act_monitores m \n"
            + "	LEFT JOIN aaa_usuarios a ON(a.id=m.id_usuario)\n"
            + "	INNER JOIN(SELECT CONCAT('%', :filtro,'%') filtro) param\n"
            + "	WHERE (a.usuario LIKE param.filtro OR m.nombre LIKE param.filtro OR m.apellido1 LIKE param.filtro \n"
            + "	OR m.apellido2 LIKE param.filtro OR m.telefono LIKE param.filtro) limit 1001", nativeQuery = true)
    
    public List<Map> adminMonitorList (String filtro);

    public List<MonitorEntity> findByNombreContainingIgnoreCaseOrApellido1ContainingOrApellido2Containing(String filtro, String filtro0, String filtro1);
    
    
        @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN act_monitores m ON (u.id=m.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_ACT_Monitor'\n"
            + "AND (m.id IS NULL OR m.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)
    public List<Map> adminMonitorFromBuscador(String filtro, Long id);  

    @Query(value = "SELECT count(1) \n"
            + "FROM aaa_usuarios u\n"
            + "INNER JOIN act_monitores m ON (u.id=m.id_usuario)\n"
            + "WHERE rol='ROLE_ACT_Monitor'\n"
            + "AND u.estado='Pendiente'", nativeQuery = true)
    public Long adminContarUsuariosPendientes();
    

    @Query(value = "SELECT em.id, em.comentario, em.valoracion, em.fecha_comentario fechaComentario, ac.nombre_actividad nombreActividad, em.id_monitor, \n"
            + "CONCAT(ifnull(be.nombre,''),' ', ifnull(be.apellido1,''),' ', ifnull(be.apellido2,'')) beneficiario \n"
            + "FROM act_evaluaciones_monitores em\n"
            + "INNER JOIN act_actividades ac ON (ac.id = em.id_actividad)\n"
            + "INNER JOIN act_beneficiarios be on (be.id = em.id_beneficiario)"
            + "INNER JOIN (SELECT :idMonitor id_monitor) params\n"
            + "WHERE em.id_monitor = params.id_monitor\n"
            + "ORDER BY em.fecha_comentario DESC", nativeQuery = true)
    public List<Map> adminFormOpiniones(Long idMonitor);       

    
}
