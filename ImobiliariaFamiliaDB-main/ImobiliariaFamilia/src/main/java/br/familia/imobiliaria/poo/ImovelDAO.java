package br.familia.imobiliaria.poo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImovelDAO extends BaseDAO {

    public void inserir(Imovel imovel) {
        String sql = "INSERT INTO imovel (tipo, endereco, cidade, uf, cep, quartos, banheiros, mobiliado, valor_aluguel_sugerido, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getTipo().name());
            pre.setString(2, imovel.getEndereco());
            pre.setString(3, imovel.getCidade());
            pre.setString(4, imovel.getUf());
            pre.setString(5, imovel.getCep());
            pre.setByte(6, imovel.getQuartos());
            pre.setByte(7, imovel.getBanheiros());
            pre.setBoolean(8, imovel.isMobiliado());
            pre.setDouble(9, imovel.getValorAluguelSugerido());
            pre.setString(10, imovel.getStatus().name());
            pre.executeUpdate();
            System.out.println("Imóvel inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir imóvel. Erro: " + e.getMessage());
        }
    }

    public Imovel buscarPorId(long id) {
        String sql = "SELECT * FROM imovel WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar imóvel pelo id: " + id + ". Erro: " + e.getMessage());
        }
        return null;
    }

    public List<Imovel> listarTodos() {
        List<Imovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM imovel";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar imóveis. " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Imovel imovel) {
        String sql = "UPDATE imovel SET tipo=?, endereco=?, cidade=?, uf=?, cep=?, quartos=?, banheiros=?, mobiliado=?, valor_aluguel_sugerido=?, status=? WHERE id=?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setString(1, imovel.getTipo().name());
            pre.setString(2, imovel.getEndereco());
            pre.setString(3, imovel.getCidade());
            pre.setString(4, imovel.getUf());
            pre.setString(5, imovel.getCep());
            pre.setByte(6, imovel.getQuartos());
            pre.setByte(7, imovel.getBanheiros());
            pre.setBoolean(8, imovel.isMobiliado());
            pre.setDouble(9, imovel.getValorAluguelSugerido());
            pre.setString(10, imovel.getStatus().name());
            pre.setLong(11, imovel.getId());
            pre.executeUpdate();
            System.out.println("Imóvel atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar imóvel. " + e.getMessage());
        }
    }

    public void deletarPeloId(long id) {
        String sql = "DELETE FROM imovel WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            pre.executeUpdate();
            System.out.println("Imóvel deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar imóvel pelo id: " + id + ". Erro: " + e.getMessage());
        }
    }

    // RELATÓRIO: imóveis disponíveis
    public List<Imovel> listarDisponiveis() {
        List<Imovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM imovel WHERE status = 'DISPONIVEL'";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar imóveis disponíveis. " + e.getMessage());
        }
        return lista;
    }

    private Imovel map(ResultSet rs) throws SQLException {
        Imovel im = new Imovel();
        im.setId(rs.getLong("id"));
        im.setTipo(Imovel.TipoImovel.valueOf(rs.getString("tipo")));
        im.setEndereco(rs.getString("endereco"));
        im.setCidade(rs.getString("cidade"));
        im.setUf(rs.getString("uf"));
        im.setCep(rs.getString("cep"));
        im.setQuartos(rs.getByte("quartos"));
        im.setBanheiros(rs.getByte("banheiros"));
        im.setMobiliado(rs.getBoolean("mobiliado"));
        im.setValorAluguelSugerido(rs.getDouble("valor_aluguel_sugerido"));
        im.setStatus(Imovel.StatusImovel.valueOf(rs.getString("status")));
        return im;
    }
}