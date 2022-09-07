package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.DocumentoRequeridoEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDocumentoRequeridoRepository extends JpaRepository<DocumentoRequeridoEntity, Long> {

    public List<DocumentoRequeridoEntity> findByIdCurso(Long idDocumentoRequerido);
    
    public List<DocumentoRequeridoEntity> findByTipoDeDocumentoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String filtro1, String filtro2);
    
    //public List<Map> adminFormDocumentos(Long id_alumno);
 
    @Query(value = "SELECT al.id, al.nombre, al.apellido1, al.apellido2, al.telefono\n"
            + "FROM doc_alumnos al\n"
            + "\n"
            + "INNER JOIN (SELECT CONCAT('%', :filtro, '%') filtro )params\n"
            + "WHERE (al.nombre LIKE params.filtro OR al.apellido1 LIKE params.filtro OR al.apellido2 LIKE params.filtro OR al.telefono LIKE params.filtro )\n"
            + "", nativeQuery = true)
    public List<Map> adminList(String filtro);
    

}
