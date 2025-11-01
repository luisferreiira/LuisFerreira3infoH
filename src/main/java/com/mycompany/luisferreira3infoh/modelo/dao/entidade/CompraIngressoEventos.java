/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

/**
 *
 * @author 
 */
public class CompraIngressoEventos {
    private Integer codCompraEventos, quantIngEvent;
    private Compra compra;
    private IngressoEventos ie;

    public CompraIngressoEventos() {
    }

    public Integer getCodCompraEventos() {
        return codCompraEventos;
    }

    public void setCodCompraEventos(Integer codCompraEventos) {
        this.codCompraEventos = codCompraEventos;
    }

    public Integer getQuantIngEvent() {
        return quantIngEvent;
    }

    public void setQuantIngEvent(Integer quantIngEvent) {
        this.quantIngEvent = quantIngEvent;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public IngressoEventos getIe() {
        return ie;
    }

    public void setIe(IngressoEventos ie) {
        this.ie = ie;
    }
    
    
}
