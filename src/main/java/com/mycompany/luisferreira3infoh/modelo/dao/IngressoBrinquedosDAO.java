/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Brinquedo;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.IngressoBrinquedos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author
 */
public class IngressoBrinquedosDAO extends GenericoDAO <IngressoBrinquedos>{
    
    public void salvar(IngressoBrinquedos objIB)
    {
        String sql = "INSERT INTO ingressobrinquedos (tipoIngresso, valor,lucroIB, brinquedo_codBrinquedo) VALUES (?,?,?,?);";
        save(sql, objIB.getTipoIngresso(),
                objIB.getValor(),
                objIB.getLucroIB() != null ? objIB.getLucroIB() : null,
                objIB.getBrinquedo().getCodBrinquedo());
    }
    
    public void alterar(IngressoBrinquedos objIB)
    {
        String sql = "Update ingressobrinquedos set tipoIngresso = ?, valor = ?,lucroIB = ?, brinquedo_codBrinquedo = ? where codIngresso = ?";
        save(sql, objIB.getTipoIngresso(),
                objIB.getValor(),
                objIB.getLucroIB() != null ? objIB.getLucroIB() : null,
                objIB.getBrinquedo().getCodBrinquedo(),
                objIB.getCodIngresso());
    }
    
    public void excluir(IngressoBrinquedos objIB)
    {
        String sql = "DELETE FROM ingressobrinquedos WHERE codIngresso = ?;";
        save(sql, objIB.getCodIngresso());
    }
    
    private static class IngressoBrinquedosRowMapper implements RowMapper<IngressoBrinquedos> {
    @Override
    public IngressoBrinquedos mapRow(ResultSet rs) throws SQLException {
        IngressoBrinquedos objIB = new IngressoBrinquedos();
        objIB.setCodIngresso(rs.getInt("codIngresso"));
        objIB.setTipoIngresso(rs.getString("tipoIngresso"));
        objIB.setValor(rs.getDouble("valor"));
        objIB.setLucroIB(rs.getInt("lucroIB"));

        // Categoria preenchido diretamente do SELECT
        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setCodBrinquedo(rs.getInt("brinquedo_codBrinquedo")); // alias correto
        brinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));
        objIB.setBrinquedo(brinquedo);

        return objIB;
    }
}

    
    public List<IngressoBrinquedos> buscarTodosIngressoBrinquedos()
    {
        String sql = "SELECT ib.*, b.nomeBrinquedo FROM ingressobrinquedos ib JOIN brinquedo b ON ib.brinquedo_codBrinquedo = b.codBrinquedo ORDER BY codIngresso ASC;";
        return buscarTodos(sql, new IngressoBrinquedosRowMapper());
    }
    
    public IngressoBrinquedos buscarIngressoBrinquedosPorId(int id)
    {
        String sql = "SELECT ib.*, b.nomeBrinquedo FROM ingressobrinquedos ib JOIN brinquedo b ON ib.brinquedo_codBrinquedo = b.codBrinquedo where ib.codIngresso = ? ;";
        return buscarPorId(sql, new IngressoBrinquedosRowMapper());
    }
}
