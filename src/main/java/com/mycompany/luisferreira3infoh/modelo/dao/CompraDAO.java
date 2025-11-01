/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class CompraDAO extends GenericoDAO<Compra> {

    public void salvar(Compra objCompra) {
        String sql = "INSERT INTO compra (tipoPagamento, dataCompra, visitante_codVisitante, funcionario_codFuncionario ) VALUES (?, ?,?,?);";
        save(sql, objCompra.getTipoPagamento(),
                Timestamp.valueOf(objCompra.getDataCompra()),
                objCompra.getVisitante().getCodVisitante(),
                objCompra.getFuncionario().getCodFuncionario());
    }

    public void alterar(Compra objCompra) {
        String sql = "Update compra set tipoPagamento = ?, dataCompra = ?,visitante_codVisitante = ?,funcionario_codFuncionario = ? where codCompra = ?";
        save(sql, objCompra.getTipoPagamento(),
                Timestamp.valueOf(objCompra.getDataCompra()),
                objCompra.getVisitante().getCodVisitante(),
                objCompra.getFuncionario().getCodFuncionario(),
                objCompra.getCodCompra());
    }

    public void excluir(Compra objCompra) {
        String sql = "DELETE FROM compra WHERE codCompra = ?;";
        save(sql, objCompra.getCodCompra());
    }

    private static class CompraRowMapper implements RowMapper<Compra> {

        @Override
        public Compra mapRow(ResultSet rs) throws SQLException {
            Compra objCompra = new Compra();
            objCompra.setCodCompra(rs.getInt("codCompra"));
            objCompra.setTipoPagamento(rs.getString("tipoPagamento"));

            Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                objCompra.setDataCompra(tsDataCompra.toLocalDateTime());
            }

            // Visitante preenchido diretamente do SELECT
            Visitante visitante = new Visitante();
            visitante.setCodVisitante(rs.getInt("visitante_codVisitante")); // alias correto
            visitante.setNomeVisitante(rs.getString("nomeVisitante"));
            visitante.setEmail(rs.getString("email"));
            visitante.setSenha(rs.getString("senha"));
            objCompra.setVisitante(visitante);

            // Funcionário preenchido diretamente do SELECT
            Funcionario funcionario = new Funcionario();
            funcionario.setCodFuncionario(rs.getInt("funcionario_codFuncionario")); // alias correto
            funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
            objCompra.setFuncionario(funcionario);

            return objCompra;
        }
    }

    public List<Compra> buscarTodasCompras() {
        String sql = "SELECT c.*, v.nomeVisitante, v.email, v.senha, f.nomeFuncionario FROM compra as c inner JOIN visitante as v ON c.visitante_codVisitante = v.codVisitante INNER JOIN funcionario as f ON c.funcionario_codFuncionario = f.codFuncionario ORDER BY codCompra ASC;";
        return buscarTodos(sql, new CompraRowMapper());
    }

    public Compra buscarCompraPorId(int id) {
        String sql = "SELECT c.*, v.nomeVisitante, v.email, v.senha, f.nomeFuncionario "
                + "FROM compra AS c "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN funcionario AS f ON c.funcionario_codFuncionario = f.codFuncionario "
                + "WHERE c.codCompra = ?";
        return buscarPorId(sql, new CompraRowMapper(), id);
    }

    public int salvarRetornandoID(Compra objCompra) {
        String sql = "INSERT INTO compra (tipoPagamento, dataCompra, visitante_codVisitante, funcionario_codFuncionario) VALUES (?, ?, ?, ?);";

        // Gera a data já com o fuso correto
        LocalDateTime dataCompraBrasil = LocalDateTime.now(java.time.ZoneId.of("America/Sao_Paulo"));
        objCompra.setDataCompra(dataCompraBrasil);

        return salvarRetornandoID(sql,
                objCompra.getTipoPagamento(),
                java.sql.Timestamp.valueOf(objCompra.getDataCompra()),
                objCompra.getVisitante().getCodVisitante(),
                objCompra.getFuncionario().getCodFuncionario()
        );
    }

    public List<Compra> listarPorVisitante(int codVisitante) {
        String sql = "SELECT c.*, v.*, f.* \n"
                + "FROM compra AS c\n"
                + "JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "JOIN funcionario AS f ON c.funcionario_codFuncionario = f.codFuncionario\n"
                + "WHERE c.visitante_codVisitante = ?;";
        return buscarTodosComParametro(sql, new CompraRowMapper(), codVisitante);
    }

    public List<Compra> listarPorVisitanteComItens(int codVisitante) {
        String sql = "SELECT c.*, v.*, f.* \n"
                + "FROM compra AS c\n"
                + "JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "JOIN funcionario AS f ON c.funcionario_codFuncionario = f.codFuncionario\n"
                + "WHERE c.visitante_codVisitante = ?;";
        List<Compra> compras = buscarTodosComParametro(sql, new CompraRowMapper(), codVisitante);

        CompraLancheDAO compraLancheDAO = new CompraLancheDAO();
        CompraIngressoBrinquedosDAO compraBrinquedoDAO = new CompraIngressoBrinquedosDAO();
        CompraIngressoEventosDAO compraEventoDAO = new CompraIngressoEventosDAO();

        for (Compra compra : compras) {
            List<CompraLanche> lanches = compraLancheDAO.listarPorCodCompra(compra.getCodCompra());
            List<CompraIngressoBrinquedos> brinquedos = compraBrinquedoDAO.listarPorCodCompra(compra.getCodCompra());
            List<CompraIngressoEventos> eventos = compraEventoDAO.listarPorCodCompra(compra.getCodCompra());

            compra.setListaLanches(lanches);
            compra.setListaCIB(brinquedos);
            compra.setListaCIE(eventos);
        }

        return compras;
    }

    public List<Compra> listarPorVisitanteComItensPorPeriodo(int codVisitante, LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT c.*, v.*, f.* "
                + "FROM compra AS c "
                + "JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "JOIN funcionario AS f ON c.funcionario_codFuncionario = f.codFuncionario "
                + "WHERE c.visitante_codVisitante = ? "
                + "AND c.dataCompra BETWEEN ? AND ? "
                + "ORDER BY c.dataCompra ASC";

        List<Compra> compras = buscarTodosComParametro(sql, new CompraRowMapper(),
                codVisitante,
                java.sql.Timestamp.valueOf(inicio),
                java.sql.Timestamp.valueOf(fim));

        CompraLancheDAO compraLancheDAO = new CompraLancheDAO();
        CompraIngressoBrinquedosDAO compraBrinquedoDAO = new CompraIngressoBrinquedosDAO();
        CompraIngressoEventosDAO compraEventoDAO = new CompraIngressoEventosDAO();

        for (Compra compra : compras) {
            compra.setListaLanches(compraLancheDAO.listarPorCodCompra(compra.getCodCompra()));
            compra.setListaCIB(compraBrinquedoDAO.listarPorCodCompra(compra.getCodCompra()));
            compra.setListaCIE(compraEventoDAO.listarPorCodCompra(compra.getCodCompra()));
        }

        return compras;
    }

    // Financeiro Diário: soma de lanches, brinquedos e eventos de um dia específico
    public double[] totalPorDia(LocalDateTime dia) {
        String sqlLanches = "SELECT SUM(cl.quantidadeLanche * l.precoLanche) AS total "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE DATE(c.dataCompra) = ?";

        String sqlBrinquedos = "SELECT SUM(cib.quantIngBrinq * ib.valor) AS total "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "WHERE DATE(c.dataCompra) = ?";

        String sqlEventos = "SELECT SUM(cie.quantIngEvent * ie.valor) AS total "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "WHERE DATE(c.dataCompra) = ?";

        double totalLanches = buscarDouble(sqlLanches, java.sql.Timestamp.valueOf(dia));
        double totalBrinquedos = buscarDouble(sqlBrinquedos, java.sql.Timestamp.valueOf(dia));
        double totalEventos = buscarDouble(sqlEventos, java.sql.Timestamp.valueOf(dia));

        return new double[]{totalLanches, totalBrinquedos, totalEventos};
    }

// Receita Mensal por Categoria: retorna array com [lanche, brinquedo, evento] retorna apenas o valor total de cada
    // parte de um relatório de comparação
    public double[] receitaMensalPorCategoria(int mes, int ano) {
        String sqlLanches = "SELECT SUM(cl.quantidadeLanche * l.precoLanche) AS total "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";

        String sqlBrinquedos = "SELECT SUM(cib.quantIngBrinq * ib.valor) AS total "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";

        String sqlEventos = "SELECT SUM(cie.quantIngEvent * ie.valor) AS total "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";

        double totalLanches = buscarDouble(sqlLanches, mes, ano);
        double totalBrinquedos = buscarDouble(sqlBrinquedos, mes, ano);
        double totalEventos = buscarDouble(sqlEventos, mes, ano);

        return new double[]{totalLanches, totalBrinquedos, totalEventos};
    }

    // receita anual
    public double[] receitaAnualPorCategoria(int ano) {
        String sqlLanches = "SELECT SUM(cl.quantidadeLanche * l.precoLanche) AS total "
                + "FROM compraslanche AS cl "
                + "INNER JOIN compra AS c ON cl.compra_codCompra = c.codCompra "
                + "INNER JOIN lanche AS l ON cl.lanche_codLanche = l.codLanche "
                + "WHERE YEAR(c.dataCompra) = ?";

        String sqlBrinquedos = "SELECT SUM(cib.quantIngBrinq * ib.valor) AS total "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "WHERE YEAR(c.dataCompra) = ?";

        String sqlEventos = "SELECT SUM(cie.quantIngEvent * ie.valor) AS total "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "WHERE YEAR(c.dataCompra) = ?";

        double totalLanches = buscarDouble(sqlLanches, ano);
        double totalBrinquedos = buscarDouble(sqlBrinquedos, ano);
        double totalEventos = buscarDouble(sqlEventos, ano);

        return new double[]{totalLanches, totalBrinquedos, totalEventos};
    }

    // somas das receitas mensais das categorias
    public double receitaTotalMes(int mes, int ano) {
        double[] receita = receitaMensalPorCategoria(mes, ano); // seu método existente
        return receita[0] + receita[1] + receita[2]; // soma lanches + brinquedos + eventos
    }

    // tabela chave-valor
    public Map<String, Double> compararSalarioVendas(int mes, int ano) {
        FuncionarioDAO fDAO = new FuncionarioDAO();
        double totalSalarios = fDAO.totalSalariosAtivosPorMes(mes, ano);
        double totalVendas = receitaTotalMes(mes, ano);

        Map<String, Double> resultado = new HashMap<>();
        resultado.put("Total Salarios", totalSalarios);
        resultado.put("Total Vendas", totalVendas);
        resultado.put("Lucro/Prejuizo", totalVendas - totalSalarios);

        return resultado;
    }

    // relatório ainda não implementado
    public List<Compra> listarPorFuncionarioComItens(int codFuncionario, LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT c.*, v.*, f.* "
                + "FROM compra AS c "
                + "JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "JOIN funcionario AS f ON c.funcionario_codFuncionario = f.codFuncionario "
                + "WHERE c.funcionario_codFuncionario = ? "
                + "AND c.dataCompra BETWEEN ? AND ? "
                + "ORDER BY c.dataCompra ASC";

        List<Compra> compras = buscarTodosComParametro(sql, new CompraRowMapper(),
                codFuncionario,
                java.sql.Timestamp.valueOf(inicio),
                java.sql.Timestamp.valueOf(fim));

        CompraLancheDAO compraLancheDAO = new CompraLancheDAO();
        CompraIngressoBrinquedosDAO compraBrinquedoDAO = new CompraIngressoBrinquedosDAO();
        CompraIngressoEventosDAO compraEventoDAO = new CompraIngressoEventosDAO();

        for (Compra compra : compras) {
            compra.setListaLanches(compraLancheDAO.listarPorCodCompra(compra.getCodCompra()));
            compra.setListaCIB(compraBrinquedoDAO.listarPorCodCompra(compra.getCodCompra()));
            compra.setListaCIE(compraEventoDAO.listarPorCodCompra(compra.getCodCompra()));
        }

        return compras;
    }

}
