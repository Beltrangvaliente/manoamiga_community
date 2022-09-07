package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.ClaseFormativaEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClaseFormativaRepository
        extends JpaRepository<ClaseFormativaEntity, Long> {

    public List<ClaseFormativaEntity> findByIdBeneficiario(Long idBeneficiario);

    public List<ClaseFormativaEntity> findByIdProfesor(Long idProfesor);



}
