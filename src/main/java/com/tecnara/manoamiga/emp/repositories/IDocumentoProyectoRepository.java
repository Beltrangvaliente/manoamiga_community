
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.DocumentoProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("emp_documento_proyecto")
public interface IDocumentoProyectoRepository extends JpaRepository<DocumentoProyectoEntity, Long>{
    
}
