package com.tecnara.manoamiga.expo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expo_plantillas")

public class PlantillaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String colorFondoGeneral;
    private Integer img1ColIni;
    private Integer img1ColFin;
    private Integer img1FilIni;
    private Integer img1FilFin;
    private Integer img2ColIni;
    private Integer img2ColFin;
    private Integer img2FilIni;
    private Integer img2FilFin;
    private Integer img3ColIni;
    private Integer img3ColFin;
    private Integer img3FilIni;
    private Integer img3FilFin;
    private Integer texto1ColIni;
    private Integer texto1ColFin;
    private Integer texto1FilIni;
    private Integer texto1FilFin;
    private String texto1ColorFondo; 
    private String texto1LetraFamilia;
    private Integer texto1LetraTamanyo;
    private String texto1LetraColor;
    private Integer texto2ColIni;
    private Integer texto2ColFin;
    private Integer texto2FilIni;
    private Integer texto2FilFin;
    private String texto2LetraFamilia;
    private Integer texto2LetraTamanyo;
    private String texto2LetraColor;
    private String texto2ColorFondo; 
    private Integer texto3ColIni;
    private Integer texto3ColFin ;
    private Integer texto3FilIni ;
    private Integer texto3FilFin;
    private String texto3LetraFamilia;
    private Integer texto3LetraTamanyo ;
    private String  texto3LetraColor;
    private String texto3ColorFondo; 
    private Integer numeroTextos;
    private Integer numeroImagenes;

    public Integer getNumeroTextos() {
        return numeroTextos;
    }

    public void setNumeroTextos(Integer numeroTextos) {
        this.numeroTextos = numeroTextos;
    }

    public Integer getNumeroImagenes() {
        return numeroImagenes;
    }

    public void setNumeroImagenes(Integer numeroImagenes) {
        this.numeroImagenes = numeroImagenes;
    }
    
    
    public String getColorFondoGeneral() {
        return colorFondoGeneral;
    }

    public void setColorFondoGeneral(String colorFondoGeneral) {
        this.colorFondoGeneral = colorFondoGeneral;
    }

    public String getTexto1ColorFondo() {
        return texto1ColorFondo;
    }

    public void setTexto1ColorFondo(String texto1ColorFondo) {
        this.texto1ColorFondo = texto1ColorFondo;
    }

    public String getTexto2ColorFondo() {
        return texto2ColorFondo;
    }

    public void setTexto2ColorFondo(String texto2ColorFondo) {
        this.texto2ColorFondo = texto2ColorFondo;
    }

    public String getTexto3ColorFondo() {
        return texto3ColorFondo;
    }

    public void setTexto3ColorFondo(String texto3ColorFondo) {
        this.texto3ColorFondo = texto3ColorFondo;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getImg1ColIni() {
        return img1ColIni;
    }

    public void setImg1ColIni(Integer img1ColIni) {
        this.img1ColIni = img1ColIni;
    }

    public Integer getImg1ColFin() {
        return img1ColFin;
    }

    public void setImg1ColFin(Integer img1ColFin) {
        this.img1ColFin = img1ColFin;
    }

    public Integer getImg1FilIni() {
        return img1FilIni;
    }

    public void setImg1FilIni(Integer img1FilIni) {
        this.img1FilIni = img1FilIni;
    }

    public Integer getImg1FilFin() {
        return img1FilFin;
    }

    public void setImg1FilFin(Integer img1FilFin) {
        this.img1FilFin = img1FilFin;
    }

    public Integer getImg2ColIni() {
        return img2ColIni;
    }

    public void setImg2ColIni(Integer img2ColIni) {
        this.img2ColIni = img2ColIni;
    }

    public Integer getImg2ColFin() {
        return img2ColFin;
    }

    public void setImg2ColFin(Integer img2ColFin) {
        this.img2ColFin = img2ColFin;
    }

    public Integer getImg2FilIni() {
        return img2FilIni;
    }

    public void setImg2FilIni(Integer img2FilIni) {
        this.img2FilIni = img2FilIni;
    }

    public Integer getImg2FilFin() {
        return img2FilFin;
    }

    public void setImg2FilFin(Integer img2FilFin) {
        this.img2FilFin = img2FilFin;
    }

    public Integer getImg3ColIni() {
        return img3ColIni;
    }

    public void setImg3ColIni(Integer img3ColIni) {
        this.img3ColIni = img3ColIni;
    }

    public Integer getImg3ColFin() {
        return img3ColFin;
    }

    public void setImg3ColFin(Integer img3ColFin) {
        this.img3ColFin = img3ColFin;
    }

    public Integer getImg3FilIni() {
        return img3FilIni;
    }

    public void setImg3FilIni(Integer img3FilIni) {
        this.img3FilIni = img3FilIni;
    }

    public Integer getImg3FilFin() {
        return img3FilFin;
    }

    public void setImg3FilFin(Integer img3FilFin) {
        this.img3FilFin = img3FilFin;
    }

    public Integer getTexto1ColIni() {
        return texto1ColIni;
    }

    public void setTexto1ColIni(Integer texto1ColIni) {
        this.texto1ColIni = texto1ColIni;
    }

    public Integer getTexto1ColFin() {
        return texto1ColFin;
    }

    public void setTexto1ColFin(Integer texto1ColFin) {
        this.texto1ColFin = texto1ColFin;
    }

    public Integer getTexto1FilIni() {
        return texto1FilIni;
    }

    public void setTexto1FilIni(Integer texto1FilIni) {
        this.texto1FilIni = texto1FilIni;
    }

    public Integer getTexto1FilFin() {
        return texto1FilFin;
    }

    public void setTexto1FilFin(Integer texto1FilFin) {
        this.texto1FilFin = texto1FilFin;
    }

    public String getTexto1LetraFamilia() {
        return texto1LetraFamilia;
    }

    public void setTexto1LetraFamilia(String texto1LetraFamilia) {
        this.texto1LetraFamilia = texto1LetraFamilia;
    }

    public Integer getTexto1LetraTamanyo() {
        return texto1LetraTamanyo;
    }

    public void setTexto1LetraTamanyo(Integer texto1LetraTamanyo) {
        this.texto1LetraTamanyo = texto1LetraTamanyo;
    }

    public String getTexto1LetraColor() {
        return texto1LetraColor;
    }

    public void setTexto1LetraColor(String texto1LetraColor) {
        this.texto1LetraColor = texto1LetraColor;
    }

    public Integer getTexto2ColIni() {
        return texto2ColIni;
    }

    public void setTexto2ColIni(Integer texto2ColIni) {
        this.texto2ColIni = texto2ColIni;
    }

    public Integer getTexto2ColFin() {
        return texto2ColFin;
    }

    public void setTexto2ColFin(Integer texto2ColFin) {
        this.texto2ColFin = texto2ColFin;
    }

    public Integer getTexto2FilIni() {
        return texto2FilIni;
    }

    public void setTexto2FilIni(Integer texto2FilIni) {
        this.texto2FilIni = texto2FilIni;
    }

    public Integer getTexto2FilFin() {
        return texto2FilFin;
    }

    public void setTexto2FilFin(Integer texto2FilFin) {
        this.texto2FilFin = texto2FilFin;
    }

    public String getTexto2LetraFamilia() {
        return texto2LetraFamilia;
    }

    public void setTexto2LetraFamilia(String texto2LetraFamilia) {
        this.texto2LetraFamilia = texto2LetraFamilia;
    }

    public Integer getTexto2LetraTamanyo() {
        return texto2LetraTamanyo;
    }

    public void setTexto2LetraTamanyo(Integer texto2LetraTamanyo) {
        this.texto2LetraTamanyo = texto2LetraTamanyo;
    }

    public String getTexto2LetraColor() {
        return texto2LetraColor;
    }

    public void setTexto2LetraColor(String texto2LetraColor) {
        this.texto2LetraColor = texto2LetraColor;
    }

    public Integer getTexto3ColIni() {
        return texto3ColIni;
    }

    public void setTexto3ColIni(Integer texto3ColIni) {
        this.texto3ColIni = texto3ColIni;
    }

    public Integer getTexto3ColFin() {
        return texto3ColFin;
    }

    public void setTexto3ColFin(Integer texto3ColFin) {
        this.texto3ColFin = texto3ColFin;
    }

    public Integer getTexto3FilIni() {
        return texto3FilIni;
    }

    public void setTexto3FilIni(Integer texto3FilIni) {
        this.texto3FilIni = texto3FilIni;
    }

    public Integer getTexto3FilFin() {
        return texto3FilFin;
    }

    public void setTexto3FilFin(Integer texto3FilFin) {
        this.texto3FilFin = texto3FilFin;
    }

    public String getTexto3LetraFamilia() {
        return texto3LetraFamilia;
    }

    public void setTexto3LetraFamilia(String texto3LetraFamilia) {
        this.texto3LetraFamilia = texto3LetraFamilia;
    }

    public Integer getTexto3LetraTamanyo() {
        return texto3LetraTamanyo;
    }

    public void setTexto3LetraTamanyo(Integer texto3LetraTamanyo) {
        this.texto3LetraTamanyo = texto3LetraTamanyo;
    }

    public String getTexto3LetraColor() {
        return texto3LetraColor;
    }

    public void setTexto3LetraColor(String texto3LetraColor) {
        this.texto3LetraColor = texto3LetraColor;
    }




}
















