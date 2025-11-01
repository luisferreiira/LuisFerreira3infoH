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
public class Lanche implements Serializable{
    private Integer codLanche, lucroLanches;
    private String nomeLanche, descricao;
    private Double precoLanche;
    private Categoria categoria;

    public Lanche() {
    }

    public Integer getCodLanche() {
        return codLanche;
    }

    public void setCodLanche(Integer codLanche) {
        this.codLanche = codLanche;
    }

    public Integer getLucroLanches() {
        return lucroLanches;
    }

    public void setLucroLanches(Integer lucroLanches) {
        this.lucroLanches = lucroLanches;
    }

    public String getNomeLanche() {
        return nomeLanche;
    }

    public void setNomeLanche(String nomeLanche) {
        this.nomeLanche = nomeLanche;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoLanche() {
        return precoLanche;
    }

    public void setPrecoLanche(Double precoLanche) {
        this.precoLanche = precoLanche;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
}
