/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.RelatorioCargoDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelatorioCargoDAO extends GenericoDAO<RelatorioCargoDTO> {

    public class RelatorioCargoRowMapper implements RowMapper<RelatorioCargoDTO> {

        @Override
        public RelatorioCargoDTO mapRow(ResultSet rs) throws SQLException {
            return new RelatorioCargoDTO(
                    rs.getString("nomeCargo"),
                    rs.getInt("totalFuncionarios"),
                    rs.getDouble("porcentagem")
            );
        }
    }

    public List<RelatorioCargoDTO> listarFuncionariosPorCargo() {
        String sql = "SELECT c.nomeCargo, COUNT(f.codFuncionario) AS totalFuncionarios, "
                + "ROUND((COUNT(f.codFuncionario) * 100.0 / "
                + "(SELECT COUNT(*) FROM funcionario f2 WHERE f2.dataDemissao IS NULL)), 2) AS porcentagem "
                + "FROM funcionario f "
                + "JOIN cargo c ON f.cargo_codCargo = c.codCargo "
                + "WHERE f.dataDemissao IS NULL "
                + "GROUP BY c.nomeCargo "
                + "ORDER BY totalFuncionarios DESC";

        return buscarTodos(sql, new RelatorioCargoRowMapper());
    }

}
