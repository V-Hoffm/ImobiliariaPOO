package br.familia.imobiliaria.poo;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ImovelDAO imovelDAO = new ImovelDAO();
        ContratoDAO contratoDAO = new ContratoDAO();

        while (true) {
            System.out.println("\n=== Imobiliária da Família ===");
            System.out.println("1) Cadastrar cliente");
            System.out.println("2) Cadastrar imóvel");
            System.out.println("3) Cadastrar contrato de aluguel");
            System.out.println("4) Relatório: Imóveis disponíveis");
            System.out.println("5) Relatório: Contratos ativos");
            System.out.println("6) Relatório: Clientes com mais contratos");
            System.out.println("7) Relatório: Contratos expirando em 30 dias");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");

            int op = lerInt();

            try {
                switch (op) {
                    case 1 -> cadastrarCliente(clienteDAO);
                    case 2 -> cadastrarImovel(imovelDAO);
                    case 3 -> cadastrarContrato(contratoDAO);
                    case 4 -> listarImoveisDisponiveis(imovelDAO);
                    case 5 -> listarContratosAtivos(contratoDAO);
                    case 6 -> rankingClientes(clienteDAO);
                    case 7 -> listarContratosExpirando(contratoDAO);
                    case 0 -> { System.out.println("Tchau!"); return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Ops: " + e.getMessage());
            }
        }
    }

    // --- CADASTROS ---
    private static void cadastrarCliente(ClienteDAO dao) {
        Cliente c = new Cliente();
        System.out.print("Nome: ");     c.setNome(in.nextLine());
        System.out.print("CPF: ");      c.setCpf(in.nextLine());
        System.out.print("Email: ");    c.setEmail(in.nextLine());
        System.out.print("Telefone: "); c.setTelefone(in.nextLine());
        dao.inserir(c);
    }

    private static void cadastrarImovel(ImovelDAO dao) {
        Imovel i = new Imovel();
        System.out.print("Tipo (CASA/APARTAMENTO/TERRENO/SALA_COMERCIAL): ");
        i.setTipo(Imovel.TipoImovel.valueOf(in.nextLine().trim().toUpperCase()));
        System.out.print("Endereço: "); i.setEndereco(in.nextLine());
        System.out.print("Cidade: ");   i.setCidade(in.nextLine());
        System.out.print("UF: ");       i.setUf(in.nextLine().trim().toUpperCase());
        System.out.print("CEP: ");      i.setCep(in.nextLine());
        System.out.print("Quartos: ");  i.setQuartos((byte) lerInt());
        System.out.print("Banheiros: ");i.setBanheiros((byte) lerInt());
        System.out.print("Mobiliado? (true/false): "); i.setMobiliado(Boolean.parseBoolean(in.nextLine()));
        System.out.print("Valor aluguel sugerido: ");  i.setValorAluguelSugerido(Double.parseDouble(in.nextLine()));
        System.out.print("Status (DISPONIVEL/ALUGADO/VENDIDO/INATIVO): ");
        i.setStatus(Imovel.StatusImovel.valueOf(in.nextLine().trim().toUpperCase()));

        dao.inserir(i);
        System.out.println("Imóvel cadastrado.");
    }

    private static void cadastrarContrato(ContratoDAO dao) {
        Contrato c = new Contrato();
        System.out.print("ID do Imóvel: ");  c.setImovelId(Long.parseLong(in.nextLine()));
        System.out.print("ID do Cliente: "); c.setClienteId(Long.parseLong(in.nextLine()));
        System.out.print("Valor mensal: ");  c.setValorMensal(Double.parseDouble(in.nextLine()));
        System.out.print("Data início (yyyy-mm-dd): "); c.setDataInicio(Date.valueOf(in.nextLine()));
        System.out.print("Data fim (yyyy-mm-dd): ");    c.setDataFim(Date.valueOf(in.nextLine()));
        System.out.print("Dia do vencimento (1-28): "); c.setDiaVencimento((byte) lerInt());
        System.out.print("Status (ATIVO/ENCERRADO/RESCINDIDO/PENDENTE): ");
        c.setStatus(Contrato.Status.valueOf(in.nextLine().trim().toUpperCase()));

        dao.inserir(c);
        System.out.println("Contrato cadastrado.");
    }

    // --- RELATÓRIOS ---
    private static void listarImoveisDisponiveis(ImovelDAO dao) {
        List<Imovel> lista = dao.listarDisponiveis();
        if (lista.isEmpty()) { System.out.println("Nenhum imóvel disponível."); return; }
        lista.forEach(i -> System.out.printf(
                "ID:%d | %s | %s, %s/%s | Quartos:%d | Banheiros:%d | Mobiliado:%s | R$ %.2f%n",
                i.getId(), i.getTipo(), i.getEndereco(), i.getCidade(), i.getUf(),
                i.getQuartos(), i.getBanheiros(), i.isMobiliado() ? "Sim":"Não",
                i.getValorAluguelSugerido()
        ));
    }

    private static void listarContratosAtivos(ContratoDAO dao) {
        List<Contrato> lista = dao.listarAtivos();
        if (lista.isEmpty()) { System.out.println("Nenhum contrato ativo."); return; }
        lista.forEach(c -> System.out.printf(
                "ID:%d | Imóvel:%d | Cliente:%d | Valor:R$ %.2f | %s -> %s | Venc: dia %d | %s%n",
                c.getId(), c.getImovelId(), c.getClienteId(), c.getValorMensal(),
                c.getDataInicio(), c.getDataFim(), c.getDiaVencimento(), c.getStatus()
        ));
    }

    private static void rankingClientes(ClienteDAO dao) {
        System.out.print("Top N (ex: 5): ");
        int n = lerInt();
        var r = dao.clientesComMaisContratos(n);
        if (r.isEmpty()) { System.out.println("Sem contratos cadastrados."); return; }
        r.forEach(System.out::println);
    }

    private static void listarContratosExpirando(ContratoDAO dao) {
        var lista = dao.listarExpirandoEm30Dias();
        if (lista.isEmpty()) { System.out.println("Nenhum contrato expira nos próximos 30 dias."); return; }
        lista.forEach(c -> System.out.printf(
                "ID:%d | Imóvel:%d | Cliente:%d | Fim:%s | Status:%s%n",
                c.getId(), c.getImovelId(), c.getClienteId(), c.getDataFim(), c.getStatus()
        ));
    }

    private static int lerInt() {
        while (true) {
            try { return Integer.parseInt(in.nextLine()); }
            catch (NumberFormatException e) { System.out.print("Número inválido, tente novamente: "); }
        }
    }
}