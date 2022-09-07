package com.tecnara.manoamiga.aaa.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/aaa/check")
public class CheckController {

/*
    @Autowired
    private com.google.code.kaptcha.impl.DefaultKaptcha captchaProducer;
  */  
    @Autowired
    private ApplicationContext appContext; 

    @GetMapping("/check_sql")
    @ResponseBody
    public String checkSql() {
        List<String> errores = new ArrayList<>();
        int verificadosOk = 0;
        String lista[] = appContext.getBeanDefinitionNames();
        for (String clase : lista) {
            if (clase.endsWith("Repository")) {
                Object o = appContext.getBean(clase);
                Method methods[] = o.getClass().getMethods();
                for (Method m : methods) {
                    String name = m.getName().toLowerCase();
                    if (name.contains("list") || name.contains("form") || name.contains("buscador")
                            || name.contains("public") || name.contains("admin") || name.contains("alumno") || name.contains("profesor")
                            || name.contains("monitor") || name.contains("tutor") || name.contains("beneficiario") || name.contains("coordinador")
                            || name.contains("trabajador") || name.contains("containing") || name.contains("scheduled")) {
                        System.out.println(clase + "." + m.getName());
                        String strParams = "";
                        try {
                            if (m.getParameterCount() == 0) {
                                m.invoke(o);
                            } else {
                                Class params[] = m.getParameterTypes();
                                Object p[] = new Object[params.length];
                                for (int n = 0; n < p.length; n++) {
                                    if (params[n].getName().toLowerCase().endsWith("long")) {
                                        p[n] = 1L;
                                        strParams += ",1L";
                                    } else if (params[n].getName().toLowerCase().endsWith("string")) {
                                        p[n] = "a";
                                        strParams += ",\"a\"";
                                    } else if (params[n].getName().toLowerCase().endsWith("date")) {
                                        p[n] = new Date();
                                        strParams += ",new Date()";
                                    } else {
                                        p[n] = null;
                                        strParams += ",null " + params[n].getName();
                                    }
                                }
                                m.invoke(o, p);
                            }
                            errores.add("Ok->" + clase + "." + m.getName() + "(" + strParams.substring(1) + ")");
                        } catch (Exception e) {
                            errores.add("<span style='color:red'>Error->" + clase + "." + m.getName() + "(" + (strParams.length() > 0 ? strParams.substring(1) : "") + ")</span>");
                        }
                    }
                }
            }
        }
        return errores.stream().reduce((x, y) -> x + "\n<br/>" + y).orElse("No hay errores");
    }

    /*
    @Autowired
    private com.google.code.kaptcha.impl.DefaultKaptcha captchaProducer;

    @GetMapping("/kaptcha_form")
    public String kaptchForm(){
        return "aaa/captcha";
    }

    /*
    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            // Producir una cadena de código de verificación y guardarla en la sesión
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    
    @GetMapping("/verify")
    @ResponseBody
    public String verify(@RequestParam("code") String code, HttpSession session) {
        if (StringUtils.isEmpty(code)) {
            return "El código de verificación debe ser lleno";
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode) || !code.equals(kaptchaCode)) {
            return "Error de código de verificación";
        }
        return "Éxito de verificación";
    }
*/
}
