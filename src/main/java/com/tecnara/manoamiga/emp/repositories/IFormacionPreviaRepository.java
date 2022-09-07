
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.FormacionPreviaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_formacion_previa")
public interface IFormacionPreviaRepository extends JpaRepository<FormacionPreviaEntity, Long> {

   
     
  // @Query(value ="", nativeQuery = true) 

    //public List<Map> tecnicoFormFormacionPrevia(idParticipante);
    
}
