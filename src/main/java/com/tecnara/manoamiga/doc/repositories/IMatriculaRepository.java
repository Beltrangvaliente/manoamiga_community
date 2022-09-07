package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IMatriculaRepository extends JpaRepository<MatriculaEntity, Long> {

    public List<MatriculaEntity> findByIdAlumno(Long idAlumno);
    public List<MatriculaEntity> findByIdCurso(Long idCurso);
    
    
    public Optional<MatriculaEntity> findByIdAndIdAlumno(Long id, Long idAlumno);
    
    public Optional<MatriculaEntity> findByIdAndIdAlumnoAndEstado(Long id, Long idAlumno, String estado);


    public Optional<MatriculaEntity> findByIdCursoAndIdAlumnoAndIdNot(Long idCurso, Long idAlumno, Long id);

    public List<MatriculaEntity> findByEstado(String estado); 
    
    @Query(value = "SELECT CONCAT (a.nombre, ' ', a.apellido1,' ' ,  a.apellido2) Alumno , a.id, c.nombre_curso Nombre, c.referencia Referencia, d.nombre Centro, m.estado Estado, m.fecha_matricula Fecha_de_Matricula\n"
            + "FROM doc_matriculas m\n"
            + "INNER JOIN doc_alumnos a ON a.id = m.id_alumno\n"
            + "INNER JOIN doc_cursos c ON c.id = m.id_curso\n"
            + "INNER JOIN doc_centros d ON d.id = c.id_centro\n"
            + "\n"
            + "INNER JOIN (SELECT concat('%',:filtro,'%') filtro , :fechaDesde fecha_desde, :fechaHasta fecha_hasta )\n"
            + "param\n"
            + "\n"
            + "WHERE (a.nombre LIKE param.filtro\n"
            + "OR a.apellido1 LIKE param.filtro\n"
            + "OR a.apellido2 LIKE param.filtro\n"
            + "OR c.nombre_curso LIKE param.filtro)\n"
            + "\n"
            + "AND (param.fecha_desde IS NULL OR m.fecha_matricula >= param.fecha_desde)\n"
            + "AND (param.fecha_hasta IS NULL OR m.fecha_matricula <= param.fecha_hasta)", nativeQuery = true)
    public List<Map> adminPlazoFinalizado(String filtro, Date fechaDesde, Date fechaHasta);
    
    @Query(value = "SELECT a.mail, CONCAT(a.nombre,' ',a.apellido1,' ',a.apellido2) persona, c.nombre_curso, ce.nombre, c.horario,\n"
            + "MAX(c.fecha_inicio) fechaInicio\n"
            + "FROM doc_alumnos a\n"
            + "LEFT JOIN doc_matriculas m ON(a.id = m.id_alumno)\n"
            + "LEFT JOIN doc_cursos c ON(c.id = m.id_curso)\n"
            + "LEFT JOIN doc_centros ce ON(ce.id = c.id_centro)\n"
            + "\n"
            + "WHERE m.estado = 'confirmado' \n"
            + "AND c.fecha_inicio = DATE_ADD(CURDATE(),INTERVAL 3 DAY)\n"
            + "GROUP BY a.mail,persona,c.nombre_curso", nativeQuery = true)
    public List<Map> scheduledMatriculaFechaInicio();
 
    public List<MatriculaEntity> findByIdCursoAndIdAlumno(Long idCurso, Long idValidado);

}
