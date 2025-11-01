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
public class Categoria {
    private Integer codCategoria;
    private String nomeCategoria;
    private String descricao;

    public Categoria() {
    }

    
    
    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategroia) {
        this.nomeCategoria = nomeCategroia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    
}
