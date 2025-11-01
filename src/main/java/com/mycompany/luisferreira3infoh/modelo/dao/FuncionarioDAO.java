/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author
 */
public class FuncionarioDAO extends GenericoDAO<Funcionario> {

    public void salvar(Funcionario objFuncionario) {
        String hashSenha = BCrypt.hashpw(objFuncionario.getSenha(), BCrypt.gensalt());

        String sql = "INSERT INTO funcionario (nomeFuncionario, cpf,carteiraTrabalho ,dataAdmissao, dataDemissao, email, senha, cargo_codCargo ) VALUES (?,?,?,?,?,?,?,?);";
        save(sql, objFuncionario.getNomeFuncionario(),
                objFuncionario.getCpf(),
                objFuncionario.getCarteiraTrabalho(),
                java.sql.Date.valueOf(objFuncionario.getDataAdmissao()),
                objFuncionario.getDataDemissao() != null ? java.sql.Date.valueOf(objFuncionario.getDataDemissao()) : null,
                objFuncionario.getEmail(),
                hashSenha,
                objFuncionario.getCargo().getCodCargo());
    }

    public void alterar(Funcionario objFuncionario) {
        String hashSenha = BCrypt.hashpw(objFuncionario.getSenha(), BCrypt.gensalt());

        String sql = "Update funcionario set nomeFuncionario = ?, cpf = ?,carteiraTrabalho = ?,dataAdmissao = ?, dataDemissao = ?, email = ?, senha = ?, cargo_codCargo = ? where codFuncionario = ?";
        save(sql, objFuncionario.getNomeFuncionario(),
                objFuncionario.getCpf(),
                objFuncionario.getCarteiraTrabalho(),
                java.sql.Date.valueOf(objFuncionario.getDataAdmissao()),
                objFuncionario.getDataDemissao() != null ? java.sql.Date.valueOf(objFuncionario.getDataDemissao()) : null,
                objFuncionario.getEmail(),
                hashSenha,
                objFuncionario.getCargo().getCodCargo(),
                objFuncionario.getCodFuncionario());
    }

    public void redefinirSenha(Funcionario objFuncionario) {

        String sql = "Update funcionario set senha = ? where codFuncionario = ?";
        save(sql, objFuncionario.getSenha(),
                objFuncionario.getCodFuncionario());
    }

    public void excluir(Funcionario objFuncionario) {
        String sql = "DELETE FROM funcionario WHERE codFuncionario = ?;";
        save(sql, objFuncionario.getCodFuncionario());
    }

    private static class FuncionarioRowMapper implements RowMapper<Funcionario> {

        @Override
        public Funcionario mapRow(ResultSet rs) throws SQLException {
            Funcionario objFuncionario = new Funcionario();
            objFuncionario.setCodFuncionario(rs.getInt("codFuncionario"));
            //objFuncionario.setAnosContrato(rs.getInt("anosContrato"));
            objFuncionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
            objFuncionario.setCpf(rs.getString("cpf"));
            objFuncionario.setCarteiraTrabalho(rs.getString("carteiraTrabalho"));
            objFuncionario.setEmail(rs.getString("email"));
            objFuncionario.setSenha(rs.getString("senha"));

            Date dataAdm = rs.getDate("dataAdmissao");
            if (dataAdm != null) {
                objFuncionario.setDataAdmissao(dataAdm.toLocalDate());
            }

            Date dataDem = rs.getDate("dataDemissao");
            if (dataDem != null) {
                objFuncionario.setDataDemissao(dataDem.toLocalDate());
            } else {
                objFuncionario.setDataDemissao(null);
            }

            // Cargo preenchido diretamente do SELECT
            Cargo cargo = new Cargo();
            cargo.setCodCargo(rs.getInt("cargo_codCargo")); // alias correto
            cargo.setNomeCargo(rs.getString("nomeCargo"));
            objFuncionario.setCargo(cargo);

            return objFuncionario;
        }
    }

    public List<Funcionario> buscarTodosFuncionarios() {
        String sql = "SELECT f.*, c.nomeCargo FROM funcionario f JOIN cargo c ON f.cargo_codCargo = c.codCargo ORDER BY codFuncionario ASC;";
        return buscarTodos(sql, new FuncionarioRowMapper());
    }

    public Funcionario buscarFuncionarioPorId(int id) {
        String sql = "SELECT f.*, c.nomeCargo FROM funcionario f JOIN cargo c ON f.cargo_codCargo = c.codCargo where f.codFuncionario = ? ;";
        return buscarPorId(sql, new FuncionarioRowMapper());
    }

    public Funcionario buscarFuncionarioAleatorio() {
        String sql = "SELECT f.*, c.nomeCargo FROM funcionario f JOIN cargo c ON f.cargo_codCargo = c.codCargo WHERE f.cargo_codCargo = 4 ORDER BY RAND() LIMIT 1;";
        return buscarUm(sql, new FuncionarioRowMapper());
    }

    public Funcionario buscarPorEmail(String email) {
        String sql = "SELECT f.*, c.nomeCargo FROM funcionario f JOIN cargo c ON f.cargo_codCargo = c.codCargo WHERE f.email = ?";
        return buscarUm(sql, new FuncionarioRowMapper(), email);
    }

    // sem utilidade
    public List<Funcionario> listarPorCargoRelatorio(int codCargo) {
        String sql = "SELECT f.*, c.nomeCargo FROM funcionario f "
                + "JOIN cargo c ON f.cargo_codCargo = c.codCargo "
                + "WHERE f.cargo_codCargo = ? "
                + "ORDER BY f.nomeFuncionario ASC;";
        return buscarTodosComParametro(sql, new FuncionarioRowMapper(), codCargo);
    }

    public List<Funcionario> listarPorTempoContratoRelatorio() {
        String sql = "SELECT f.*, c.nomeCargo, "
                + "TIMESTAMPDIFF(YEAR, f.dataAdmissao, CURDATE()) AS anosContrato "
                + "FROM funcionario f "
                + "JOIN cargo c ON f.cargo_codCargo = c.codCargo "
                + "WHERE f.dataDemissao IS NULL "
                + "ORDER BY anosContrato DESC;";

        // RowMapper exclusivo para esse relatório
        RowMapper<Funcionario> mapperRelatorio = new RowMapper<Funcionario>() {
            @Override
            public Funcionario mapRow(ResultSet rs) throws SQLException {
                Funcionario f = new Funcionario();
                f.setCodFuncionario(rs.getInt("codFuncionario"));
                f.setNomeFuncionario(rs.getString("nomeFuncionario"));
                f.setCpf(rs.getString("cpf"));
                f.setCarteiraTrabalho(rs.getString("carteiraTrabalho"));
                f.setEmail(rs.getString("email"));
                f.setSenha(rs.getString("senha"));

                Date dataAdm = rs.getDate("dataAdmissao");
                if (dataAdm != null) {
                    f.setDataAdmissao(dataAdm.toLocalDate());
                }

                Date dataDem = rs.getDate("dataDemissao");
                if (dataDem != null) {
                    f.setDataDemissao(dataDem.toLocalDate());
                } else {
                    f.setDataDemissao(null);
                }

                // Cargo
                Cargo cargo = new Cargo();
                cargo.setCodCargo(rs.getInt("cargo_codCargo"));
                cargo.setNomeCargo(rs.getString("nomeCargo"));
                f.setCargo(cargo);

                // anosContrato específico deste relatório
                f.setAnosContrato(rs.getInt("anosContrato"));

                return f;
            }
        };

        return buscarTodos(sql, mapperRelatorio);
    }

    // parte para um relatorio funcionar
    public double totalSalariosAtivosPorMes(int mes, int ano) {
        String sql = "SELECT SUM(c.salarioFinal) AS totalSalarios "
                + "FROM funcionario AS f "
                + "INNER JOIN cargo AS c ON f.cargo_codCargo = c.codCargo "
                + "WHERE f.dataDemissao IS NULL "
                + "AND MONTH(f.dataAdmissao) <= ? AND YEAR(f.dataAdmissao) <= ?";

        return buscarDouble(sql, mes, ano);
    }

}
