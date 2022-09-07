/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnara.manoamiga.act.repositories;

import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProfesorRepository extends JpaRepository<ProfesorEntity,Long>{
    
    public  List<ProfesorEntity> findByIdUsuario(Long idUsuario);
    public List<ProfesorEntity> findByNombreContainingIgnoreCase(String nombre);
    
    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN act_profesores tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_ACT_Profesor'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)

    public List<Map> adminFromBuscador(String filtro, Long id);    
    public List<ProfesorEntity> findByNombreContainingIgnoreCaseOrApellido1ContainingOrApellido2Containing(String filtro, String filtro0, String filtro1);    

}
