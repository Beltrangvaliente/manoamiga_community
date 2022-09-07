package com.tecnara.manoamiga.emp.repositories;

import com.tecnara.manoamiga.emp.entities.ContratoEntity;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_empresa")
public interface IEmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

    @Query(value = "SELECT emp.id, emp.cif Cif, emp.direccion Direccion, emp.email Email, emp.nombre Nombre, emp.responsable Responsable, emp.telefono Telefono, emp.ciudad Ciudad, emp.codigo_postal Codigo, emp.socio Socio\n"
            + "FROM emp_empresas emp\n"
            + "INNER JOIN(SELECT CONCAT('%', :filtro , '%') filtro, :idSector id_sector)params\n"
            + "WHERE (emp.nombre LIKE params.filtro OR emp.responsable LIKE params.filtro OR emp.cif LIKE params.filtro )\n"
            + "AND (params.id_sector IS NULL OR emp.id_sector = params.id_sector)", nativeQuery = true)
    public List<Map> empresaList(String filtro, Long idSector);

    @Query(value = "SELECT emp.id, emp.nombre nombre, emp.fecha_inicio fechaInicio, emp.fecha_fin fechaFin, emp. estado, emp.id_empresa\n"
            + "FROM emp_proyectos emp\n"
            + "INNER JOIN (SELECT :idEmpresa id_empresa)params\n"
            + "WHERE emp.id_empresa=params.id_empresa", nativeQuery = true)
    public List<Map> tecnicoFormProyectos(Long idEmpresa);

    @Query(value = "SELECT part.nombre, part.apellido1, part.apellido2, part.telefono, part.email, par.id_participante, emp.nombre nombre_empresa, pro.nombre nombre_proyecto \n"
            + "FROM emp_proyectos pro\n"
            + "INNER JOIN emp_empresas emp ON emp.id = pro.id_empresa\n"
            + "INNER JOIN emp_proyectos_participantes par ON pro.id = par.id_proyecto\n"
            + "INNER JOIN emp_participantes part ON part.id = par.id_participante\n"
            + "INNER JOIN (SELECT :idEmpresa id_empresa) param\n"
            + "WHERE (emp.id = param.id_empresa)", nativeQuery = true)
    public List<Map> tecnicoFormParticipantes(Long idEmpresa);

    public List<EmpresaEntity> findByNombreContaining(String filtro);

    @Query(value = "SELECT ce.id,ce.nombre, ce.telefono, ce.email, ce.cargo\n"
            + "FROM emp_contactos_empresas ce\n"
            + "INNER JOIN (SELECT :idEmpresa id_empresa)params\n"
            + "WHERE ce.id_empresa=params.id_empresa", nativeQuery = true)
    public List<Map> tecnicoFormContactosEmpresa(Long idEmpresa);
}
