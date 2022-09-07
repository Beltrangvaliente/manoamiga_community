/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.FaltaAsistenciaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tecnara
 */
public interface IFaltaAsistenciaRepository  extends JpaRepository<FaltaAsistenciaEntity, Long>{
    
    public List<FaltaAsistenciaEntity> findByIdMonitor(Long idMonitor);
    public List<FaltaAsistenciaEntity> findByIdProfesor (Long idProfesor);
    public List<FaltaAsistenciaEntity> findByIdActividad (Long idActividad);
    
    public List<FaltaAsistenciaEntity> findByIdMonitorAndIdBeneficiario(Long idMonitor, Long idBeneficiario);
    public List<FaltaAsistenciaEntity> findByIdBeneficiarioOrderByFechaFaltaAsistenciaDesc(Long idBeneficiario);

    public List<FaltaAsistenciaEntity> findByIdActividadOrderByFechaFaltaAsistenciaDesc(Long idActividad);
}
