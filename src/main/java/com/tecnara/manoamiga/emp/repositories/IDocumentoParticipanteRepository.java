
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.TecnicoEntity;

import com.tecnara.manoamiga.emp.entities.DocumentoParticipanteEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository ("emp_documento_participante")
public interface IDocumentoParticipanteRepository extends JpaRepository<DocumentoParticipanteEntity, Long> {

    public List<DocumentoParticipanteEntity> findByIdParticipante(Long idParticipante);

   @Query(value = "SELECT dp.id,dp.fecha_subido,dp.id_documento,dp.id_participante,dp.nombre_documento\n" 
          +"FROM emp_documentos_participantes dp\n" 
          +"INNER JOIN (SELECT :idParticipante id_participante)params\n" 
          + "WHERE dp.id_participante=params.id_participante ", nativeQuery = true)
    public List<Map> tecnicoFormDocumentosParticipantes(Long idParticipante);
    
}


