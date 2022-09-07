/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.aaa.config;

import com.tecnara.manoamiga.aaa.services.IGeneral;
import com.tecnara.manoamiga.aaa.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Value(value = "${tecnara.acceso-total}")
    private String accesoTotal;
    
    @Autowired
    private SecurityService securityService;

    @Autowired
    private IGeneral general;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); 
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        if ("si".equalsIgnoreCase(accesoTotal)){
            http.authorizeHttpRequests().antMatchers("/**").permitAll();
        }else{
            http.authorizeHttpRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/act/*").permitAll()
                .antMatchers("/act/admin/**").hasAnyRole("ACT_Admin")
                .antMatchers("/act/beneficiario/**").hasAnyRole("ACT_Beneficiario")
                .antMatchers("/act/monitor/**").hasAnyRole("ACT_Monitor")
                .antMatchers("/act/profesor/**").hasAnyRole("ACT_Profesor")
                .antMatchers("/act/tutor/**").hasAnyRole("ACT_Tutor")
                .antMatchers("/act/public/**").permitAll()
                .antMatchers("/doc/*").permitAll() 
                .antMatchers("/doc/admin/**").hasAnyRole("DOC_Admin")
                .antMatchers("/doc/alumno/**").hasAnyRole("DOC_Alumno")
                .antMatchers("/doc/profesor/**").hasAnyRole("DOC_Profesor")
                .antMatchers("/doc/public/**").permitAll()
                .antMatchers("/emp/*").permitAll()
                .antMatchers("/emp/tecnico/**").hasAnyRole("EMP_Tecnico")
                .antMatchers("/emp/trabajador/**").hasAnyRole("EMP_Trabajador")
                .antMatchers("/emp/public/**").permitAll()
                .antMatchers("/expo/*").permitAll()
                .antMatchers("/expo/coordinador/**").hasAnyRole("EXPO_Coordinador")
                .antMatchers("/expo/trabajador/**").hasAnyRole("EXPO_Trabajador")
                .antMatchers("/expo/public/**").permitAll()
            .and().rememberMe().key("recordar_usuario");
            //.and()
            //    .exceptionHandling().accessDeniedPage("/error/403")
        }
        String urlLogin=general.getPreferencia("LOGIN_PAGE");
        if (general.getModulesInstalled()==1){
            if (general.isModule("expo")){
                urlLogin="/expo/public/usuario/login";
            }
            if (general.isModule("act")){
                urlLogin="/act/public/usuario/login";
            }
            if (general.isModule("emp")){
                urlLogin="/emp/public/usuario/login";
            }
            if (general.isModule("doc")){
                urlLogin="/doc/public/usuario/login";
            }
        }
        if (urlLogin!=""){
            http.formLogin().loginPage(urlLogin).loginProcessingUrl(urlLogin).and().logout();
        }else{
            http.formLogin().and().logout();
        }
    }
    
    
    
}
