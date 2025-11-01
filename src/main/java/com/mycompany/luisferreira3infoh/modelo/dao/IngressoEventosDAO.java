/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Eventos;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.IngressoEventos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author
 */
public class IngressoEventosDAO extends GenericoDAO <IngressoEventos>{
    
    public void salvar(IngressoEventos objIE)
    {
        String sql = "INSERT INTO ingressoeventos (tipoIngresso, valor,lucroIE, eventos_codEventos) VALUES (?,?,?,?);";
        save(sql, objIE.getTipoIngresso(),
                objIE.getValor(),
                objIE.getLucroIE() != null ? objIE.getLucroIE() : null,
                objIE.getEvento().getCodEventos());
    }
    
    public void alterar(IngressoEventos objIE)
    {
        String sql = "Update ingressoeventos set tipoIngresso = ?, valor = ?,lucroIE = ?, eventos_codEventos = ? where codIngresso = ?";
        save(sql, objIE.getTipoIngresso(),
                objIE.getValor(),
                objIE.getLucroIE() != null ? objIE.getLucroIE() : null,
                objIE.getEvento().getCodEventos(),
                objIE.getCodIngresso());
    }
    
    public void excluir(IngressoEventos objIE)
    {
        String sql = "DELETE FROM ingressoeventos WHERE codIngresso = ?;";
        save(sql, objIE.getCodIngresso());
    }
    
    private static class IngressoEventosRowMapper implements RowMapper<IngressoEventos> {
    @Override
    public IngressoEventos mapRow(ResultSet rs) throws SQLException {
        IngressoEventos objIE = new IngressoEventos();
        objIE.setCodIngresso(rs.getInt("codIngresso"));
        objIE.setTipoIngresso(rs.getString("tipoIngresso"));
        objIE.setValor(rs.getDouble("valor"));
        objIE.setLucroIE(rs.getInt("lucroIE"));

        // Categoria preenchido diretamente do SELECT
        Eventos evento = new Eventos();
        evento.setCodEventos(rs.getInt("eventos_codEventos")); // alias correto
        evento.setNomeEvento(rs.getString("nomeEvento"));
        objIE.setEvento(evento);

        return objIE;
    }
}

    
    public List<IngressoEventos> buscarTodosIngressoEventos()
    {
        String sql = "SELECT ie.*, e.nomeEvento FROM ingressoeventos as ie INNER JOIN eventos as e ON ie.eventos_codEventos = e.codEventos ORDER BY codIngresso ASC;";
        return buscarTodos(sql, new IngressoEventosRowMapper());
    }
    
    public IngressoEventos buscarIngressoEventosPorId(int id)
    {
        String sql = "SELECT ie.*, e.nomeEvento FROM ingressoeventos as ie INNER JOIN eventos as e ON ie.eventos_codEventos = e.codEventos where ie.codIngresso = ?;";
        return buscarPorId(sql, new IngressoEventosRowMapper());
    }
}
