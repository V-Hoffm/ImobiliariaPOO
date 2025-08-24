package br.familia.imobiliaria.poo;

import java.sql.Date;

public class Contrato {
    private long id;
    private long imovelId;
    private long clienteId;
    private double valorMensal;
    private Date dataInicio;
    private Date dataFim;
    private byte diaVencimento;
    private Status status;

    public enum Status {
        ATIVO, ENCERRADO, RESCINDIDO, PENDENTE
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getImovelId() { return imovelId; }
    public void setImovelId(long imovelId) { this.imovelId = imovelId; }

    public long getClienteId() { return clienteId; }
    public void setClienteId(long clienteId) { this.clienteId = clienteId; }

    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

    public byte getDiaVencimento() { return diaVencimento; }
    public void setDiaVencimento(byte diaVencimento) { this.diaVencimento = diaVencimento; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}