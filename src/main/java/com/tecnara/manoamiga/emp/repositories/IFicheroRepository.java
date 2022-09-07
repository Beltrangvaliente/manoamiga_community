
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.FicheroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("emp_fichero")
public interface IFicheroRepository extends JpaRepository<FicheroEntity, Long> {

    public com.tecnara.manoamiga.doc.entities.FicheroEntity save(com.tecnara.manoamiga.doc.entities.FicheroEntity nueva);
    
}



    

