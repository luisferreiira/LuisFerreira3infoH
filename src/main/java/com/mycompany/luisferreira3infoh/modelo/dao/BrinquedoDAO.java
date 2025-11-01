package com.mycompany.luisferreira3infoh.modelo.dao;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Brinquedo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BrinquedoDAO extends GenericoDAO<Brinquedo> {

    public void salvar(Brinquedo objBrinquedo) {
        String sql = "insert into brinquedo(nomeBrinquedo,capacidadeMaxima,tipoBrinquedo,idadeRestrita,funcionamento ) values (?,?,?,?,?);";
        save(sql,objBrinquedo.getNomeBrinquedo(), objBrinquedo.getCapacidadeMaxima(),objBrinquedo.getTipoBrinquedo(), objBrinquedo.getIdadeRestrita(), objBrinquedo.getFuncionamento() );
    }

    public void alterar(Brinquedo objBrinquedo) {
        String sql = "update brinquedo set nomeBrinquedo=?, capacidadeMaxima=?, tipoBrinquedo=?, idadeRestrita=?, funcionamento=? where codBrinquedo=?";
        save(sql, objBrinquedo.getNomeBrinquedo(), objBrinquedo.getCapacidadeMaxima(),objBrinquedo.getTipoBrinquedo(), objBrinquedo.getIdadeRestrita(), objBrinquedo.getFuncionamento(), objBrinquedo.getCodBrinquedo() );
    }

    public void excluir(Brinquedo objBrinquedo) {
        String sql = "DELETE FROM brinquedo WHERE codBrinquedo = ?;";
        save(sql, objBrinquedo.getCodBrinquedo());
    }

    private static class BrinquedoRowMapper implements RowMapper<Brinquedo> {
        @Override
        public Brinquedo mapRow(ResultSet rs) throws SQLException {
            Brinquedo objBrinquedo = new Brinquedo();
            objBrinquedo.setCodBrinquedo(rs.getInt("codBrinquedo"));
            objBrinquedo.setNomeBrinquedo(rs.getString("nomeBrinquedo"));
            objBrinquedo.setCapacidadeMaxima(rs.getInt("capacidadeMaxima"));
            objBrinquedo.setCapacidadeAtual(rs.getInt("capacidadeAtual"));
            objBrinquedo.setTipoBrinquedo(rs.getString("tipoBrinquedo"));
            objBrinquedo.setIdadeRestrita(rs.getInt("idadeRestrita"));
            objBrinquedo.setFuncionamento(rs.getInt("funcionamento"));
            return objBrinquedo;
        }
    }

    public List<Brinquedo> buscarTodosBrinquedos() {
        String sql = "SELECT * FROM brinquedo;";
        return buscarTodos(sql, new BrinquedoRowMapper());
    }

    public Brinquedo buscarPorId(int id) {
        String sql = "SELECT * FROM brinquedo WHERE codBrinquedo = ?;";
        return buscarPorId(sql, new BrinquedoRowMapper(), id);
    }
}

