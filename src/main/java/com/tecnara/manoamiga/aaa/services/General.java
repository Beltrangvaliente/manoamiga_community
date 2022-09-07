package com.tecnara.manoamiga.aaa.services;

import com.tecnara.manoamiga.aaa.config.SessionInfo;
import com.tecnara.manoamiga.aaa.entities.PreferenciaEntity;
import com.tecnara.manoamiga.aaa.repositories.IPreferenciaRepository;
import com.tecnara.manoamiga.aaa.services.adapters.UserServiceAdapter;
import com.tecnara.manoamiga.doc.repositories.INotificacionRepository;
import com.tecnara.manoamiga.emp.entities.TecnicoEntity;
import com.tecnara.manoamiga.emp.repositories.ITecnicoRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service 
public class General implements IGeneral {

    @Value("${tecnara.modules}")
    private String modules;

    @Value("${tecnara.theme}")  //@Value -> application.properties.
    private String theme;

    @Autowired
    private SessionInfo sessionInfo;
    
    @Override
    public boolean isModule(String code) {
        return ((";" + modules.toLowerCase() + ";").contains(";" + code.toLowerCase() + ";"));
    }

    @Override
    public String getTheme() {
        if (sessionInfo.getTheme()!=null){
            return sessionInfo.getTheme();
        }
        return theme;
    }


    @Override
    public Long getIdUsuario() {
        Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj==null){
            return null;
        }else{
            if (obj instanceof UserServiceAdapter){
                return ((UserServiceAdapter) obj).getUsuario().getId();
            }
            return null;                
        }     
    }    
    
    @Override
    public String getUsername() {
        Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj==null){
            return "";
        }else{
            if (obj instanceof UserServiceAdapter){
                return ((UserServiceAdapter) obj).getUsername();
            }
            return "";                
        }
    }

    @Override
    public boolean isRole(String code) {
        return true;
    }

    @Override
    public Long getIdValidado() {
        Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj==null){
            return 1L;
        }else{
            if (obj instanceof UserServiceAdapter){
                long idUsuario=((UserServiceAdapter) obj).getIdValidado();
                return idUsuario;
            }
            return 1L;    
        }
        
    }

    @Override
    public Date getFechaActual() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date d=cal.getTime();
        return d;
    }
    

    @Override
    public String formatDate(Date d) {
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleformat.format(d);    
    }
    
    @Override
    public String formatDateSQL(Date d) {
        SimpleDateFormat simpleformat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleformat.format(d);    
    }

    
    @Override
    public String getHoraActual() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("HH:mm");
        return simpleformat.format(cal.getTime());    
    }

    @Override
    public String concat(Object... values) {
        String f="";
        for (int n=0;n<values.length;n++){
            if (values[n]!=null && false=="null".equals(values[n])){
                if (values[n] instanceof Date ){
                    f+=formatDate((Date)values[n]) + " ";
                }else{
                    f+=values[n] + " ";        
                }
                
            }
        }
        return f.trim();
    }

    @Override
    public String getDaysAgo(Date fecha) {
        Date hoy=getFechaActual();
         
        long diferencia = fecha.getTime() - hoy.getTime();
        long dias=(long)(diferencia*1.01/1000/60/60/24);
        String medida="días";
        String tiempo="Dentro de";
        if (dias<0){
            dias=-dias;
            tiempo="Hace";
        }
        if (dias>30){
            dias=dias/30;
            medida="meses";
            if (dias>12){
                dias=dias/12;
                medida="años";
            }
        }
        if (dias==1){
            if ("días".equals(medida)){
                medida = "día";
            }else if ("meses".equals(medida)){
                medida = "mes";
            }else if ("años".equals(medida)){
                medida = "año";
            }
        }
        if (dias==0){
            return "Hoy";
        }
        return concat(tiempo,dias+"",medida); 
    }

    @Override
    public Date getFechaCercana(int days) {
        Calendar c=Calendar.getInstance();
        c.setTime(getFechaActual());
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    @Override
    public String getFechaTitulo(Date fecha) { 
        int day=fecha.getDay();
        int date=fecha.getDate();
        String dias[]={"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        return dias[day] + ", día "+ date;
    }

    @Override
    public Date getPrimerDiaMes() {
        Date d=getFechaActual();
        d.setDate(1);
        return d;
    }

    @Override
    public Date getUltimoDiaMes() {
        Date d=getFechaActual();
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        Date m=c.getTime();
        return m;
    }

    @Override
    public Date getPrimerDiaMesAnterior() {
        Date d=getFechaActual();
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    @Override
    public Date getUltimoDiaMesAnterior() {
        Date d=getFechaActual();
        Calendar c=Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        Date m=c.getTime();
        return m;
    }

    
    
    @Override
    public String getDiaSemana(long diaSemana){
        String dias[]={"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        return dias[(int)diaSemana];
    }


    @Autowired
    private INotificacionRepository repoNotificaciones;

    @Override
    public Integer getNotificaciones() {
        return repoNotificaciones.alumnoNotificaciones( getIdValidado() );
    }
    
    @Autowired
    private IPreferenciaRepository repoPreferencia;
    

    @Override
    public String getPreferencia(String clave) {
        return getPreferencia(clave,"");
    }

    @Override
    public String getPreferenciaStr(String clave, String valorDefecto) {
        String obj=getPreferencia(clave);
        if (obj==""){
            return valorDefecto;
        }
        return obj;
    }

    @Override
    public Date getFechaCercana(String days) { 
        return getFechaCercana(Integer.parseInt(days));
    }

    @Override
    public Date addDays(Date fecha, int days) {
        Calendar c=Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE, days);
        return c.getTime();    }

    @Override
    public String getRole() {
        Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (obj==null){
            return "";
        }else{
            if (obj instanceof UserServiceAdapter){
                return ((UserServiceAdapter) obj).getRole();
            }
            return "";    
        }
    }

    @Override
    public int getModulesInstalled() {
        return (int)List.of(modules.trim().split(";"))
                    .stream()
                    .filter(x->x.length()>0)
                    .count();
    }

    @Override
    public String getPreferencia(String clave, String defecto) {
        Optional<PreferenciaEntity> pref=repoPreferencia.findByClave(clave);
        if (pref.isEmpty()){
            return defecto;
        }else{
            return pref.get().getValor();
        }
    }

    @Autowired
    private ITecnicoRepository repoTecnico;
    
    
    @Override
    public boolean superAdministrador() {
        try{
            TecnicoEntity tec= repoTecnico.findById(getIdValidado()).get();
            if ("Si".equals(tec.getSuperAdministrador())){
                 return true;
            }else if (tec.getSuperAdministrador()==null){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            return false;
        }
    }
      //Tu codigo
    

    
}
