/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Eventos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class EventosDAO extends GenericoDAO <Eventos>{
    
    public void salvar(Eventos objEvento) {
        String sql = "INSERT INTO eventos (nomeEvento, tipoEvento, localEvento, dataInicio, dataFim, descricao) VALUES (?, ?, ?, ?, ?, ?);";
        save(sql,
             objEvento.getNomeEvento(),
             objEvento.getTipoEvento(),
             objEvento.getLocalEvento(),
             Timestamp.valueOf(objEvento.getDataInicio()), // LocalDateTime -> Timestamp
             Timestamp.valueOf(objEvento.getDataFim()),
             objEvento.getDescricao());
    }

    public void alterar(Eventos objEvento) {
        String sql = "UPDATE eventos SET nomeEvento = ?, tipoEvento = ?, localEvento = ?, dataInicio = ?, dataFim = ?, descricao = ? WHERE codEventos = ?;";
        save(sql,
             objEvento.getNomeEvento(),
             objEvento.getTipoEvento(),
             objEvento.getLocalEvento(),
             Timestamp.valueOf(objEvento.getDataInicio()),
             Timestamp.valueOf(objEvento.getDataFim()),
             objEvento.getDescricao(),
             objEvento.getCodEventos());
    }

    public void excluir(Eventos objEvento) {
        String sql = "DELETE FROM eventos WHERE codEventos = ?;";
        save(sql, objEvento.getCodEventos());
    }

    private static class EventosRowMapper implements RowMapper<Eventos> {
        @Override
        public Eventos mapRow(ResultSet rs) throws SQLException {
            Eventos objEvento = new Eventos();
            objEvento.setCodEventos(rs.getInt("codEventos")); // caso tenha esse campo
            objEvento.setNomeEvento(rs.getString("nomeEvento"));
            objEvento.setTipoEvento(rs.getString("tipoEvento"));
            objEvento.setLocalEvento(rs.getString("localEvento"));

            Timestamp tsInicio = rs.getTimestamp("dataInicio");
            if (tsInicio != null) {
                objEvento.setDataInicio(tsInicio.toLocalDateTime());
            }

            Timestamp tsFim = rs.getTimestamp("dataFim");
            if (tsFim != null) {
                objEvento.setDataFim(tsFim.toLocalDateTime());
            }

            objEvento.setDescricao(rs.getString("descricao"));
            return objEvento;
        }
    }

    public List<Eventos> buscarTodosEventos() {
        String sql = "SELECT * FROM eventos;";
        return buscarTodos(sql, new EventosRowMapper());
    }

    public Eventos buscarPorId(int id) {
        String sql = "SELECT * FROM eventos WHERE codEventos = ?;";
        return buscarPorId(sql, new EventosRowMapper(), id);
    }
}