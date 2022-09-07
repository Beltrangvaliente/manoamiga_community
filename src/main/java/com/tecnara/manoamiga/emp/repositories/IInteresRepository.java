
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.InteresEntity;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_formacion_interes")

public interface IInteresRepository extends JpaRepository<InteresEntity, Long> {
    
       
   //@Query(value ="", nativeQuery = true) 

   // public List<Map> tecnicoFormIntereses(idParticipante);
    
}
