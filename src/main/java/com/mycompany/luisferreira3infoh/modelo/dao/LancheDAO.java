/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Categoria;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Lanche;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author
 */
public class LancheDAO extends GenericoDAO <Lanche>{
    
    public void salvar(Lanche objLanche)
    {
        String sql = "INSERT INTO lanche (nomeLanche, precoLanche , descricao ,lucroLanches, categoria_codCategoria) VALUES (?,?,?,?,?);";
        save(sql, objLanche.getNomeLanche(),
                objLanche.getPrecoLanche(),
                objLanche.getDescricao() != null ? objLanche.getDescricao() : null,
                objLanche.getLucroLanches() != null ? objLanche.getLucroLanches() : null,
                objLanche.getCategoria().getCodCategoria());
    }
    
    public void alterar(Lanche objLanche)
    {
        String sql = "Update lanche set nomeLanche = ?, precoLanche = ?,descricao = ?,lucroLanches = ?, categoria_codCategoria = ? where codLanche = ?";
        save(sql, objLanche.getNomeLanche(),
                objLanche.getPrecoLanche(),
                objLanche.getDescricao() != null ? objLanche.getDescricao() : null,
                objLanche.getLucroLanches() != null ? objLanche.getLucroLanches() : null,
                objLanche.getCategoria().getCodCategoria(),
                objLanche.getCodLanche());
    }
    
    public void excluir(Lanche objLanche)
    {
        String sql = "DELETE FROM lanche WHERE codLanche = ?;";
        save(sql, objLanche.getCodLanche());
    }
    
    private static class LancheRowMapper implements RowMapper<Lanche> {
    @Override
    public Lanche mapRow(ResultSet rs) throws SQLException {
        Lanche objLanche = new Lanche();
        objLanche.setCodLanche(rs.getInt("codLanche"));
        objLanche.setNomeLanche(rs.getString("nomeLanche"));
        objLanche.setPrecoLanche(rs.getDouble("precoLanche"));
        
        objLanche.setDescricao(rs.getString("descricao"));
        objLanche.setLucroLanches(rs.getInt("lucroLanches"));

        // Categoria preenchido diretamente do SELECT
        Categoria categoria = new Categoria();
        categoria.setCodCategoria(rs.getInt("categoria_codCategoria")); // alias correto
        categoria.setNomeCategoria(rs.getString("nomeCategoria"));
        objLanche.setCategoria(categoria);

        return objLanche;
    }
}

    
    public List<Lanche> buscarTodosLanches()
    {
        String sql = "SELECT l.*, c.nomeCategoria FROM lanche l JOIN categoria c ON l.categoria_codCategoria = c.codCategoria ORDER BY codLanche ASC;";
        return buscarTodos(sql, new LancheRowMapper());
    }
    
    public Lanche buscarLanchePorId(int id)
    {
        String sql = "SELECT l.*, c.nomeCategoria FROM lanche l JOIN categoria c ON l.categoria_codCategoria = c.codCategoria where l.codLanche = ? ;";
        return buscarPorId(sql, new LancheRowMapper());
    }
}
