package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Tokens_Redefinicao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tokens_RedefinicaoDAO extends GenericoDAO<Tokens_Redefinicao> {

    // Salvar token
    public void salvar(Tokens_Redefinicao token) {
        String sql = "INSERT INTO tokens_redefinicao (email, token, tipo_usuario, expiracao, criado_em) VALUES (?, ?, ?, ?,?);";
        save(sql, token.getEmail(), token.getToken(), token.getTipoUsuario(), Timestamp.valueOf(token.getExpiracao()), Timestamp.valueOf(token.getCriadoEm()));
    }

    // Buscar token válido pelo token e tipo de usuário
    public Tokens_Redefinicao buscarPorToken(String tokenStr, String tipoUsuario) {
        String sql = "SELECT * FROM tokens_redefinicao WHERE token = ? AND expiracao >= NOW()";
        List<Object> params = new ArrayList<>();
        params.add(tokenStr);

        if (tipoUsuario != null) {
            sql += " AND tipo_usuario = ?";
            params.add(tipoUsuario);
        }

        List<Tokens_Redefinicao> lista = buscarTodosComParametro(sql, new TokenRowMapper(), params.toArray());
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Tokens_Redefinicao buscarPorToken2(String tokenStr, String tipoUsuario) {
        String sql = "SELECT * FROM tokens_redefinicao WHERE token = ?";
        List<Object> params = new ArrayList<>();
        params.add(tokenStr);

        if (tipoUsuario != null) {
            sql += " AND tipo_usuario = ?";
            params.add(tipoUsuario);
        }

        List<Tokens_Redefinicao> lista = buscarTodosComParametro(sql, new TokenRowMapper(), params.toArray());

        if (lista.isEmpty()) {
            return null;
        }

        Tokens_Redefinicao token = lista.get(0);

        ZonedDateTime agoraBrasil = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        // Comparar expiracao em Java
        if (token.getExpiracao() != null && token.getExpiracao().isBefore(LocalDateTime.now())) {
            // Token expirado
            apagarToken(tokenStr); // opcional: remove token expirado
            return null;
        }

        return token;
    }

    // Apagar token após uso
    public void apagarToken(String tokenStr) {
        String sql = "DELETE FROM tokens_redefinicao WHERE token = ?;";
        save(sql, tokenStr);
    }

    // Buscar todos os tokens (sem filtro)
    public List<Tokens_Redefinicao> buscarTodosTokens() {
        String sql = "SELECT * FROM tokens_redefinicao;";
        return buscarTodos(sql, new TokenRowMapper());
    }

    // RowMapper para mapear ResultSet para Tokens_Redefinicao
    private static class TokenRowMapper implements RowMapper<Tokens_Redefinicao> {

        @Override
        public Tokens_Redefinicao mapRow(ResultSet rs) throws SQLException {
            Tokens_Redefinicao token = new Tokens_Redefinicao();
            token.setCodTokensRedefinicao(rs.getInt("codTokens_Redefinicao"));
            token.setEmail(rs.getString("email"));
            token.setToken(rs.getString("token"));
            token.setTipoUsuario(rs.getString("tipo_usuario"));

            Timestamp expiracaoTS = rs.getTimestamp("expiracao");
            token.setExpiracao(expiracaoTS != null ? expiracaoTS.toLocalDateTime() : null);

            Timestamp criadoTS = rs.getTimestamp("criado_em");
            token.setCriadoEm(criadoTS != null ? criadoTS.toLocalDateTime() : null);

            return token;
        }
    }
}
