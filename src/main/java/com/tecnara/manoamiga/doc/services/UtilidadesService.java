package com.tecnara.manoamiga.doc.services;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilidadesService implements IUtilidadesService {

    @Override
    public void guardarObjetoValoresNoNulos(JpaRepository repositorio, Object objeto) {
        Long id = null;
        try {
            //objeto.getClass().getMethod("getId", objeto.getClass());
            Method m = objeto.getClass().getMethod("getId");
            Object o=m.invoke(objeto);
            if (o != null){
                id=(Long)o;
            }
        } catch (Exception e) {
            System.out.println("Error importante: Debe implementarse el método getId en la clase " + objeto.getClass().getName() + "!!!!!");
            e.printStackTrace();
        }
        if (id != null) {
            Object registro = repositorio.getById(id);
            BeanUtils.copyProperties(objeto, registro, getNullPropertyNames(objeto));
            repositorio.save(registro);
            repositorio.flush();
        } else {
            repositorio.save(objeto);
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Override
    public Long getIdAlumnoValidado() {
        return 1L;
    }

    @Override
    public Long getIdProfesorValidado() { 
        return 1L;
    }

    @Override
    public Long getIdUsuarioValidado() {
        return 1L;
    }

    @Override
    public String formatearFecha(Date d) {
        if (d==null){
            return "";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    @Override
    public <T> T findById(JpaRepository repo, Long id, Class<T> type) {
        T objectEmpty=null;
        try{
            Class c=Class.forName(type.getName());
            objectEmpty=(T) c.newInstance();
        }catch(Exception e){
            return null;
        }

        if (id==null){
            return objectEmpty;
        }
        return (T)(repo.findById(id).orElse(objectEmpty));
    }

}
