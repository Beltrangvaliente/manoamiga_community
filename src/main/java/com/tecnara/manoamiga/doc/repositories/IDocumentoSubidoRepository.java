package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author tecnara
 */
public interface IDocumentoSubidoRepository extends JpaRepository<DocumentoSubidoEntity, Long> {

    public List<DocumentoSubidoEntity> findByIdAlumno(Long idAlumno);

    public List<DocumentoSubidoEntity> findByVerificacionContainingIgnoreCase(String verificacion);

    public List<DocumentoSubidoEntity> findByIdAlumnoAndVerificacionContainingIgnoreCase(Long idAlumno, String estado);

    @Query(value = " SELECT su.id, CONCAT(a.nombre, ' ', a.apellido1, ' ', a.apellido2) alumno, CONCAT (IFNULL(re.tipo_de_documento,''),' ', IFNULL(re. descripcion,'')) documento, su.fecha_subida fechaDeSubida,\n" +
                    "   (select count(1) from doc_documentos_subidos_fichero dsf where dsf.id_documento_subido = su.id ) numero_documentos \n" +
"            FROM doc_alumnos a\n" +
"            INNER JOIN doc_documentos_subidos su ON (a.id= su.id_alumno)\n" +
"            LEFT JOIN doc_documentos_solicitados  so ON (so.id= su.id_documento_solicitado) \n" +
"            LEFT JOIN doc_documentos_requeridos re ON (re.id= so.id_documento_requerido)\n" +
"            INNER JOIN (SELECT CONCAT('%', :filtro, '%') filtro)params\n" +
"           \n" +
"            ORDER BY su.fecha_subida", nativeQuery = true)
    public List<Map> adminVerificacion(String filtro);

    @Query(value = "  SELECT CONCAT(al.nombre,' ',al.apellido1,' ',al.apellido2) persona, CONCAT(IFNULL(re.tipo_de_documento ,' '),' ',IFNULL( re.descripcion,'')) documento ,  s.fecha_limite fechaLimite, cu.nombre_curso\n" +
"                      FROM doc_alumnos al\n" +
"                      LEFT JOIN doc_documentos_subidos s ON(s.id_alumno=al.id)\n" +
"                      LEFT JOIN doc_documentos_solicitados ds ON(ds.id_alumno=s.id_alumno)\n" +
"                      LEFT JOIN doc_documentos_requeridos re ON(re.id=ds.id_documento_requerido)\n" +
"                      LEFT JOIN doc_cursos cu ON(cu.id=re.id_curso)\n" +
"                      INNER JOIN(SELECT CONCAT('%',:filtro,'%') filtro) params  \n" +
"		      WHERE (cu.nombre_curso LIKE params.filtro OR al.nombre LIKE params.filtro OR al.apellido1 LIKE params.filtro OR al.apellido2 LIKE params.filtro OR re.descripcion LIKE params.filtro OR re.tipo_de_documento LIKE params.filtro) \n" +
"                      ", nativeQuery = true)
    public List<Map> adminPlazoFinalizado(String filtro);

    @Query(value = "SELECT sub.id, req.tipo_de_documento tipoDocumento, sub.fecha_subida Fecha, cur.nombre_curso Curso, sub.verificacion Estado\n"
            + "FROM doc_alumnos alu\n"
            + "INNER JOIN doc_documentos_subidos sub ON(sub.id_alumno=alu.id)\n"
            + "INNER JOIN doc_documentos_solicitados sol ON(sol.id=sub.id_documento_solicitado)\n"
            + "INNER JOIN doc_documentos_requeridos req ON(req.id=sol.id_documento_requerido)\n"
            + "INNER JOIN doc_cursos cur ON(cur.id=req.id_curso)\n"
            + "INNER JOIN (SELECT :verificacion  verificacion,  :fecha_desde   fecha_desde, :fecha_hasta fecha_hasta)params\n"
            + "WHERE  (params.fecha_desde IS NULL OR sub.fecha_subida  >=params.fecha_desde) \n"
            + "AND (params.fecha_hasta IS NULL OR sub.fecha_subida  <=params.fecha_hasta) \n"
            + "AND (params.verificacion = '' OR sub.verificacion=params.verificacion)\n", nativeQuery = true)

    public List<Map> alumnoDocumentoSubidoList(String verificacion, Date fecha_desde, Date fecha_hasta);

    @Query(value = "SELECT  su.id, su.tipo_documento, re.descripcion descripcion, su.fecha_subida fechaSubida, su.verificacion estadoVerificacion, su.observaciones\n"
            + "FROM doc_documentos_subidos su\n"
            + "LEFT JOIN doc_documentos_solicitados so ON (su.id_documento_solicitado = so.id)\n"
            + "LEFT JOIN doc_documentos_requeridos re ON (so.id_documento_requerido = re.id)\n"
            + " \n"
            + "INNER JOIN (SELECT :idDocumentoSubido id_documento_subido, :idAlumno id_alumno) params\n"
            + "WHERE  params.id_documento_subido = su.id\n"
            + "AND params.id_alumno = su.id_alumno", nativeQuery = true)
    public Map alumnoShow(Long idDocumentoSubido, Long idAlumno);

    public Optional<DocumentoSubidoEntity> findByIdAndIdAlumnoAndVerificacionNot(Long id, Long idAlumno, String verificacion);
    
    @Query(value = "SELECT al.mail, CONCAT(al.nombre,' ',al.apellido1,' ',al.apellido2) persona,\n"
            + "	GROUP_CONCAT(CONCAT(IFNULL(re.tipo_de_documento ,' '),' ',IFNULL( re.descripcion,''),' para ', cu.nombre_curso )) documento,\n"
            + "	MAX(ds.fecha_limite) fechaLimite \n"
            + "	FROM doc_alumnos al\n"
            + "        LEFT JOIN doc_documentos_solicitados ds ON(ds.id_alumno=al.id)\n"
            + "        LEFT JOIN doc_documentos_subidos s ON(s.id_documento_solicitado=ds.id)\n"
            + "        LEFT JOIN doc_documentos_requeridos re ON(re.id=ds.id_documento_requerido)\n"
            + "        LEFT JOIN doc_cursos cu ON (cu.id=re.id_curso)\n"
            + "WHERE ds.fecha_limite = DATE_ADD(CURDATE(),INTERVAL 3 DAY)\n"
            + "AND s.id IS NULL\n"
            + "GROUP BY al.mail,persona", nativeQuery = true)
    public List<Map> scheduleddocumentosfechalimite();

    public Optional<DocumentoSubidoEntity> findByIdDocumentoSolicitadoAndIdAlumno(Long idDocumentoSolicitado, Long idAlumno);

}
