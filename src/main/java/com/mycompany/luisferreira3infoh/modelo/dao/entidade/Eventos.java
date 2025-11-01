/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author 16394072605
 */
public class Eventos {
    private Integer codEventos;
    private String nomeEvento;
    private String tipoEvento;
    private String localEvento;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String descricao;

    public Eventos() {
    }

    public Integer getCodEventos() {
        return codEventos;
    }

    public void setCodEventos(Integer codEventos) {
        this.codEventos = codEventos;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.codEventos);
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
        final Eventos other = (Eventos) obj;
        if (!Objects.equals(this.codEventos, other.codEventos)) {
            return false;
        }
        return true;
    }
    
    /*@Transient // não persistente no banco de dados 
    public String getDataInicioFormatado()
  {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      return sdf.format(dataInicio.getTimeInMillis());
  }
    @Transient // não persistente no banco de dados 
    public String getDataFimFormatado()
  {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      return sdf.format(dataFim.getTimeInMillis());
  }*/
    public String getDataInicioFormatada() {
    if (dataInicio != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataInicio.format(formatter);
    }
    return "erro";
}

    public String getDataFimFormatada() {
    if (dataFim != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataFim.format(formatter);
    }
    return "erro";
}


    @Override
    public String toString() {
        return nomeEvento;
    }
}
