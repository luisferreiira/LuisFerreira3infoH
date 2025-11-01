/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Visitante;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class VisitanteDAO extends GenericoDAO <Visitante>{
    
    public void salvar(Visitante objVisitante) {
        String hashSenha = BCrypt.hashpw(objVisitante.getSenha(), BCrypt.gensalt());

        String sql = "INSERT INTO visitante(nomeVisitante, dataNascimento, email, senha) VALUES (?, ?, ?, ?);";
        save(sql, objVisitante.getNomeVisitante(),
                java.sql.Date.valueOf(objVisitante.getDataNascimento()), // LocalDate --> Date
                objVisitante.getEmail(),
                hashSenha);
    }

    public void alterar(Visitante objVisitante) {
        String hashSenha = BCrypt.hashpw(objVisitante.getSenha(), BCrypt.gensalt());
        String sql = "UPDATE visitante SET nomeVisitante = ?, dataNascimento = ?, email = ?, senha = ? WHERE codVisitante = ?;";
        save(sql, objVisitante.getNomeVisitante(),
                java.sql.Date.valueOf(objVisitante.getDataNascimento()), // LocalDate --> Date
                objVisitante.getEmail(),
                hashSenha,
                objVisitante.getCodVisitante());
    }
    
    public void redefinirSenha(Visitante objVisitante) {
        
        String sql = "Update visitante set senha = ? where codVisitante = ?";
        save(sql, objVisitante.getSenha(),
                objVisitante.getCodVisitante());
    }

    public void excluir(Visitante objVisitante) {
        String sql = "DELETE FROM visitante WHERE codVisitante = ?;";
        save(sql, objVisitante.getCodVisitante());
    }

    private static class VisitanteRowMapper implements RowMapper<Visitante> {
    @Override
    public Visitante mapRow(ResultSet rs) throws SQLException {
        Visitante objVisitante = new Visitante();

        objVisitante.setCodVisitante(rs.getInt("codVisitante")); // se tiver esse campo
        objVisitante.setNomeVisitante(rs.getString("nomeVisitante"));
        
        Date dataNasc = rs.getDate("dataNascimento");
        if (dataNasc != null) {
            objVisitante.setDataNascimento(dataNasc.toLocalDate()); // Date -> LocalDate
        }

        objVisitante.setEmail(rs.getString("email"));
        objVisitante.setSenha(rs.getString("senha"));
        
        return objVisitante;
    }
}


    public List<Visitante> buscarTodosVisitantes() {
        String sql = "SELECT * FROM visitante;";
        return buscarTodos(sql, new VisitanteRowMapper());
    }

    public Visitante buscarPorId(int id) {
        String sql = "SELECT * FROM visitante WHERE codVisitante = ?;";
        return buscarPorId(sql, new VisitanteRowMapper(), id);
    }
    
   public Visitante buscarPorEmail(String email) {
    String sql = "SELECT * FROM visitante WHERE email = ?;";
    return buscarUm(sql, new VisitanteRowMapper(), email);
}
   
   /*
        public Usuario buscarUsuarioPorNomeUsuario(String nomeUsuario) {
        String sql = "SELECT nomeusuario, senhahash FROM usuario WHERE nomeusuario = ?";
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getString("nomeusuario"), rs.getString("senhahash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     
     public void registrarUsuario(String nomeUsuario, String senhaSimples, String email) {
    // Gerar hash da senha
    String hashDeSenha = BCrypt.hashpw(senhaSimples, BCrypt.gensalt());

    // Inserir usuário no banco de dados
    String sql = "INSERT INTO usuario (nomeusuario, senhahash,email) VALUES (?, ?,?)";
    try (Connection con = ConnectionFactory.getInstance().getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, nomeUsuario);
        stmt.setString(2, hashDeSenha);
        stmt.setString(3, email);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
   */

}