package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.AlumnoNecesidadFormacionEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAlumnoRepository extends JpaRepository<AlumnoEntity, Long> {

    public List<AlumnoEntity> findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(String nombre, String apellido1, String apellido2);

    public List<AlumnoEntity> findByIdUsuario(Long idUsuario);

    @Query(value = "SELECT ma.id_alumno id, a.nombre, a.apellido1, a. apellido2, a.telefono, cu.nombre_curso nombreCurso\n"
            + "FROM doc_alumnos a\n"
            + "LEFT JOIN doc_matriculas ma ON(ma.id_alumno= a.id)\n"
            + "LEFT JOIN doc_cursos cu ON(ma.id_curso=cu. id)\n"
            + "LEFT JOIN doc_profesores_cursos pc ON(pc.id_curso=a.id)\n"
            + "\n"
            + "INNER JOIN(SELECT CONCAT('%',:filtro, '%') filtro, :idProfesor id_profesor )params\n"
            + "WHERE (a.nombre LIKE params.filtro OR a.apellido1 LIKE params.filtro OR a.apellido2 LIKE params.filtro OR cu.nombre_curso LIKE params.filtro)\n"
            + "AND pc.id_profesor = params.id_profesor", nativeQuery = true)
    public List<Map> profesorList(String filtro, Long idProfesor);

    @Query(value = "SELECT t.id, CONCAT(a.nombre, ' ', a.apellido1, ' ', a.apellido2) nombreAlumno, t.titulacion, t.nivel, t.duracion, t.homologacion, t.pais_expedicion paisExpedicion\n"
            + "FROM doc_titulos t\n"
            + "INNER JOIN doc_alumnos a ON (a.id = t.id_alumno) \n"
            + "\n"
            + "INNER JOIN(SELECT :idAlumno id_alumno) params\n"
            + "\n"
            + "WHERE a.id = params.id_alumno", nativeQuery = true)
    public List<Map> adminFormTitulos(Long idAlumno);

    @Query(value = "SELECT m.id id, m.fecha_matricula fechaMatricula,m.estado estado,c.nombre_curso nombreCurso,c.referencia referencia \n"
            + "FROM doc_matriculas m\n"
            + "INNER JOIN doc_cursos c ON(c.id=m.id_curso) \n"
            + "\n"
            + "INNER JOIN (SELECT :id_alumno id_alumno)params\n"
            + "WHERE m.id_alumno=params.id_alumno\n"
            + "ORDER BY m.fecha_matricula", nativeQuery = true)
    public List<Map> adminFormMatriculas(Long id_alumno);

    @Query(value = "SELECT dc.id, dc.estado, dc.fecha_de_cita fechaDeCita, dc.hora_de_cita horaDeCita, dca.nombre nombreCentroDeAtencion\n"
            + "FROM doc_citas dc\n"
            + "INNER JOIN doc_centros_atencion dca ON (dca. id = dc.id_centro_de_atencion)\n"
            + "INNER JOIN doc_alumnos da ON (da.id = dc.id_alumno)\n"
            + "\n"
            + "INNER JOIN (SELECT :idAlumno id_alumno) params --\n"
            + "WHERE dc.id_alumno=params.id_alumno", nativeQuery = true)
    public List<Map> adminFormCitas(Long idAlumno);

    @Query(value = "SELECT er.id, er.tipo_de_documento tipoDeDocumento,sf.nombre_fichero nombreFichero,er.obligatorio obligatorio, ds.verificacion,ds.observaciones \n" +
"                FROM doc_documentos_subidos ds \n" +
"               INNER JOIN doc_documentos_solicitados sol ON (sol.id=ds.id_documento_solicitado )\n" +
"                INNER JOIN doc_documentos_requeridos er ON (sol.id_documento_requerido =er.id)\n" +
"                INNER JOIN doc_documentos_subidos_fichero sf ON(sf.id_documento_subido=ds.id)\n" +
"               INNER JOIN (SELECT :id_alumno id_alumno) param\n" +
"                WHERE (ds.id_alumno=param.id_alumno)", nativeQuery = true)
    public List<Map> adminFormDocumentos(Long id_alumno);

    @Query(value = "SELECT al.id, al.nombre, al.apellido1, al.apellido2, al.telefono\n"
            + "FROM doc_alumnos al\n"
            + "\n"
            + "INNER JOIN (SELECT CONCAT('%', :filtro, '%') filtro )params\n"
            + "WHERE (al.nombre LIKE params.filtro OR al.apellido1 LIKE params.filtro OR al.apellido2 LIKE params.filtro OR al.telefono LIKE params.filtro )\n"
            + "", nativeQuery = true)
    public List<Map> adminList(String filtro);

    @Query(value = "SELECT DISTINCT al.* \n"
            + "FROM doc_alumnos al\n"
            + "INNER JOIN doc_matriculas ma ON (ma.id_alumno=al.id)\n"
            + "INNER JOIN doc_cursos cu ON (ma.id_curso=cu.id)\n"
            + "INNER JOIN doc_profesores_cursos pc ON (pc.id_profesor=al.id)\n"
            + "INNER JOIN(SELECT :idAlumno id_alumno, :idProfesor id_profesor) params \n"
            + "WHERE ma.id_alumno LIKE params.id_alumno "
            + " AND pc.id_profesor LIKE params.id_profesor", nativeQuery = true)

    public Optional<AlumnoEntity> profesorForm(Long idAlumno, Long idProfesor);

    @Query(value = "SELECT DISTINCT al.id_usuario idUsuario,al.nombre nombre, al.apellido1 apellido1, al.apellido2 apellido2,al.mail mail,al.telefono telefono \n"
            + "FROM doc_alumnos al\n"
            + "INNER JOIN doc_matriculas ma ON (ma.id_alumno=al.id)\n"
            + "INNER JOIN doc_cursos cu ON (ma.id_curso=cu.id)\n"
            + "INNER JOIN doc_profesores_cursos pc ON (pc.id_profesor=al.id)\n"
            + "INNER JOIN(SELECT :idAlumno id_alumno, :idProfesor id_profesor) params \n"
            + "WHERE ma.id_alumno LIKE params.id_alumno "
            + " AND pc.id_profesor LIKE params.id_profesor", nativeQuery = true)

    public Optional<Map> profesorShow(Long idAlumno, Long idProfesor);

    @Query(value = "SELECT DISTINCT sol.id, sol.id_alumno, cur.nombre_curso nombreCurso , sol.estado estado, req.tipo_de_documento tipoDeDocumento , sol.fecha_limite fecha_limite \n"
            + "            FROM doc_documentos_solicitados sol \n"
            + "            INNER JOIN doc_documentos_requeridos req ON( sol.id_documento_requerido = req.id) \n"
            + "            LEFT JOIN doc_cursos cur ON( req.id_curso = cur.id) \n"
            + "            LEFT JOIN doc_matriculas ma ON( ma.id_curso = sol.id)\n"
            + "            INNER JOIN (SELECT :idAlumno id_alumno)params\n"
            + "            WHERE sol.id_alumno=params.id_alumno\n"
            + "             ",
            nativeQuery = true)

    public List<Map> adminFormDocumentosSolicitados(Long idAlumno);

    @Query(value = "SELECT alum.id id_alumno, cur.id id_curso, alum.nombre, alum.apellido1, alum.apellido2,\n"
            + "	IFNULL((SELECT 'Si' FROM doc_asistencias asis WHERE asis.id_alumno=alum.id AND cur.id=asis.id_curso AND asis.fecha=CURDATE() LIMIT 1),'No') asistencia\n"
            + "FROM doc_alumnos alum\n"
            + "INNER JOIN doc_matriculas mat ON(alum.id=mat.id_alumno)\n"
            + "INNER JOIN doc_cursos cur ON(cur.id=mat.id_curso)\n"
            + "INNER JOIN doc_profesores_cursos dpc ON(dpc.id_curso= cur.id)\n"
            + "INNER JOIN (SELECT :idCurso id_curso, :idProfesor id_profesor)params\n"
            + "WHERE dpc.id_profesor = params.id_profesor \n"
            + "AND cur.id=params.id_curso ", nativeQuery = true)
    public List<Map> profesorPasarLista(Long idCurso, Long idProfesor);

    
    @Query(value = "SELECT cu.id \n"
            + " FROM doc_profesores_cursos c \n"
            + " LEFT JOIN doc_cursos cu ON (cu.id = c.id_curso)\n"
            + " INNER JOIN (SELECT :idProfesor idProfesor) params\n"
            + " WHERE (c.id_profesor=params.idProfesor )\n"
            + " AND(IFNULL(cu.fecha_inicio,'1900-01-01') <= CURDATE())\n"
            + " AND(IFNULL(cu.fecha_fin,'2200-01-01') >= CURDATE()) \n"
            + " LIMIT 1"
            + " ", nativeQuery = true)
    public Long profesorPasarListaCursoInicial (Long idProfesor);    
    
    @Query(value = "SELECT nf.id,anf.id_alumno,nf.nombre nombre, nf.idioma idioma, nf.edad edad\n"
            + "FROM doc_necesidades_formacion nf\n"
            + "LEFT JOIN doc_alumnos_necesidades_formacion anf ON(nf.id= anf.id_necesidad_formacion)\n"
            + "LEFT JOIN doc_alumnos a ON(a.id=anf.id_alumno)\n"
            + "INNER JOIN(SELECT :idAlumno idAlumno)param\n"
            + "WHERE anf.id_alumno=param.idAlumno", nativeQuery = true)
    public List<Map> adminFormNecesidadFormacion(Long idAlumno);    
    
}
