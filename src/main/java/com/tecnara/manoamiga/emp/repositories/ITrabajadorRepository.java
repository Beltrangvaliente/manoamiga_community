/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.TrabajadorEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_trabajador")
public interface ITrabajadorRepository extends JpaRepository<TrabajadorEntity, Long> {

    public List<TrabajadorEntity>findByIdUsuario(Long idUsuario); 
    public List<com.tecnara.manoamiga.emp.entities.TrabajadorEntity> findByNombreContainingOrApellido1ContainingOrApellido2Containing(String nombre, String apellido1, String apellido2);

    @Query(value = "SELECT t.*\n"
            + "FROM emp_trabajadores t\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE t.nombre LIKE params.filtro OR t.apellido1 LIKE params.filtro OR t.apellido2 LIKE params.filtro \n"
            + "ORDER BY t.nombre, t.apellido1, t.apellido2"
            + "             ", nativeQuery = true)

    public List<Map> tecnicoList(String filtro);

    @Query(value = "SELECT u.* \n"
            + "FROM aaa_usuarios u\n"
            + "LEFT JOIN emp_trabajadores tra ON (u.id=tra.id_usuario)\n"
            + "INNER JOIN (SELECT CONCAT('%',:filtro,'%') filtro) params\n"
            + "WHERE rol='ROLE_EMP_Trabajador'\n"
            + "AND (tra.id IS NULL OR tra.id = :id )\n"
            + "AND (u.usuario LIKE params.filtro)", nativeQuery = true)

    public List<Map> tecnicoFromBuscador(String filtro, Long id);

  //  @Query(value = "SELECT t.nombre,ht.fecha,ht.hora_inicio,ht.hora_fin,ht.tipo_registro,ht.tiempo_minutos\n"
    //        + "FROM emp_horarios_trabajados ht\n"
      //      + "INNER JOIN emp_trabajadores t ON(ht.id_trabajador=t.id)  ", nativeQuery = true)

  // public List<Map> trabajadorHorasTrabajadas(String filtro, Long id);
}
