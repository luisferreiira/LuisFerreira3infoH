/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.beans.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

/** 
 *
 * @author 16394072605
 */
public class Visitante implements Serializable{
    private Integer codVisitante;
    private String nomeVisitante, email, senha;
    private LocalDate dataNascimento;

    
    public Visitante ()
    {
    }

    public Integer getCodVisitante() {
        return codVisitante;
    }

    public void setCodVisitante(Integer codVisitante) {
        this.codVisitante = codVisitante;
    }

    

    

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
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

    
    
    /*public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }*/

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getDataNascimentoFormatada() {
    if (dataNascimento != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formatter);
    }
    return "erro";
}
    
    
        /*@Transient // não persistente no banco de dados 
      public String getDataNascimentoFormatado()
      {
          SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          return sdf.format(dataNascimento.getTime());
      }*/
      
      @Override
    public String toString() {
        return nomeVisitante;
    }
  
    
}
