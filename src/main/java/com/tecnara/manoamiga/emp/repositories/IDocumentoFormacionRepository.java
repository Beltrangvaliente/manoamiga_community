/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.DocumentoFormacionEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("emp_documento_formacion")
public interface IDocumentoFormacionRepository extends JpaRepository<DocumentoFormacionEntity, Long> {

    
}
