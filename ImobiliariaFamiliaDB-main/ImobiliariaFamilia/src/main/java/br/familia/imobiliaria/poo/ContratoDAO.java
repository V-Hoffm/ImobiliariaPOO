package br.familia.imobiliaria.poo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO extends BaseDAO {

    public void inserir(Contrato c) {
        String sql = "INSERT INTO contratos (imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, c.getImovelId());
            pre.setLong(2, c.getClienteId());
            pre.setDouble(3, c.getValorMensal());
            pre.setDate(4, c.getDataInicio());
            pre.setDate(5, c.getDataFim());
            pre.setByte(6, c.getDiaVencimento());
            pre.setString(7, c.getStatus().name());
            pre.execute();
            System.out.println("Contrato inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir contrato. Erro: " + e.getMessage());
        }
    }

    public void deletarPeloId(long id) {
        String sql = "DELETE FROM contratos WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            pre.execute();
            System.out.println("Contrato deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar contrato ID: " + id + ". Erro: " + e.getMessage());
        }
    }

    public List<Contrato> listarTodos() {
        String sql = "SELECT id, imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status FROM contratos";
        List<Contrato> lista = new ArrayList<>();
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar contratos. " + e.getMessage());
        }
        return lista;
    }

    public Contrato buscarPorId(long id) {
        String sql = "SELECT id, imovel_id, cliente_id, valor_mensal, data_inicio, data_fim, dia_vencimento, status FROM contratos WHERE id = ?";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setLong(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar contrato ID: " + id + ". " + e.getMessage());
        }
        return null;
    }

    // RELATÓRIOS
    public List<Contrato> listarAtivos() {
        List<Contrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM contratos WHERE status = 'ATIVO'";
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar contratos ativos. " + e.getMessage());
        }
        return lista;
    }

    public List<Contrato> listarExpirandoEm30Dias() {
        List<Contrato> lista = new ArrayList<>();
        String sql = """
            SELECT * FROM contratos
            WHERE data_fim BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
              AND status = 'ATIVO'
        """;
        try (Connection con = con(); PreparedStatement pre = con.prepareStatement(sql); ResultSet rs = pre.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar contratos expirando em 30 dias. " + e.getMessage());
        }
        return lista;
    }

    private Contrato map(ResultSet rs) throws SQLException {
        Contrato c = new Contrato();
        c.setId(rs.getLong("id"));
        c.setImovelId(rs.getLong("imovel_id"));
        c.setClienteId(rs.getLong("cliente_id"));
        c.setValorMensal(rs.getDouble("valor_mensal"));
        c.setDataInicio(rs.getDate("data_inicio"));
        c.setDataFim(rs.getDate("data_fim"));
        c.setDiaVencimento(rs.getByte("dia_vencimento"));
        c.setStatus(Contrato.Status.valueOf(rs.getString("status")));
        return c;
    }
}