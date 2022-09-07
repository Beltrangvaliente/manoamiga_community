/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.expo.repositories;

import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITrabajadorRepository extends JpaRepository<TrabajadorEntity, Long>{
    
    public List<TrabajadorEntity>findByIdUsuario(Long idUsuario); 
    public List<TrabajadorEntity>findByIdArea(Long idArea);
    public List<TrabajadorEntity>findByNombreTrabajadorContainingIgnoreCase(String nombreTrabajador);
    
     @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN expo_trabajadores tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_EXPO_Trabajador'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro OR email LIKE params.filtro)", nativeQuery = true)

    public List<Map>coordinadorTrabajadorFromBuscador(String filtro, Long id);

    
    //public void trabajadorPendiente(Long idTrabajador);
}
