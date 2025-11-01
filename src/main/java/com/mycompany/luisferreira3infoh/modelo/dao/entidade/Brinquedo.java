/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.util.Objects;

/**
 *
 * @author 16394072605
 */
public class Brinquedo {
    private Integer codBrinquedo;
    private String nomeBrinquedo;
    private int capacidadeMaxima;
    private int capacidadeAtual;
    private String tipoBrinquedo;
    private int idadeRestrita;
    private int funcionamento;

    public Brinquedo() {
    }

    public Integer getCodBrinquedo() {
        return codBrinquedo;
    }

    public void setCodBrinquedo(Integer codBrinquedo) {
        this.codBrinquedo = codBrinquedo;
    }

    public String getNomeBrinquedo() {
        return nomeBrinquedo;
    }

    public void setNomeBrinquedo(String nomeBrinquedo) {
        this.nomeBrinquedo = nomeBrinquedo;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public void setCapacidadeAtual(int capacidadeAtual) {
        this.capacidadeAtual = capacidadeAtual;
    }
    

    public String getTipoBrinquedo() {
        return tipoBrinquedo;
    }

    public void setTipoBrinquedo(String tipoBrinquedo) {
        this.tipoBrinquedo = tipoBrinquedo;
    }

    public int getIdadeRestrita() {
        return idadeRestrita;
    }

    public void setIdadeRestrita(int idadeRestrita) {
        this.idadeRestrita = idadeRestrita;
    }

    public int getFuncionamento() {
        return funcionamento;
    }

    public void setFuncionamento(int funcionamento) {
        this.funcionamento = funcionamento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.codBrinquedo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Brinquedo other = (Brinquedo) obj;
        if (!Objects.equals(this.codBrinquedo, other.codBrinquedo)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nomeBrinquedo;
    }
}
