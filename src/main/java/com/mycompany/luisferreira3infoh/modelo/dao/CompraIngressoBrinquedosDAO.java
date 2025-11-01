package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CompraIngressoBrinquedosDAO extends GenericoDAO<CompraIngressoBrinquedos> {

    public void salvar(CompraIngressoBrinquedos objCIB) {
        String sql = "INSERT INTO compra_ingressobrinquedos (compra_codCompra, ingressoBrinquedos_codIngresso, quantIngBrinq) VALUES (?, ?, ?);";
        save(sql,
                objCIB.getCompra().getCodCompra(),
                objCIB.getIb().getCodIngresso(),
                objCIB.getQuantIngBrinq());
    }

    public void alterar(CompraIngressoBrinquedos objCIB) {
        String sql = "UPDATE compra_ingressobrinquedos SET compra_codCompra = ?, ingressoBrinquedos_codIngresso = ?, quantIngBrinq = ? WHERE codCompraBrinquedos = ?;";
        save(sql,
                objCIB.getCompra().getCodCompra(),
                objCIB.getIb().getCodIngresso(),
                objCIB.getQuantIngBrinq(),
                objCIB.getCodCompraBrinquedos());
    }

    public void excluir(CompraIngressoBrinquedos objCIB) {
        String sql = "DELETE FROM compra_ingressobrinquedos WHERE codCompraBrinquedos = ?;";
        save(sql, objCIB.getCodCompraBrinquedos());
    }

    private static class CompraIngressoBrinquedosRowMapper implements RowMapper<CompraIngressoBrinquedos> {

        @Override
        public CompraIngressoBrinquedos mapRow(ResultSet rs) throws SQLException {
            CompraIngressoBrinquedos objCIB = new CompraIngressoBrinquedos();
            objCIB.setCodCompraBrinquedos(rs.getInt("codCompraBrinquedos"));
            objCIB.setQuantIngBrinq(rs.getInt("quantIngBrinq"));

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
            objCIB.setCompra(compra);

            // Brinquedo
            Brinquedo brinquedo = new Brinquedo();
            brinquedo.setCodBrinquedo(rs.getInt("codBrinquedo"));
            brinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));

            // IngressoBrinquedos com brinquedo dentro
            IngressoBrinquedos ingresso = new IngressoBrinquedos();
            ingresso.setCodIngresso(rs.getInt("codIngresso"));
            ingresso.setValor(rs.getDouble("valor"));
            ingresso.setBrinquedo(brinquedo);
            objCIB.setIb(ingresso);

            return objCIB;
        }
    }

    public List<CompraIngressoBrinquedos> buscarTodasComprasIB() {
        String sql = "SELECT cib.*, c.codCompra, v.nomeVisitante,\n"
                + "                   ib.codIngresso,ib.valor, b.codBrinquedo, b.nomeBrinquedo\n"
                + "            FROM compra_ingressobrinquedos AS cib\n"
                + "            INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra\n"
                + "            INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "            INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso\n"
                + "            INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo;";
        return buscarTodos(sql, new CompraIngressoBrinquedosRowMapper());
    }

    public CompraIngressoBrinquedos buscarCompraIBPorId(int id) {
        String sql = "SELECT cib.*, c.codCompra, v.nomeVisitante,\n"
                + "                   ib.codIngresso,ib.valor, b.codBrinquedo, b.nomeBrinquedo\n"
                + "            FROM compra_ingressobrinquedos AS cib\n"
                + "            INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra\n"
                + "            INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante\n"
                + "            INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso\n"
                + "            INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo\n"
                + "            WHERE cib.codCompraBrinquedos = ?;";

        return buscarPorId(sql, new CompraIngressoBrinquedosRowMapper(), id);
    }

    public List<CompraIngressoBrinquedos> listarPorCodCompra(int codCompra) {
        String sql = "SELECT cib.*, c.codCompra, v.nomeVisitante, "
                + "ib.codIngresso,ib.valor, b.codBrinquedo, b.nomeBrinquedo "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo "
                + "WHERE cib.compra_codCompra = ?";
        return buscarTodosComParametro(sql, new CompraIngressoBrinquedosRowMapper(), codCompra);
    }

    // verificar se os dados estão na ordem correta 
    public List<CompraIngressoBrinquedos> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT cib.*, c.codCompra, c.dataCompra, v.nomeVisitante, ib.codIngresso, ib.valor, b.codBrinquedo, b.nomeBrinquedo "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN visitante AS v ON c.visitante_codVisitante = v.codVisitante "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo "
                + "WHERE c.dataCompra BETWEEN ? AND ?";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();

            Compra c = new Compra();
            Timestamp tsDataCompra = rs.getTimestamp("dataCompra");
            if (tsDataCompra != null) {
                c.setDataCompra(tsDataCompra.toLocalDateTime());
            }
            c.setCodCompra(rs.getInt("codCompra"));

            Brinquedo brinquedo = new Brinquedo();
            brinquedo.setCodBrinquedo(rs.getInt("codBrinquedo"));
            brinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));

            IngressoBrinquedos ingresso = new IngressoBrinquedos();
            ingresso.setBrinquedo(brinquedo);
            ingresso.setValor(rs.getDouble("valor"));

            // Se quiser, você pode setar também o nome do visitante
            Visitante v = new Visitante();
            v.setNomeVisitante(rs.getString("nomeVisitante"));
            c.setVisitante(v);

            cib.setIb(ingresso);
            cib.setQuantIngBrinq(rs.getInt("quantIngBrinq"));
            cib.setCompra(c);

            return cib;
        }, java.sql.Timestamp.valueOf(inicio), java.sql.Timestamp.valueOf(fim));
    }

    // Quantidade vendida por brinquedo, colocar um filtro de data
    public List<CompraIngressoBrinquedos> quantidadePorBrinquedo(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT ib.brinquedo_codBrinquedo AS codBrinquedo, b.nomeBrinquedo, SUM(cib.quantIngBrinq) AS totalVendido "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo "
                + "WHERE c.dataCompra BETWEEN ? AND ? "
                + "GROUP BY ib.brinquedo_codBrinquedo, b.nomeBrinquedo "
                + "ORDER BY totalVendido DESC";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();
            Brinquedo brinquedo = new Brinquedo();
            brinquedo.setCodBrinquedo(rs.getInt("codBrinquedo"));
            brinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));
            IngressoBrinquedos ingresso = new IngressoBrinquedos();
            ingresso.setBrinquedo(brinquedo);
            cib.setIb(ingresso);
            cib.setQuantIngBrinq(rs.getInt("totalVendido"));
            return cib;
        }, Timestamp.valueOf(inicio), Timestamp.valueOf(fim));
    }

// Receita mensal de ingressos de brinquedos, não faz sentido se tenho em compradao
    public double receitaMensal(int mes, int ano) {
        String sql = "SELECT SUM(cib.quantIngBrinq * ib.valor) AS totalReceita "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "WHERE MONTH(c.dataCompra) = ? AND YEAR(c.dataCompra) = ?";
        return buscarDouble(sql, mes, ano);
    }

    //mais vendidos
    public List<CompraIngressoBrinquedos> maisVendidos(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT "
                + "    ib.codIngresso, "
                + "    b.nomeBrinquedo, "
                + "    ib.tipoIngresso, "
                + "    SUM(cib.quantIngBrinq) AS totalVendido "
                + "FROM compra_ingressobrinquedos AS cib "
                + "INNER JOIN compra AS c ON cib.compra_codCompra = c.codCompra "
                + "INNER JOIN ingressobrinquedos AS ib ON cib.ingressoBrinquedos_codIngresso = ib.codIngresso "
                + "INNER JOIN brinquedo AS b ON ib.brinquedo_codBrinquedo = b.codBrinquedo "
                + "WHERE c.dataCompra BETWEEN ? AND ? "
                + "GROUP BY ib.codIngresso, ib.tipoIngresso, b.nomeBrinquedo "
                + "ORDER BY totalVendido DESC";

        return buscarTodosComParametro(sql, rs -> {
            CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();

            IngressoBrinquedos ingresso = new IngressoBrinquedos();
            ingresso.setCodIngresso(rs.getInt("codIngresso"));
            ingresso.setTipoIngresso(rs.getString("tipoIngresso"));

            Brinquedo brinquedo = new Brinquedo();
            brinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));

            ingresso.setBrinquedo(brinquedo);
            cib.setIb(ingresso);

            // aqui usamos o campo "quantIngBrinq" como base, mas agora é o somatório "totalVendido"
            cib.setQuantIngBrinq(rs.getInt("totalVendido"));

            return cib;
        }, Timestamp.valueOf(inicio), Timestamp.valueOf(fim));
    }

}
