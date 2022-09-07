/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.aaa.services;

import com.tecnara.manoamiga.aaa.entities.UsuarioEntity;
import com.tecnara.manoamiga.aaa.repositories.IUsuarioRepository;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.act.entities.BeneficiarioEntity;
import com.tecnara.manoamiga.act.entities.MonitorEntity;
import com.tecnara.manoamiga.act.entities.ProfesorEntity;
import com.tecnara.manoamiga.act.entities.TutorEntity;
import com.tecnara.manoamiga.act.repositories.IBeneficiarioRepository;
import com.tecnara.manoamiga.act.repositories.IMonitorRepository;
import com.tecnara.manoamiga.act.repositories.IProfesorRepository;
import com.tecnara.manoamiga.act.repositories.ITutorRepository;
import com.tecnara.manoamiga.doc.entities.AlumnoEntity;
import com.tecnara.manoamiga.doc.repositories.IAlumnoRepository;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import com.tecnara.manoamiga.expo.entities.CoordinadorEntity;
import com.tecnara.manoamiga.expo.entities.TrabajadorEntity;
import com.tecnara.manoamiga.expo.repositories.ICoordinadorRepository;
import com.tecnara.manoamiga.expo.repositories.ITrabajadorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityService implements UserDetailsService  {

    @Autowired
    private IUsuarioRepository repoUsuario;
    
    @Autowired
    private IBeneficiarioRepository repoBeneficiario;

    @Autowired
    private IMonitorRepository repoMonitor;

    @Autowired
    private IProfesorRepository repoProfesor;

    @Autowired
    private ITutorRepository repoTutor;

    @Autowired
    private IAlumnoRepository repoAlumno;

    @Autowired
    private com.tecnara.manoamiga.doc.repositories.IProfesorRepository repoProfesorDoc;

    @Autowired
    private ICoordinadorRepository repoCoordinador;
    
    @Autowired
    private ITrabajadorRepository repoTrabajador;

    @Autowired
    private ITecnicoRepository repoTecnico;

    @Autowired
    private com.tecnara.manoamiga.emp.repositories.ITrabajadorRepository repoTrabajadorEmp;
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuario=repoUsuario.findByUsuario(username);
        if (usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        UserServiceAdapter userDetail=new UserServiceAdapter();
        userDetail.setUsuario(usuario.get());
        
        String rol=usuario.get().getRol();
        Long id=usuario.get().getId();
        
        switch(rol){
            case "ROLE_ACT_Beneficiario":
                List<BeneficiarioEntity> ben=repoBeneficiario.findByIdUsuario(id);
                if (ben.size()>0){
                    userDetail.setIdValidado(ben.get(0).getId());
                }
                break;
            case "ROLE_ACT_Monitor":
                List<MonitorEntity> mon=repoMonitor.findByIdUsuario(id);
                if (mon.size()>0){
                    userDetail.setIdValidado(mon.get(0).getId());
                }
                break;
            case "ROLE_ACT_Profesor":
                List<ProfesorEntity> pro=repoProfesor.findByIdUsuario(id);
                if (pro.size()>0){
                    userDetail.setIdValidado(pro.get(0).getId());
                }
                break;
            case "ROLE_ACT_Tutor":
                List<TutorEntity> tut=repoTutor.findByidUsuario(id);
                if (tut.size()>0){
                    userDetail.setIdValidado(tut.get(0).getId());
                }
                break;
            case "ROLE_DOC_Alumno":
                List<AlumnoEntity> alu=repoAlumno.findByIdUsuario(id);
                if (alu.size()>0){
                    userDetail.setIdValidado(alu.get(0).getId());
                }
                break;
            case "ROLE_DOC_Profesor":
                List<com.tecnara.manoamiga.doc.entities.ProfesorEntity> pro2=repoProfesorDoc.findByIdUsuario(id);
                if (pro2.size()>0){
                    userDetail.setIdValidado(pro2.get(0).getId());
                }
                break;
            case "ROLE_EXPO_Coordinador":
                List<CoordinadorEntity> coo=repoCoordinador.findByIdUsuario(id);
                if (coo.size()>0){
                    userDetail.setIdValidado(coo.get(0).getId());
                }
                break;
            case "ROLE_EXPO_Trabajador":
                List<TrabajadorEntity> tra=repoTrabajador.findByIdUsuario(id);
                if (tra.size()>0){
                    userDetail.setIdValidado(tra.get(0).getId());
                }
                break;
            case "ROLE_EMP_Tecnico":
                List<TecnicoEntity> tec=repoTecnico.findByIdUsuario(id);
                if (tec.size()>0){
                    userDetail.setIdValidado(tec.get(0).getId());
                }
                break;
            case "ROLE_EMP_Trabajador":
                List<com.tecnara.manoamiga.emp.entities.TrabajadorEntity> traEmp=repoTrabajadorEmp.findByIdUsuario(id);
                if (traEmp.size()>0){
                    userDetail.setIdValidado(traEmp.get(0).getId());
                }
                break;                
        }
        
        return userDetail;
    }
    
}
