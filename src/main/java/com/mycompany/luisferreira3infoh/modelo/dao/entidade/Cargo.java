/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

/**
 *
 * @author 16394072605
 */
public class Cargo {
    private Integer codCargo;
    private String nomeCargo;
    private double salarioInicial;
    private double salarioFinal;
    private int comissao;

    public Cargo() {
    }

    public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public double getSalarioInicial() {
        return salarioInicial;
    }

    public void setSalarioInicial(double salarioInicial) {
        this.salarioInicial = salarioInicial;
    }

    public double getSalarioFinal() {
        return salarioFinal;
    }

    public void setSalarioFinal(double salarioFinal) {
        this.salarioFinal = salarioFinal;
    }

    public int getComissao() {
        return comissao;
    }

    public void setComissao(int comissao) {
        this.comissao = comissao;
    }
    
    
}
