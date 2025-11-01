/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Cargo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CargoDAO extends GenericoDAO <Cargo>{
    
    public void salvar(Cargo objCargo)
    {
        String sql = "INSERT INTO cargo (nomeCargo, salarioInicial, comissao) VALUES (?,?,?);";
        save(sql, objCargo.getNomeCargo(), objCargo.getSalarioInicial(), objCargo.getComissao());
    }
    
    public void alterar(Cargo objCargo)
    {
        String sql = "UPDATE cargo SET nomeCargo = ?, salarioInicial = ?, comissao = ? WHERE codCargo = ?;";
        save(sql, objCargo.getNomeCargo(), objCargo.getSalarioInicial(),objCargo.getComissao() ,objCargo.getCodCargo());
    }
    public void excluir(Cargo objCargo)
    {
        String sql = "DELETE FROM cargo WHERE codCargo = ?;";
        save(sql, objCargo.getCodCargo());
    }
    
    private static class CargoRowMapper implements RowMapper<Cargo>{
        @Override
        public Cargo mapRow (ResultSet rs) throws SQLException {
            Cargo objCargo = new Cargo();
            objCargo.setCodCargo(rs.getInt("codCargo"));
            objCargo.setNomeCargo(rs.getString("nomeCargo"));
            objCargo.setSalarioInicial(rs.getDouble("salarioInicial"));
            objCargo.setSalarioFinal(rs.getDouble("salarioFinal"));
            objCargo.setComissao(rs.getInt("comissao"));
            return objCargo;
    }
    }
    
    public List<Cargo> buscarTodosCargos()
    {
        String sql = "SELECT * FROM cargo;";
        return buscarTodos(sql, new CargoRowMapper());
    }
    
    public Cargo buscarPorId(int id)
    {
        String sql = "SELECT * FROM cargo WHERE codCargo = ?;";
        return buscarPorId(sql, new CargoRowMapper());
    }
}
