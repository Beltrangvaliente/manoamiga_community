
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.ContactoEmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("emp_contacto_empresa")
public interface IContactoEmpresaRepository extends JpaRepository<ContactoEmpresaEntity, Long>{
    
}
