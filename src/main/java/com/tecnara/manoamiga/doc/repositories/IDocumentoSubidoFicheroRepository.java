package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.DocumentoSubidoEntity;
import com.tecnara.manoamiga.doc.entities.DocumentoSubidoFicheroEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDocumentoSubidoFicheroRepository extends JpaRepository<DocumentoSubidoFicheroEntity, Long> {

    public List<DocumentoSubidoFicheroEntity> findByIdDocumentoSubido(Long id);

    @Query(value = "SELECT DISTINCT REPLACE(CONCAT(IFNULL(apellido1,''), '_', IFNULL(apellido2,''), '_', IFNULL(nombre,''), '_', IFNULL(documento,'')) , ' ', '') nombre\n"
            + "FROM doc_documentos_subidos ds\n"
            + "INNER JOIN doc_alumnos al ON (ds.id_alumno = al.id)\n"
            + "INNER JOIN (SELECT :idDocumentoSubido id_documento_subido) params\n"
            + "WHERE ds.id = params.id_documento_subido", nativeQuery = true)
    public String adminDescargar(Long idDocumentoSubido);

}
