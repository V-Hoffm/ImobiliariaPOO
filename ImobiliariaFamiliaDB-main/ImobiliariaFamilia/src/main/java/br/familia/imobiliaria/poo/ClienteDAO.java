package br.familia.imobiliaria.poo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends BaseDAO {

    public void inserir(Cliente c) {
        String sql = "INSERT INTO cliente (nome, cpf, email, telefone) VALUES (?, ?, ?, ?)";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setString(1, c.getNome());
            pre.setString(2, c.getCpf());
            pre.setString(3, c.getEmail());
            pre.setString(4, c.getTelefone());
            pre.execute();
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente. Erro: " + e.getMessage());
        }
    }

    public void deletarPeloId(long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            pre.execute();
            System.out.println("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pelo id: " + id + ". Erro: " + e.getMessage());
        }
    }

    public void atualizar(Cliente c) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setString(1, c.getNome());
            pre.setString(2, c.getCpf());
            pre.setString(3, c.getEmail());
            pre.setString(4, c.getTelefone());
            pre.setLong(5, c.getId());
            pre.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente id: " + c.getId() + ". Erro: " + e.getMessage());
        }
    }

    public Cliente buscarPorId(long id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente id: " + id + ". Erro: " + e.getMessage());
        }
        return null;
    }

    // RELATÓRIO: clientes com mais contratos (Top N)
    public List<String> clientesComMaisContratos(int topN) {
        String sql = """
            SELECT c.id, c.nome, COUNT(ct.id) qtd
            FROM cliente c
            JOIN contratos ct ON ct.cliente_id = c.id
            GROUP BY c.id, c.nome
            ORDER BY qtd DESC, c.nome ASC
            LIMIT ?
        """;
        List<String> ranking = new ArrayList<>();
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, topN);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    ranking.add(String.format("%d - %s (%d contratos)",
                            rs.getLong("id"), rs.getString("nome"), rs.getInt("qtd")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no ranking de clientes. " + e.getMessage());
        }
        return ranking;
    }
}