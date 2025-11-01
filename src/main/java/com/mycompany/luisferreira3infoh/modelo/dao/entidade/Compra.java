/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author
 */
public class Compra implements Serializable {

    private Integer codCompra;
    private String tipoPagamento;
    private LocalDateTime dataCompra;
    private Visitante visitante;
    private Funcionario funcionario;
    private List<CompraLanche> listaLanches;
    private List<CompraIngressoBrinquedos> listaCIB;
    private List<CompraIngressoEventos> listaCIE;

    public Compra() {
    }

    public Integer getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(Integer codCompra) {
        this.codCompra = codCompra;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getDataCompraFormatada() {
        if (dataCompra != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dataCompra.format(formatter);
        }
        return "erro";
    }

    public String getDataCompraFormatada2() {
        if (dataCompra != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return dataCompra.format(formatter);
        }
        return "erro";
    }

    public List<CompraLanche> getListaLanches() {
        return listaLanches;
    }

    public void setListaLanches(List<CompraLanche> listaLanches) {
        this.listaLanches = listaLanches;
    }

    public List<CompraIngressoBrinquedos> getListaCIB() {
        return listaCIB;
    }

    public void setListaCIB(List<CompraIngressoBrinquedos> listaCIB) {
        this.listaCIB = listaCIB;
    }

    public List<CompraIngressoEventos> getListaCIE() {
        return listaCIE;
    }

    public void setListaCIE(List<CompraIngressoEventos> listaCIE) {
        this.listaCIE = listaCIE;
    }

    
    public double getValorTotalCompra() {
        double total = 0;

        if (listaLanches != null) {
            for (CompraLanche cl : listaLanches) {
                total += cl.getLanche().getPrecoLanche() * cl.getQuantidadeLanche();
            }
        }

        if (listaCIB != null) {
            for (CompraIngressoBrinquedos cib : listaCIB) {
                total += cib.getIb().getValor() * cib.getQuantIngBrinq();
            }
        }

        if (listaCIE != null) {
            for (CompraIngressoEventos cie : listaCIE) {
                total += cie.getIe().getValor() * cie.getQuantIngEvent();
            }
        }

        return total;
    }

    public String getDescricaoResumida() {
        if (listaLanches != null && !listaLanches.isEmpty()) {
            return listaLanches.get(0).getLanche().getNomeLanche() + (listaLanches.size() > 1 ? " e outros" : "");
        } else if (listaCIB != null && !listaCIB.isEmpty()) {
            return listaCIB.get(0).getIb().getBrinquedo().getNomeBrinquedo() + (listaCIB.size() > 1 ? " e outros" : "");
        } else if (listaCIE != null && !listaCIE.isEmpty()) {
            return listaCIE.get(0).getIe().getEvento().getNomeEvento() + (listaCIE.size() > 1 ? " e outros" : "");
        }
        return "Sem itens";
    }

    public String getDescricaoTotal() {
        StringBuilder sb = new StringBuilder(); // para concatenar as strings de maneira mais dinâmica

        if (listaLanches != null && !listaLanches.isEmpty()) {
            for (int i = 0; i < listaLanches.size(); i++) {
                sb.append(listaLanches.get(i).getLanche().getNomeLanche());
                if (i < listaLanches.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        if (listaCIB != null && !listaCIB.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", "); // separa lanches de brinquedos
            }
            for (int i = 0; i < listaCIB.size(); i++) {
                sb.append(listaCIB.get(i).getIb().getBrinquedo().getNomeBrinquedo());
                if (i < listaCIB.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        if (listaCIE != null && !listaCIE.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(", "); // separa brinquedos de eventos
            }
            for (int i = 0; i < listaCIE.size(); i++) {
                sb.append(listaCIE.get(i).getIe().getEvento().getNomeEvento());
                if (i < listaCIE.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        return sb.length() > 0 ? sb.toString() : "Sem itens";
    }

    public int getQuantidadeTotal() {
        int total = 0;
        if (listaLanches != null) {
            total += listaLanches.stream().mapToInt(cl -> cl.getQuantidadeLanche()).sum();
        }
        if (listaCIB != null) {
            total += listaCIB.stream().mapToInt(cib -> cib.getQuantIngBrinq()).sum();
        }
        if (listaCIE != null) {
            total += listaCIE.stream().mapToInt(cie -> cie.getQuantIngEvent()).sum();
        }
        return total;
    }

}
