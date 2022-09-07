/*

 */
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.EvaluacionBeneficiarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEvaluacionBeneficiarioRepository extends JpaRepository<EvaluacionBeneficiarioEntity, Long>{
    public List<EvaluacionBeneficiarioEntity> findByIdBeneficiario(Long idBeneficiario);
    public List<EvaluacionBeneficiarioEntity> findByIdProfesor (Long idProfesor);
    public List<EvaluacionBeneficiarioEntity> findByIdMonitor (Long idMonitor);
    public List<EvaluacionBeneficiarioEntity> findByIdActividad(Long idActividad);
    //public List<EvaluacionBeneficiarioEntity> findByCamposContainingIgnoreCase(String actividad, String beneficiario);
    
    public List<EvaluacionBeneficiarioEntity> findByIdBeneficiarioOrderByFechaDeEvaluacionDesc(Long idBeneficiario);
}
