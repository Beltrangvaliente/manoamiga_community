/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tecnara.manoamiga.aaa.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
@Controller
public class ErrorCustomController extends TecnaraBaseController implements ErrorController {
    
    @RequestMapping("/error")
    public String error(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "aaa/error_403";
            }else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "aaa/error_404";
            }
        }
        return "error";        
        
    }

}

*/