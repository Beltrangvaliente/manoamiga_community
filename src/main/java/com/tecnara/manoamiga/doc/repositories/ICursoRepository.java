package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.dto.ClaveValorDto;
import com.tecnara.manoamiga.doc.entities.CursoEntity;
import com.tecnara.manoamiga.doc.entities.MatriculaEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.MapsId;

import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

public interface ICursoRepository extends JpaRepository<CursoEntity, Long> {

    public List<CursoEntity> findByNombreCursoContainingIgnoreCase(String nombreCurso);

    public List<CursoEntity> findByIdCentro(Long idCentro);

    public List<CursoEntity> findByIdEmpresa(Long idEmpresa);

    @Query(value = "SELECT ma.id, cu.id idCurso, cu.nombre_curso nombreCurso, ce.nombre centro, cu.horario horario, cu.modalidad modalidad, ce.direccion localizacion ,cu.fecha_inicio fechaInicio, ma.estado\n"
            + "\n"
            + "FROM doc_matriculas ma\n"
            + "INNER JOIN doc_alumnos a ON(a.id=ma.id_alumno)\n"
            + "INNER JOIN doc_cursos cu ON (cu.id= ma.id_curso)\n"
            + "LEFT JOIN doc_centros ce ON(ce.id=cu.id_centro)"
            + "INNER JOIN (SELECT :idAlumno id_alumno)params\n"
            + "WHERE ma.id_alumno =params.id_alumno", nativeQuery = true)

    public List<Map> alumnoList(Long idAlumno);
    //public Optional<MatriculaEntity> findByIdAndIdAlumnoAndEstado(Long idAlumno, String estado);    

    @Query(value = " select m.id, concat(a.nombre, ' ', a.apellido1, ' ',a.apellido2) usuario,\n"
            + "	a.telefono, a.fecha_nacimiento fechaNacimiento, m.estado estadoMatricula\n"
            + "FROM doc_matriculas m\n"
            + "LEFT JOIN doc_alumnos a ON(m.id_alumno=a.id)\n"
            + "LEFT JOIN aaa_usuarios u ON(a.id_usuario=u.id)\n"
            + "LEFT JOIN doc_cursos c ON(m.id_curso=c.id)\n"
            + "inner join (select :id_curso id_curso) param\n"
            + "where (param.id_curso='' or m.id_curso=param.id_curso)", nativeQuery = true)

    public List<Map> adminCursoFormMatriculas(Long id_curso);

    @Query(value = "SELECT cu.nombre_curso nombreCurso, cu.modalidad, cu.horario, ce.nombre, cu.fecha_inicio fechaInicio, cu.id\n"
            + "FROM doc_cursos cu\n"
            + "LEFT JOIN doc_centros ce ON( ce.id = cu.id_centro)\n"
            + "INNER JOIN (SELECT CONCAT('%', :filtro, '%') filtro) params\n"
            + "WHERE(cu.nombre_curso LIKE params.filtro\n"
            + "	OR cu.modalidad LIKE params.filtro\n"
            + "	OR ce.nombre LIKE params.filtro\n"
            + ")", nativeQuery = true)

    public List<Map> cursoList(String filtro);

    @Query(value = "SELECT dr.id,dr.tipo_de_documento tipodocumento,dr.dias_plazo diasplazo,dr.descripcion descripcion,dr.obligatorio obligatorio\n"
            + "FROM doc_documentos_requeridos dr\n"
            + "INNER JOIN doc_cursos c ON(dr.id_curso=c.id)\n"
            + "\n"
            + "INNER JOIN (SELECT :id_curso id_curso)params\n"
            + "WHERE (dr.id_curso=params.id_curso)", nativeQuery = true)

    public List<Map> adminFormDocumentoRequeridos(Long id_curso);

    @Query(value = "SELECT c.id, c.nombre_curso nombreCurso, ce.nombre nombreCentro, c.numero_horas numeroHoras, c.modalidad, c.localidad, c.fecha_inicio fechaInicio, c.fecha_fin fechaFin, c.horario\n"
            + "FROM doc_cursos c\n"
            + "LEFT JOIN doc_centros ce ON (ce.id = c.id_centro) \n"
            + "\n"
            + "INNER JOIN(SELECT CONCAT('%', :filtro, '%') filtro, :mes mes,  :modalidad modalidad, :horario horario) params\n"
            + "\n"
            + "WHERE (c.nombre_curso LIKE params.filtro)\n"
            + "\n"
            + "AND (params.mes is null OR MONTH(c.fecha_inicio) = params.mes)\n"
            + "AND (params.modalidad = '' OR c.modalidad = params.modalidad)\n"
            + "AND (params.horario = '' OR (params.horario='Mañana' AND c.horario <= \"14\") OR (params.horario='Tarde' AND c.horario > \"14\"))",
            nativeQuery = true)

    public List<Map> publicList(String filtro, Long mes, String modalidad, String horario);

    @Query(value = "SELECT pc.id, p.nombre_prof Nombre, p.apellido1prof primerApellido, p.apellido2prof segundoApellido\n"
            + "FROM doc_profesores_cursos pc\n"
            + "INNER JOIN doc_profesor p ON (p.id=pc.id_profesor)\n"
            + "INNER JOIN  doc_cursos c ON (c.id=pc.id_curso)\n"
            + "INNER JOIN (SELECT :id_curso id_curso)param\n"
            + "WHERE pc.id_curso=param.id_curso\n", nativeQuery = true)

    public List<Map> adminCursoFormProfesores(Long id_curso);

    @Query(value = "SELECT cr.nombre_curso valor, pc.id_curso clave \n"
            + "              FROM doc_profesores_cursos pc \n"
            + "              LEFT JOIN  doc_cursos cr ON(pc.id_curso=cr.id) \n"
            + "              INNER JOIN (SELECT CONCAT ('%', :filtro, '%') filtro,  :idProfesor id_profesor )params\n"
            + "              WHERE (pc.id_profesor = params.id_profesor )\n"
            + "              AND cr.nombre_curso LIKE params.filtro", nativeQuery = true)

    public List<Map> buscador(String filtro, Long idProfesor);

    @Query(value = "SELECT dcur.id, dcur.nombre_curso Curso, dcen.nombre Centro, dcur.localidad Localizacion, dcur.fecha_inicio fechaInicio\n"
            + "FROM doc_cursos dcur\n"
            + "LEFT JOIN doc_centros dcen ON (dcen.id = dcur.id_centro)\n"
            + "INNER JOIN doc_profesores_cursos pc ON (pc.id_curso = dcur.id)\n"
            + "INNER JOIN (SELECT CONCAT ('%', :filtro, '%') filtro, :id_profesor id_profesor )params\n"
            + "WHERE ( dcur.nombre_curso LIKE params.filtro OR dcen.nombre LIKE params.filtro OR dcur.localidad LIKE params.filtro)\n"
            + "AND pc.id_profesor = params.id_profesor "
            + " order by dcur.fecha_inicio desc ", nativeQuery = true)
    public List<Map> profesorList(String filtro, Long id_profesor);

    @Query(value = "SELECT cu.id, cu.nombre_curso\n"
            + "FROM doc_profesor p \n"
            + " LEFT JOIN doc_profesores_cursos c ON (p.id = c.id_profesor)\n"
            + " LEFT JOIN doc_cursos cu ON (cu.id = c.id_curso)\n"
            + " INNER JOIN (SELECT :idProfesor idProfesor, :fechaDesde fechaDesde, :fechaHasta fechaHasta) params\n"
            + " WHERE (c.id_profesor=params.idProfesor )\n"
            + " AND(params.fechaDesde IS NULL OR cu.fecha_inicio >= params.fechaDesde)\n"
            + " AND(params.fechaHasta IS NULL OR cu.fecha_fin <= params.fechaHasta) \n"
            + " ", nativeQuery = true)
    public List<Map> pasarLista(Long idProfesor);

    @Query(value = "SELECT dr.id, dr.descripcion,dr.dias_plazo,dr.fecha_actualizacion,dr.obligatorio,dr.tipo_de_documento\n"
            + "FROM doc_documentos_requeridos dr\n"
            + "INNER JOIN doc_cursos dc ON (dc.id = dr.id_curso)\n"
            + "WHERE dr.id_curso = :idCurso", nativeQuery = true)
    public List<Map> alumnoShowDocumentoRequeridos(Long idCurso);

    @Modifying
    @Query(value = "INSERT INTO doc_matriculas(id_curso,id_alumno,estado)VALUES(:idCurso,:idAlumno,'preinscripcion')", nativeQuery = true)
    public void registro(Long idCurso, Long idAlumno);
}
