package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.CitaEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ICitaRepository extends JpaRepository<CitaEntity, Long> {

    public List<CitaEntity> findByIdAlumno(Long idAlumno);

    public Optional<CitaEntity> findByIdAndIdAlumno(Long id, Long idAlumno);

    @Query(value = "SELECT c.id, c.fecha_de_cita fechaDeCita, c.hora_de_cita , ce.nombre nombreCentro\n"
            + "FROM doc_citas c\n"
            + "INNER JOIN doc_centros_atencion ce ON (ce.id=c.id_centro_de_atencion)\n"
            + "INNER JOIN (SELECT :id_alumno id_alumno)param\n"
            + "WHERE c.id_alumno=param.id_alumno\n"
            + "AND c.fecha_de_cita >= CURDATE()\n"
            + "ORDER BY c.fecha_de_cita", nativeQuery = true)
    public List<Map> alumnoCitaList(Long id_alumno);

    @Query(value = "SELECT c.id, c.fecha_de_cita fechaDeCita, c.hora_de_cita horaDeCita, ca.nombre centroDeAtencion, \n"
            + "	c.motivo_consulta motivoConsulta, c.explicacion_consulta explicacionConsulta, \n"
            + "	CONCAT(a.nombre, ' ', a.apellido1, ' ', a.apellido2) nombreAlumno, c.estado\n"
            + "FROM doc_citas c\n"
            + "LEFT JOIN doc_alumnos a ON(c.id_alumno=a.id)\n"
            + "LEFT JOIN doc_centros_atencion ca ON(c.id_centro_de_atencion=ca.id)\n"
            + "INNER JOIN (SELECT :fechaDeCita fechaDeCita, CONCAT('%', :centroDeAtencion,'%') centroDeAtencion, :estado estado, CONCAT('%',:nombreAlumno,'%') nombreAlumno) param\n"
            + "WHERE (param.fechaDeCita IS NULL OR c.fecha_de_cita=param.fechaDeCita)\n"
            + "AND (ca.nombre LIKE param.centroDeAtencion)\n"
            + "AND (param.estado='' OR c.estado=param.estado)\n"
            + "AND (a.nombre LIKE param.nombreAlumno OR a.apellido1 LIKE param.nombreAlumno\n"
            + "OR a.apellido2 OR param.nombreAlumno)", nativeQuery = true)
    public List<Map> adminCitaListUsuariosApuntados(Date fechaDeCita, String centroDeAtencion, String estado, String nombreAlumno);

    @Query(value = "select c.id,SUBSTRING(c.fecha_de_cita,1,10) fechaDeCita, c.hora_de_cita horaDeCita, ca.nombre \n"
            + "FROM doc_citas c\n"
            + "LEFT JOIN doc_alumnos a ON(c.id_alumno=a.id)\n"
            + "LEFT JOIN doc_centros_atencion ca ON (c.id_centro_de_atencion = ca.id)\n"
            + "INNER JOIN (SELECT :id_alumno id_alumno) param\n"
            + "WHERE (c.id_alumno=param.id_alumno)"
            + "AND c.fecha_de_cita  >= CURDATE()\n"
            + "ORDER BY c.fecha_de_cita", nativeQuery = true)
    public List<Map> alumnoCitaListCalendario(Long id_alumno);

    @Modifying
    @Query(value = "INSERT INTO doc_citas (estado, fecha_de_cita, hora_de_cita, id_alumno, id_curso, motivo_consulta) \n"
            + "SELECT 'Aceptado', params.fecha_cita, params.hora_cita, id_alumno, id_curso, params.motivo_consulta\n"
            + "FROM doc_matriculas\n"
            + "INNER JOIN (SELECT :idCurso idCurso, :fechaCita fecha_cita, :horaCita hora_cita, :motivoConsulta motivo_consulta ) params\n"
            + "WHERE id_curso=params.idCurso", nativeQuery = true)
    public int insertCitasACurso(Long idCurso, Date fechaCita, String horaCita, String motivoConsulta);

    @Query(value = "SELECT a.nombre,a.apellido1,a.apellido2,a.mail,c.fecha_de_cita,c.hora_de_cita,c.estado\n"
            + "FROM doc_alumnos a\n"
            + "INNER JOIN doc_citas c ON (c.id_alumno=a.id)\n"
            + "WHERE fecha_de_cita = DATE_ADD(CURDATE(), INTERVAL 1 DAY)\n"
            + "AND c.estado = 'aceptado'", nativeQuery = true)
    public List<Map> scheduledCitaList();

    @Query(value = "SELECT DISTINCT fecha_de_cita fechaDeCita\n"
            + "FROM doc_citas c\n"
            + "INNER JOIN (SELECT :idCentroDeAtencion id_centro_de_atencion) params\n"
            + "WHERE c.id_alumno IS NULL\n"
            + "AND c.id_centro_de_atencion=params.id_centro_de_atencion\n"
            + "AND c.fecha_de_cita >= CURDATE()\n"
            + "ORDER BY fecha_de_cita\n"
            + "LIMIT 15", nativeQuery = true)
    public List<Map> fechaDisponibleCitaForm(Long idCentroDeAtencion);

    @Query(value = "SELECT DISTINCT hora_de_cita\n"
            + "FROM doc_citas c\n"
            + "INNER JOIN (SELECT :idCentroDeAtencion id_centro_de_atencion, :fecha fecha) params\n"
            + "WHERE c.id_alumno IS NULL\n"
            + "AND c.id_centro_de_atencion=params.id_centro_de_atencion\n"
            + "AND c.fecha_de_cita = params.fecha\n"
            + "AND fecha_de_cita >= CURDATE()\n"
            + "ORDER BY hora_de_cita", nativeQuery = true)
    public List<Map> horaDisponibleCitaForm(Long idCentroDeAtencion, Date fecha);

    @Query(value = "SELECT c.fecha_de_cita fecha, ca.nombre, COUNT(*) disponibles \n"
            + "FROM doc_citas c\n"
            + "INNER JOIN doc_centros_atencion ca ON(ca.id = c.id_centro_de_atencion)\n"
            + "WHERE id_alumno IS NULL\n"
            + "GROUP BY fecha_de_cita, ca.nombre\n"
            + "ORDER BY fecha_de_cita, ca.nombre", nativeQuery = true)
    public List<Map> adminAgenda();

    
    @Modifying
    @Transactional
    @Query(value = "UPDATE doc_citas\n"
            + "SET id_alumno=:idAlumno\n"
            + "WHERE id_centro_de_atencion = :idCentroDeAtencion\n"
            + "AND fecha_de_cita=:fechaDeCita\n"
            + "AND hora_de_cita=:horaDeCita\n"
            + "AND id_alumno IS NULL", nativeQuery = true)
    
    public void alumnoCitaUpdate(Long idAlumno, Long idCentroDeAtencion , Date fechaDeCita, String horaDeCita );
    
    
    
    
    
    

}
