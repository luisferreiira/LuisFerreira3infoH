package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CompraIngressoEventosDAO extends GenericoDAO<CompraIngressoEventos> {

    public void salvar(CompraIngressoEventos objCIE) {
        String sql = "INSERT INTO compra_ingressoeventos (compra_codCompra, ingressoEventos_codIngresso, quantIngEvent) VALUES (?, ?, ?);";
        save(sql,
                objCIE.getCompra().getCodCompra(),
                objCIE.getIe().getCodIngresso(),
                objCIE.getQuantIngEvent());
    }

    public void alterar(CompraIngressoEventos objCIE) {
        String sql = "UPDATE compra_ingressoeventos SET compra_codCompra = ?, ingressoEventos_codIngresso = ?, quantIngEvent = ? WHERE codCompraEventos = ?;";
        save(sql,
                objCIE.getCompra().getCodCompra(),
                objCIE.getIe().getCodIngresso(),
                objCIE.getQuantIngEvent(),
                objCIE.getCodCompraEventos());
    }

    public void excluir(CompraIngressoEventos objCIE) {
        String sql = "DELETE FROM compra_ingressoeventos WHERE codCompraEventos = ?;";
        save(sql, objCIE.getCodCompraEventos());
    }

    private static class CompraIngressoEventosRowMapper implements RowMapper<CompraIngressoEventos> {

        @Override
        public CompraIngressoEventos mapRow(ResultSet rs) throws SQLException {
            CompraIngressoEventos objCIE = new CompraIngressoEventos();
            objCIE.setCodCompraEventos(rs.getInt("codCompraEventos"));
            objCIE.setQuantIngEvent(rs.getInt("quantIngEvent"));

            // Compra
            Compra compra = new Compra();
            /*Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                compra.setDataCompra(tsDataCompra.toLocalDateTime());
            }*/
            compra.setCodCompra(rs.getInt("codCompra"));

            // Visitante
            Visitante visitante = new Visitante();
            visitante.setNomeVisitante(rs.getString("nomeVisitante"));
            compra.setVisitante(visitante);
            objCIE.setCompra(compra);

            // Evento
            Eventos evento = new Eventos();
            evento.setCodEventos(rs.getInt("codEventos"));
            evento.setNomeEvento(rs.getString("nomeEvento"));

            // IngressoEventos com evento dentro
            IngressoEventos ingresso = new IngressoEventos();
            ingresso.setCodIngresso(rs.getInt("codIngresso"));
            ingresso.setValor(rs.getDouble("valor"));
            ingresso.setEvento(evento);
            objCIE.setIe(ingresso);

            return objCIE;
        }
    }

    public List<CompraIngressoEventos> buscarTodasCompraIE() {
        String sql = "SELECT cie.*, c.codCompra, v.nomeVisitante,\n"
                + "                   ie.codIngresso,ie.valor, e.codEventos, e.nomeEvento\n"
                + "            FROM compra_ingressoeventos AS cie\n"
                + "            INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra\n"
                + "            INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "            INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso\n"
                + "            INNER JOIN eventos AS e ON ie.eventos_codEventos = e.codEventos;";
        return buscarTodos(sql, new CompraIngressoEventosRowMapper());
    }

    public CompraIngressoEventos buscarCompraIEPorId(int id) {
        String sql = "SELECT cie.*, c.codCompra, v.nomeVisitante,\n"
                + "                   ie.codIngresso, ie.valor, e.codEventos, e.nomeEvento\n"
                + "            FROM compra_ingressoeventos AS cie\n"
                + "            INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra\n"
                + "            INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "            INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso\n"
                + "            INNER JOIN eventos AS e ON ie.eventos_codEventos = e.codEventos\n"
                + "            WHERE cie.codCompraEventos = ?;";

        return buscarPorId(sql, new CompraIngressoEventosRowMapper(), id);
    }

    public List<CompraIngressoEventos> listarPorCodCompra(int codCompra) {
        String sql = "SELECT cie.*, c.codCompra, v.nomeVisitante, "
                + "ie.codIngresso, ie.valor, e.codEventos, e.nomeEvento "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "INNER JOIN eventos AS e ON ie.eventos_codEventos = e.codEventos "
                + "WHERE cie.compra_codCompra = ?";
        return buscarTodosComParametro(sql, new CompraIngressoEventosRowMapper(), codCompra);
    }

    // verificar se organiza as informações na ordem correta
    public List<CompraIngressoEventos> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT cie.*, c.codCompra, c.dataCompra, v.nomeVisitante, ie.codIngresso, ie.valor, e.codEventos, e.nomeEvento "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "INNER JOIN eventos AS e ON ie.eventos_codEventos = e.codEventos "
                + "WHERE c.dataCompra BETWEEN ? AND ?";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoEventos cie = new CompraIngressoEventos();
            Compra c = new Compra();
            c.setCodCompra(rs.getInt("codCompra"));
            Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                c.setDataCompra(tsDataCompra.toLocalDateTime());
            }

            Visitante v = new Visitante();
            v.setNomeVisitante(rs.getString("nomeVisitante"));
            c.setVisitante(v);

            Eventos evento = new Eventos();
            evento.setCodEventos(rs.getInt("codEventos"));
            evento.setNomeEvento(rs.getString("nomeEvento"));

            IngressoEventos ingresso = new IngressoEventos();
            ingresso.setEvento(evento);
            ingresso.setValor(rs.getDouble("valor"));

            cie.setIe(ingresso);
            cie.setCompra(c);
            cie.setQuantIngEvent(rs.getInt("quantIngEvent"));

            return cie;
        }, java.sql.Timestamp.valueOf(inicio), java.sql.Timestamp.valueOf(fim));
    }

// Quantidade vendida por evento, pode fazer sentido com filtro de data
    public List<CompraIngressoEventos> quantidadePorEvento(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT ie.eventos_codEventos AS codEventos, e.nomeEvento, SUM(cie.quantIngEvent) AS totalVendido "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "INNER JOIN evento AS e ON ie.eventos_codEventos = e.codEventos"
                + "WHERE c.dataCompra BETWEEN ? AND ? "
                + "GROUP BY ie.eventos_codEventos, e.nomeEvento "
                + "ORDER BY totalVendido DESC";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoEventos cie = new CompraIngressoEventos();
            Eventos evento = new Eventos();
            evento.setCodEventos(rs.getInt("codEventos"));
            evento.setNomeEvento(rs.getString("nomeEvento"));
            IngressoEventos ingresso = new IngressoEventos();
            ingresso.setEvento(evento);
            cie.setIe(ingresso);
            cie.setQuantIngEvent(rs.getInt("totalVendido"));
            return cie;
        }, Timestamp.valueOf(inicio), Timestamp.valueOf(fim));
    }

// Receita mensal de ingressos de eventos, não faz sentido se tenho no compradao
    public double receitaMensal(int mes, int ano) {
        String sql = "SELECT SUM(cie.quantIngEvento * ie.valor) AS totalReceita "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";
        return buscarDouble(sql, mes, ano);
    }

    //mais vendidos
    public List<CompraIngressoEventos> maisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT "
                + "ie.codIngresso, "
                + "e.nomeEvento, "
                + "ie.tipoIngresso, "
                + "SUM(cie.quantIngEvent) AS totalVendido "
                + "FROM compra_ingressoeventos AS cie "
                + "INNER JOIN compra AS c ON cie.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressoeventos AS ie ON cie.ingressoEventos_codIngresso = ie.codIngresso "
                + "INNER JOIN eventos AS e ON ie.eventos_codEventos = e.codEventos "
                + "WHERE c.dataCompra BETWEEN ? AND ? "
                + "GROUP BY ie.codIngresso, ie.tipoIngresso, e.nomeEvento "
                + "ORDER BY totalVendido DESC";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoEventos cie = new CompraIngressoEventos();

            IngressoEventos ingresso = new IngressoEventos();
            ingresso.setCodIngresso(rs.getInt("codIngresso"));
            ingresso.setTipoIngresso(rs.getString("tipoIngresso"));

            // Evento associado
            Eventos evento = new Eventos();
            evento.setNomeEvento(rs.getString("nomeEvento"));
            ingresso.setEvento(evento);

            // popula objeto CompraIngressoEventos
            cie.setIe(ingresso);

            // total vendido (pode ser grande; se preferir use getLong)
            cie.setQuantIngEvent(rs.getInt("totalVendido"));

            return cie;
        }, java.sql.Timestamp.valueOf(inicio), java.sql.Timestamp.valueOf(fim));
    }

}
