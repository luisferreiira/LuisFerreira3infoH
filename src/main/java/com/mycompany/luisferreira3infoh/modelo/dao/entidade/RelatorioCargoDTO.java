/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;


/**
 *
 * @author 
 */
public class RelatorioCargoDTO {

    private String nomeCargo;
    private int totalFuncionarios;
    private double porcentagem;

    public RelatorioCargoDTO(String nomeCargo, int totalFuncionarios, double porcentagem) {
        this.nomeCargo = nomeCargo;
        this.totalFuncionarios = totalFuncionarios;
        this.porcentagem = porcentagem;
    }


    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public int getTotalFuncionarios() {
        return totalFuncionarios;
    }

    public void setTotalFuncionarios(int totalFuncionarios) {
        this.totalFuncionarios = totalFuncionarios;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }
    
    
}


