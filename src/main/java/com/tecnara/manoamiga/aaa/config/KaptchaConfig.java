/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.aaa.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
@Component
public class KaptchaConfig {
    @Bean
    public com.google.code.kaptcha.impl.DefaultKaptcha getDefaultKaptcha(){
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        // borde de imagen
        properties.put("kaptcha.border", "no");
        // color de fuente
        properties.put("kaptcha.textproducer.font.color", "black");
        // foto de ancho
        properties.put("kaptcha.image.width", "160");
        // imagen alta
        properties.put("kaptcha.image.height", "40");
        // tamaño de fuente
        properties.put("kaptcha.textproducer.font.size", "30");
        // Longitud del código de verificación
        properties.put("kaptcha.textproducer.char.space", "5");
        // font
        properties.setProperty("kaptcha.textproducer.font.names", "Canción,  , Microsoft Ya");
        com.google.code.kaptcha.util.Config config = new com.google.code.kaptcha.util.Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
*/