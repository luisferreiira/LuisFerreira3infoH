package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaRowMapper implements RowMapper<Categoria> {
    @Override
    public Categoria mapRow(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setCodCategoria(rs.getInt("codCategoria"));
        c.setNomeCategoria(rs.getString("nomeCategoria"));
        c.setDescricao(rs.getString("descricao"));
        return c;
    }
}
