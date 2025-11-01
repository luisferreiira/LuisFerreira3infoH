package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDAO extends GenericoDAO<Categoria> {

    public void salvar(Categoria objCategoria) {
        String sql = "INSERT INTO categoria (nomeCategoria, descricao) VALUES (?, ?);";
        save(sql, objCategoria.getNomeCategoria(), objCategoria.getDescricao());
    }

    public void alterar(Categoria objCategoria) {
        String sql = "UPDATE categoria SET nomeCategoria = ?, descricao = ? WHERE codCategoria = ?;";
        save(sql, objCategoria.getNomeCategoria(), objCategoria.getDescricao(), objCategoria.getCodCategoria());
    }

    public void excluir(Categoria objCategoria) {
        String sql = "DELETE FROM categoria WHERE codCategoria = ?;";
        save(sql, objCategoria.getCodCategoria());
    }

    private static class CategoriaRowMapper implements RowMapper<Categoria> {
        @Override
        public Categoria mapRow(ResultSet rs) throws SQLException {
            Categoria objCategoria = new Categoria();
            objCategoria.setCodCategoria(rs.getInt("codCategoria"));
            objCategoria.setNomeCategoria(rs.getString("nomeCategoria"));
            objCategoria.setDescricao(rs.getString("descricao"));
            return objCategoria;
        }
    }

    public List<Categoria> buscarTodasCategorias() {
        String sql = "SELECT * FROM categoria;";
        return buscarTodos(sql, new CategoriaRowMapper());
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE codCategoria = ?;";
        return buscarPorId(sql, new CategoriaRowMapper(), id);
    }
}

