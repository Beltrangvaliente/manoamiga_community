package com.tecnara.manoamiga.emp.repositories;

/*
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.OfertaTrabajoEntity;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("emp_oferta_trabajo_empresa_repository")
public interface IOfertaTrabajoEmpresaRepository extends JpaRepository<OfertaTrabajoEntity, Long> {

    @Query(value = "SELECT id, fecha_fin fechaFin, ot.fecha_inicio fechaInicio, ot.puesto puesto, ot.requisitos_candidato requisitosCandidato,ot.rango_salarial rangoSalarial, ot.vacantes vacantes\n"
            + "FROM emp_ofertas_trabajo ot\n"
            + "INNER JOIN (SELECT :idEmpresa id_empresa) params\n"
            + "WHERE ot.id_empresa=params.id_empresa", nativeQuery = true)
    public List<Map> ofertasTrabajoEmpresa(Long idEmpresa);



}
*/