 
package com.tecnara.manoamiga.aaa.repositories;

import com.tecnara.manoamiga.aaa.entities.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

 
public interface IEmailRepository extends JpaRepository<EmailEntity, Long> {
    
}
