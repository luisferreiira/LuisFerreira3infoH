/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class IngressoBrinquedos implements Serializable{
    private Integer codIngresso, lucroIB;
    private String tipoIngresso;
    private Double valor;
    private Brinquedo brinquedo;

    public IngressoBrinquedos() {
    }

    public Integer getCodIngresso() {
        return codIngresso;
    }

    public void setCodIngresso(Integer codIngresso) {
        this.codIngresso = codIngresso;
    }

    public Integer getLucroIB() {
        return lucroIB;
    }

    public void setLucroIB(Integer lucroIB) {
        this.lucroIB = lucroIB;
    }

    public String getTipoIngresso() {
        return tipoIngresso;
    }

    public void setTipoIngresso(String tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Brinquedo getBrinquedo() {
        return brinquedo;
    }

    public void setBrinquedo(Brinquedo brinquedo) {
        this.brinquedo = brinquedo;
    }
    
    
}
