/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

/**
 *
 * @author 
 */
public class CompraIngressoBrinquedos {
    private Integer codCompraBrinquedos, quantIngBrinq;
    private Compra compra;
    private IngressoBrinquedos ib;

    public CompraIngressoBrinquedos() {
    }

    public Integer getCodCompraBrinquedos() {
        return codCompraBrinquedos;
    }

    public void setCodCompraBrinquedos(Integer codCompraBrinquedos) {
        this.codCompraBrinquedos = codCompraBrinquedos;
    }

    public Integer getQuantIngBrinq() {
        return quantIngBrinq;
    }

    public void setQuantIngBrinq(Integer quantIngBrinq) {
        this.quantIngBrinq = quantIngBrinq;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public IngressoBrinquedos getIb() {
        return ib;
    }

    public void setIb(IngressoBrinquedos ib) {
        this.ib = ib;
    }

    
    
    
}
