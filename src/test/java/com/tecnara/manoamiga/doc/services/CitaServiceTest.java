/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.tecnara.manoamiga.doc.services;

/*

import com.tecnara.manoamiga.doc.entities.CitaEntity;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CitaServiceTest {
    
    public CitaServiceTest() {
    }


    @Test
    public void testCreateHour() {
        System.out.println("createHour");
        Date d=new Date(2020-1900, 0, 1);
        Calendar day = Calendar.getInstance();
        day.setTime(d);
        String hora = "03:53";
        CitaService instance = new CitaService();
        Calendar result = instance.createHour(day, hora);
        assertEquals(result.get(Calendar.HOUR), 3);
        assertEquals(result.get(Calendar.MINUTE), 53);
    }

    @Test
    public void testGenerarCita() throws Exception {
        Date d1=new Date(2022-1900, 2, 7);
        Date d2=new Date(2022-1900, 2, 14);
        
        CitaService instance = new CitaService();
        List<CitaEntity> lista = instance.generarCita(1L, d1, d2, "12" , "10:00", "13:00", 90);

        assertEquals(lista.size(), 9);
        assertEquals(lista.get(0).getHoraDeCita(), "10:00");
        assertEquals(lista.get(1).getHoraDeCita(), "11:30");
        assertEquals(lista.get(2).getHoraDeCita(), "13:00");
        assertEquals(lista.get(3).getHoraDeCita(), "10:00");
        assertEquals(lista.get(4).getHoraDeCita(), "11:30");
        assertEquals(lista.get(5).getHoraDeCita(), "13:00");
        
    }

    @Test
    public void testGenerarAgenda() throws Exception {
    }
    
}
*/