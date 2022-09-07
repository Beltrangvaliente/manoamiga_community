package com.tecnara.manoamiga.aaa.services;

import java.util.Date;


public interface IGeneral {  
 
    /**
     * Esto devuelve si estoy en un modulo
     * @param code es el código del módulo
     * @return verdadero si ese modulo está activado
     */
    public boolean isModule(String code);   //Devuelve si un módulo está activado: act,doc,expo
    public String getTheme();               //Devuelve el tema configurado
    public int getModulesInstalled();       //Devuelve cuantos modulos hay instalados
    
    public Long getIdValidado();            //Devuelve el Id de la persona que ha hecho login
    public Long getIdUsuario();             //Devuelve el Id del usuario. aaa_usuarios.

    public String getUsername();            //Devuelve el nombre del usuario que ha hecho login
    public boolean isRole(String code);     //Devuelve si la persona que ha validado es 
    public String getRole();                //Devuelve el rol del usuario que ha hecho login
    
    public boolean superAdministrador(); 
    
    public String getPreferencia(String clave);  //Configuración del sistema.
    public String getPreferencia(String clave, String defecto);  //Configuración del sistema. Devuelve un valor por defecto si no está configurado.
    
    public Date getFechaActual();           //Devuelve la fecha de hoy
    public String getHoraActual();          //Devuelve la hora actual
    public String getDaysAgo(Date fecha);   //Devuelve un texto que dice hace 5 dias
    public Date getFechaCercana(int days);  //Devuelve la fecha desplazando los días indicados (-1=ayer, 1=mañana, etc)
    public Date getFechaCercana(String days);  //Devuelve la fecha desplazando los días indicados (-1=ayer, 1=mañana, etc)
    public String formatDate(Date d);       //Muestra la fecha formateada.
    public String formatDateSQL(Date d);
    
    public String concat(Object ... values);    //Sirve para concatenar los valores con un espacio en blanco
    public String getFechaTitulo(Date fecha);   //Devuelve un título con la fecha
    public Date getPrimerDiaMes();          //Devuelve el primer día del mes
    public Date getUltimoDiaMes();          //Devuelve el último día del mes
    public Date getPrimerDiaMesAnterior();          //Devuelve el primer día del mes
    public Date getUltimoDiaMesAnterior();          //Devuelve el último día del mes
    
    
    public String getDiaSemana(long diaSemana); //Devuelve un texto con el día de la semana.
    
    public Integer getNotificaciones();

    public String getPreferenciaStr(String clave, String valorDefecto);

    public Date addDays(Date fechaActividad, int i);
    
}
