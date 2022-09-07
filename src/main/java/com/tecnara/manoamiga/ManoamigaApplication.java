package com.tecnara.manoamiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManoamigaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManoamigaApplication.class, args);
	}
        
        /*@Bean
        public ServletWebServerFactory servletContainer(){
            TomcatServletWebServerFactory tomcat= postProcessContext(context)-> {
            
            }
        }*/
 
}
