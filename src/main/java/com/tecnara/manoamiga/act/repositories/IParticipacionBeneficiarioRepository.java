
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.ParticipacionBeneficiarioEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IParticipacionBeneficiarioRepository  extends JpaRepository<ParticipacionBeneficiarioEntity, Long>{
    
    public List<ParticipacionBeneficiarioEntity> findByIdBeneficiario(Long idBeneficario);
    public List<ParticipacionBeneficiarioEntity> findByIdActividad (Long idActividad);
     
    
}
