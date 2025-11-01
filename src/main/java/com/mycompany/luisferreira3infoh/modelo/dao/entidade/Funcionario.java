/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author
 */
public class Funcionario implements Serializable{
    private Integer codFuncionario;
    private String nomeFuncionario;
    private String cpf;
    private String carteiraTrabalho;
    private String email, senha;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private Cargo cargo_codCargo;
    private Integer anosContrato;
    
    public Funcionario() {
    }

    public Integer getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(Integer codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCarteiraTrabalho() {
        return carteiraTrabalho;
    }

    public void setCarteiraTrabalho(String carteiraTrabalho) {
        this.carteiraTrabalho = carteiraTrabalho;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Cargo getCargo() {
        return cargo_codCargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo_codCargo = cargo;
    }
    
    public String getDataAdmissaoFormatada() {
    if (dataAdmissao != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataAdmissao.format(formatter);
    }
    return "erro";
}
    
    public String getDataDemissaoFormatada() {
    if (dataDemissao != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataDemissao.format(formatter);
    }
    else
    {
        return "--";
    }
}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getAnosContrato() {
        return anosContrato;
    }

    public void setAnosContrato(Integer anosContrato) {
        this.anosContrato = anosContrato;
    }
    
    
    
    
}
