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
public class IngressoEventos implements Serializable{
    private Integer codIngresso, lucroIE;
    private String tipoIngresso;
    private Double valor;
    private Eventos evento;

    public IngressoEventos() {
    }

    public Integer getCodIngresso() {
        return codIngresso;
    }

    public void setCodIngresso(Integer codIngresso) {
        this.codIngresso = codIngresso;
    }

    public Integer getLucroIE() {
        return lucroIE;
    }

    public void setLucroIE(Integer lucroIE) {
        this.lucroIE = lucroIE;
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

    public Eventos getEvento() {
        return evento;
    }

    public void setEvento(Eventos evento) {
        this.evento = evento;
    }    
}
