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
public class CompraLanche implements Serializable{
    private Integer codCompraLanche, quantidadeLanche;
    private Compra compra;
    private Lanche lanche;

    public CompraLanche() {
    }

    public Integer getCodCompraLanche() {
        return codCompraLanche;
    }

    public void setCodCompraLanche(Integer codCompraLanche) {
        this.codCompraLanche = codCompraLanche;
    }

    public Integer getQuantidadeLanche() {
        return quantidadeLanche;
    }

    public void setQuantidadeLanche(Integer quantidadeLanche) {
        this.quantidadeLanche = quantidadeLanche;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Lanche getLanche() {
        return lanche;
    }

    public void setLanche(Lanche lanche) {
        this.lanche = lanche;
    }
    
    
}
