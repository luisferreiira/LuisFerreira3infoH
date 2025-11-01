/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Tokens_Redefinicao implements Serializable{

    private Integer codTokensRedefinicao;
    private String email;
    private String token;
    private String tipoUsuario; // "visitante" ou "funcionario"
    private LocalDateTime expiracao;
    private LocalDateTime criadoEm;

    public Tokens_Redefinicao() {
    }

    public Integer getCodTokensRedefinicao() {
        return codTokensRedefinicao;
    }

    public void setCodTokensRedefinicao(Integer codTokensRedefinicao) {
        this.codTokensRedefinicao = codTokensRedefinicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDateTime getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(LocalDateTime expiracao) {
        this.expiracao = expiracao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

}
