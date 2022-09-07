
package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.DocumentoSolicitadoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IDocumentoSolicitadoRepository extends JpaRepository<DocumentoSolicitadoEntity, Long>{
     
    
    
    @Query(value = "SELECT dso.id, CONCAT(dre.tipo_de_documento, '-', dcu.nombre_curso) descripcion\n" 
            + "FROM doc_documentos_solicitados dso\n" 
            + "LEFT JOIN doc_documentos_requeridos dre ON (dre.id = dso.id_documento_requerido)\n" 
            + "INNER JOIN doc_matriculas dm ON (dm.id=dso.id_matricula) \n" 
            + "INNER JOIN doc_cursos dcu ON (dm.id_curso=dcu.id) \n" 
            + "INNER JOIN(SELECT CONCAT('%',:filtro,'%') filtro, :idAlumno id_alumno) params\n" 
            + "WHERE (dre.tipo_de_documento LIKE filtro OR dcu.nombre_curso LIKE params.filtro )\n" 
            + "AND (dso.id_alumno = params.id_alumno) ", nativeQuery = true)
    public List<Map> alumnoBuscador(String filtro, Long idAlumno);

 
@Query(value = "SELECT CONCAT(dre.tipo_de_documento, '-', dcu.nombre_curso) descripcion\n" 
            + "FROM doc_documentos_solicitados dso\n" 
            + "LEFT JOIN doc_documentos_requeridos dre ON (dre.id = dso.id_documento_requerido)\n" 
            + "INNER JOIN doc_matriculas dm ON (dm.id=dso.id_matricula) \n" 
            + "INNER JOIN doc_cursos dcu ON (dm.id_curso=dcu.id) \n" 
            + "INNER JOIN(SELECT :id id) params\n" 
            + "WHERE (dso.id = params.id) ", nativeQuery = true)
    public Optional<Map> alumnoBuscadorId(Long id);
    
@Query(value = "SELECT al.mail, CONCAT(al.nombre,' ',al.apellido1,' ',al.apellido2) persona, \n"
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
    public List<Map> scheduledDocumentosFechaLimite();

  
  
    
    
}
