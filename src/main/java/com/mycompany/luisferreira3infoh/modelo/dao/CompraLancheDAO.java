/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Compra;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.CompraLanche;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Lanche;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Visitante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author
 */
public class CompraLancheDAO extends GenericoDAO<CompraLanche> {

    public void salvar(CompraLanche objCL) {
        String sql = "INSERT INTO compraslanche (compra_codCompra, lanche_codLanche, quantidadeLanche ) VALUES (?,?,?);";
        save(sql, objCL.getCompra().getCodCompra(),
                objCL.getLanche().getCodLanche(),
                objCL.getQuantidadeLanche());
    }

    public void alterar(CompraLanche objCL) {
        String sql = "Update comprasLanche set compra_codCompra = ?, lanche_codLanche = ?, quantidadeLanche = ? where codCompraLanche = ?";
        save(sql, objCL.getCompra().getCodCompra(),
                objCL.getLanche().getCodLanche(),
                objCL.getQuantidadeLanche(),
                objCL.getCodCompraLanche());
    }

    public void excluir(CompraLanche objCL) {
        String sql = "DELETE FROM compraslanche WHERE codCompraLanche = ?;";
        save(sql, objCL.getCodCompraLanche());
    }

    private static class CompraLancheRowMapper implements RowMapper<CompraLanche> {

        @Override
        public CompraLanche mapRow(ResultSet rs) throws SQLException {
            CompraLanche objCL = new CompraLanche();
            objCL.setCodCompraLanche(rs.getInt("codCompraLanche"));
            objCL.setQuantidadeLanche(rs.getInt("quantidadeLanche"));

            // Compra preenchido diretamente do SELECT
            Compra compra = new Compra();
            compra.setCodCompra(rs.getInt("compra_codCompra")); // alias correto
            /*Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                compra.setDataCompra(tsDataCompra.toLocalDateTime());
            }*/
            objCL.setCompra(compra);

            // Visitante dentro da Compra
            Visitante visitante = new Visitante();
            visitante.setNomeVisitante(rs.getString("nomeVisitante"));
            compra.setVisitante(visitante); // Assumindo que Compra tem um atributo Visitante

            // Lanche preenchido diretamente do SELECT
            Lanche lanche = new Lanche();
            lanche.setCodLanche(rs.getInt("lanche_codLanche")); // alias correto
            lanche.setNomeLanche(rs.getString("nomeLanche"));
            lanche.setPrecoLanche(rs.getDouble("precoLanche")); // ESSENCIAL!
            objCL.setLanche(lanche);

            return objCL;
        }
    }

    public List<CompraLanche> buscarTodasCompraLanches() {
        String sql = "SELECT cl.*, c.codCompra, v.nomeVisitante, l.* FROM compraslanche AS cl INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante INNER JOIN lanche as l on cl.lanche_codLanche = l.cod_Lanche;";
        return buscarTodos(sql, new CompraLancheRowMapper());
    }

    public CompraLanche buscarCompraLanchePorId(int id) {
        String sql = "SELECT cl.*, c.codCompra, v.nomeVisitante, l.* FROM compraslanche AS cl INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante INNER JOIN lanche as l on cl.lanche_codLanche = l.cod_Lanche where cl.codCompraLanche = ?;";
        return buscarPorId(sql, new CompraLancheRowMapper());
    }

    public List<CompraLanche> listarPorCodCompra(int codCompra) {
        String sql = "SELECT cl.*, c.codCompra, v.nomeVisitante, l.codLanche, l.nomeLanche, l.precoLanche "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE cl.compra_codCompra = ?";
        return buscarTodosComParametro(sql, new CompraLancheRowMapper(), codCompra);
    }

    public List<CompraLanche> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT cl.*, c.codCompra, c.dataCompra, v.nomeVisitante, "
                + "l.codLanche, l.nomeLanche, l.precoLanche "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE c.dataCompra BETWEEN ? AND ?";

        return buscarTodosComParametro(sql, rs -> {
            CompraLanche cl = new CompraLanche();

            // Compra
            Compra c = new Compra();
            Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                c.setDataCompra(tsDataCompra.toLocalDateTime());
            }
            c.setCodCompra(rs.getInt("codCompra"));

            // Visitante
            Visitante v = new Visitante();
            v.setNomeVisitante(rs.getString("nomeVisitante"));
            c.setVisitante(v);

            // Lanche
            Lanche l = new Lanche();
            l.setCodLanche(rs.getInt("codLanche"));
            l.setNomeLanche(rs.getString("nomeLanche"));
            l.setPrecoLanche(rs.getDouble("precoLanche"));

            // CompraLanche
            cl.setCompra(c);
            cl.setLanche(l);
            cl.setQuantidadeLanche(rs.getInt("quantidadeLanche"));

            return cl;
        }, java.sql.Timestamp.valueOf(inicio), java.sql.Timestamp.valueOf(fim));
    }

    //funciona pronto para o relatório
    public List<CompraLanche> maisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT cl.lanche_codLanche, l.nomeLanche, SUM(cl.quantidadeLanche) AS totalVendido "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE c.dataCompra BETWEEN ? AND ? "
                + "GROUP BY cl.lanche_codLanche, l.nomeLanche "
                + "ORDER BY totalVendido DESC";

        return buscarTodosComParametro(sql, rs -> {
            CompraLanche cl = new CompraLanche();
            Lanche lanche = new Lanche();
            lanche.setCodLanche(rs.getInt("lanche_codLanche"));
            lanche.setNomeLanche(rs.getString("nomeLanche"));
            cl.setLanche(lanche);
            cl.setQuantidadeLanche(rs.getInt("totalVendido"));
            return cl;
        }, Timestamp.valueOf(inicio), Timestamp.valueOf(fim));
    }

    // funciona retorna apenas 1 valor, não faz sentido se tenho em compradao
    public double receitaMensal(int mes, int ano) {
        String sql = "SELECT SUM(cl.quantidadeLanche * l.precoLanche) AS totalReceita "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";
        return buscarDouble(sql, mes, ano);
    }

}
