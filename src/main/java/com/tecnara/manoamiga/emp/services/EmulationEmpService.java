/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tecnara.manoamiga.emp.services;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.emp.entities.EmpresaEntity;
import com.tecnara.manoamiga.emp.entities.FormacionEntity;
import com.tecnara.manoamiga.emp.entities.ParticipanteEntity;
import com.tecnara.manoamiga.emp.entities.ProyectoEntity;
import com.tecnara.manoamiga.emp.entities.SectorEntity;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.IEmpresaRepository;
import com.tecnara.manoamiga.emp.repositories.IFicheroRepository;
import com.tecnara.manoamiga.emp.repositories.IFormacionRepository;
import com.tecnara.manoamiga.emp.repositories.IParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IProyectoParticipanteRepository;
import com.tecnara.manoamiga.emp.repositories.IProyectoRepository;
import com.tecnara.manoamiga.emp.repositories.ISectorRepository;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmulationEmpService implements IEmulationEmpService {

    @Autowired
    private IGeneral general;

    @Autowired
    private IEmpresaRepository repoEmpresa;

    @Autowired
    private IFicheroRepository repoFichero;

    @Autowired
    private IFormacionRepository repoFormación;

    @Autowired
    private IProyectoParticipanteRepository repoProyectoParticipante;

    @Autowired
    private IProyectoRepository repoProyecto;

    @Autowired
    private ISectorRepository repoSector;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @Autowired
    private IUsuarioRepository repoUsuario;
    
    @Autowired
    private IParticipanteRepository repoParticipante;

    private PasswordEncoder passEnc;
    
    @Override
    public void generarDatosDePrueba(PasswordEncoder passEnc) {
        this.passEnc=passEnc;
        try {
            if (repoProyecto.count() > 0) {
                return;
            }

            crear2Sectores();
            crear3Empresas();
            crear2Proyectos();
            crear3Formaciones();
            crear2Tecnicos();
            crear4Participantes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crear3Empresas() {
        EmpresaEntity empresa1 = new EmpresaEntity();
        empresa1.setId(1L);
        empresa1.setNombre("Tecnus");
        empresa1.setResponsable("Magdalena Gascón");
        empresa1.setTelefono("976883252");
        empresa1.setCodigoPostal("50003");
        empresa1.setDireccion("de la Cdad. de Soria, 8");
        empresa1.setEmail("Tecnus@Tecnus.com");
        empresa1.setCif("23245263");
        empresa1.setCiudad("Zaragoza");
        repoEmpresa.save(empresa1);

        EmpresaEntity empresa2 = new EmpresaEntity();
        empresa2.setId(2L);
        empresa2.setNombre("Telecom");
        empresa2.setResponsable("Yolanda Beltran");
        empresa2.setTelefono("976757542");
        empresa2.setCodigoPostal("50008");
        empresa2.setDireccion("Av/ Bolonia");
        empresa2.setEmail("Telecom@gmail.com");
        empresa2.setCif("52320034");
        empresa2.setCiudad("Huesca");
        repoEmpresa.save(empresa2);
        
        EmpresaEntity empresa3 = new EmpresaEntity();
        empresa3.setId(3L);
        empresa3.setNombre("Universidad San Jorge");
        empresa3.setResponsable("Bernat Corredor");
        empresa3.setTelefono("976067500");
        empresa3.setCodigoPostal("50830");
        empresa3.setDireccion("Autovía Mudéjar, km. 299");
        empresa3.setEmail("Sanjorge@gmail.com");
        empresa3.setCif("88523263");
        empresa3.setCiudad("Villanueva de Gállego");
        repoEmpresa.save(empresa3);
        
    }

    private void crear2Sectores() {
        
        SectorEntity sector1 = new SectorEntity();
        sector1.setId(1L);
        sector1.setNombre("TIC");
        repoSector.save(sector1);

        SectorEntity sector2 = new SectorEntity();
        sector1.setId(2L);
        sector2.setNombre("Artes graficas");
        repoSector.save(sector2);

    }

    private void crear2Proyectos() {
        ProyectoEntity proyecto1 = new ProyectoEntity();
        proyecto1.setId(1L);
        proyecto1.setEstado("Activo");
        proyecto1.setNombre("Proyecto Industrial Learning IVS");
        proyecto1.setIdEmpresa(1L);
        repoProyecto.save(proyecto1);

        ProyectoEntity proyecto2 = new ProyectoEntity();
        proyecto2.setId(2L);
        proyecto2.setEstado("Finalizado");
        proyecto2.setNombre("Proyecto AION");
        proyecto2.setIdEmpresa(2L);
        repoProyecto.save(proyecto2);

    }

    private void crear3Formaciones() {
        FormacionEntity formacion1 = new FormacionEntity();
        formacion1.setId(1L);
        formacion1.setNombre("Curso Digital Transformer");
        formacion1.setEstado("Activo");
        formacion1.setIdProyecto(2L);
        repoFormación.save(formacion1);

        FormacionEntity formacion2 = new FormacionEntity();
        formacion2.setId(2L);
        formacion2.setNombre("Curso web con Java");
        formacion2.setEstado("Activo");
        formacion2.setIdProyecto(1L);
        repoFormación.save(formacion2);

        FormacionEntity formacion3 = new FormacionEntity();
        formacion3.setId(3L);
        formacion3.setNombre("Curso web con .NET y HTML5");
        formacion3.setEstado("Finalizado");
        formacion3.setIdProyecto(2L);
        repoFormación.save(formacion3);

    }

    
    private void crear2Tecnicos() {
        UsuarioEntity user=new UsuarioEntity();
        user.setUsuario("tecnico");
        user.setPassword(passEnc.encode("1111"));
        user.setRol("ROLE_EMP_Tecnico");
        user.setEstado("Aceptado");
        user.setCodigoVerificacion(UUID.randomUUID().toString());;
        user=repoUsuario.save(user);
        TecnicoEntity tecnico1 = new TecnicoEntity();
        tecnico1.setIdUsuario(user.getId());
        tecnico1.setNombre("Alfredo");
        tecnico1.setApellidos("Pérez Miranda");
        tecnico1.setEmail("alpem@gmail.com");
        tecnico1.setTelefono("657894274");
        repoTecnico.save(tecnico1);

        TecnicoEntity tecnico2 = new TecnicoEntity();
        tecnico2.setId(2L);
        tecnico2.setNombre("Celestino ");
        tecnico2.setApellidos("Chaves Sánchez");
        tecnico2.setEmail("cechas@gmail.com");
        tecnico2.setTelefono("657994272");
        repoTecnico.save(tecnico2);

    }

    private void crear4Participantes() {
        ParticipanteEntity participante1 = new ParticipanteEntity();
        participante1.setId(1L);
        participante1.setNombre("Carmen");
        participante1.setApellido1("Chacón");
        participante1.setApellido2("Bermejo");
        participante1.setTelefono("976581622");
        participante1.setEmail("cachabe@gmail.es");
        participante1.setEstado("aceptado");
        repoParticipante.save(participante1);
        
        ParticipanteEntity participante2 = new ParticipanteEntity();
        participante2.setId(2L);
        participante2.setNombre("Elena");
        participante2.setApellido1("Salgado");
        participante2.setApellido2(" ");
        participante2.setTelefono("976518262");
        participante2.setEmail("elensa10@gmail.es");
        participante2.setEstado("Pendiente");
        repoParticipante.save(participante2);
        
        ParticipanteEntity participante3 = new ParticipanteEntity();
        participante3.setId(3L);
        participante3.setNombre("César Antonio");
        participante3.setApellido1("Sierra");
        participante3.setApellido2("Cabrera");
        participante3.setTelefono("975658162");
        participante3.setEmail("casca30@gmail.com");
        participante3.setEstado("aceptado");
        repoParticipante.save(participante3);
        
        ParticipanteEntity participante4 = new ParticipanteEntity();
        participante4.setId(4L);
        participante4.setNombre("Angel");
        participante4.setApellido1("Arza");
        participante4.setApellido2("Moratino");
        participante4.setTelefono("976562283");
        participante4.setEmail("aamora8@yahoo.es");
        participante4.setEstado("aceptado");
        repoParticipante.save(participante4);     
       
        
    }

}
