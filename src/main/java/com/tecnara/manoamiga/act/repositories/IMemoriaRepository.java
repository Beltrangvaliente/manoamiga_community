/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.MemoriaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tecnara
 */
public interface IMemoriaRepository extends JpaRepository<MemoriaEntity, Long> {
    
    public List<MemoriaEntity>findByIdBeneficiario(Long idBeneficiario);
    public List<MemoriaEntity>findByIdMonitor(Long idMonitor);
    public List<MemoriaEntity>findByIdMonitorAndIdBeneficiario(Long idMonitor, Long idBeneficiario);

    public List<MemoriaEntity> findByIdMonitorAndIdBeneficiarioOrderByFechaComentarioDesc(Long idValidado, Long idBeneficiario);

    public List<MemoriaEntity> findByIdBeneficiarioOrderByFechaComentarioDesc(Long idBeneficiario);
}
