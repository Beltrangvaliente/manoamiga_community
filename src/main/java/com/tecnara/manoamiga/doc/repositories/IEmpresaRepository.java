package com.tecnara.manoamiga.doc.repositories;

import com.tecnara.manoamiga.doc.entities.EmpresaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEmpresaRepository extends JpaRepository<EmpresaEntity, Long>{
    public List<EmpresaEntity> findByNombreContainingIgnoreCase( String nombre);
}
