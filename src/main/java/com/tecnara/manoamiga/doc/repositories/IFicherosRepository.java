
package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.FicheroEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IFicherosRepository extends JpaRepository<FicheroEntity, Long>{
    
}
