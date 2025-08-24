package br.familia.imobiliaria.poo;

public class Imovel {
    private long id;
    private TipoImovel tipo;
    private String endereco;
    private String cidade;
    private String uf;
    private String cep;
    private byte quartos;
    private byte banheiros;
    private boolean mobiliado;
    private double valorAluguelSugerido;
    private StatusImovel status;

    public enum TipoImovel { CASA, APARTAMENTO, TERRENO, SALA_COMERCIAL }
    public enum StatusImovel { DISPONIVEL, ALUGADO, VENDIDO, INATIVO }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public TipoImovel getTipo() { return tipo; }
    public void setTipo(TipoImovel tipo) { this.tipo = tipo; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public byte getQuartos() { return quartos; }
    public void setQuartos(byte quartos) { this.quartos = quartos; }

    public byte getBanheiros() { return banheiros; }
    public void setBanheiros(byte banheiros) { this.banheiros = banheiros; }

    public boolean isMobiliado() { return mobiliado; }
    public void setMobiliado(boolean mobiliado) { this.mobiliado = mobiliado; }

    public double getValorAluguelSugerido() { return valorAluguelSugerido; }
    public void setValorAluguelSugerido(double valorAluguelSugerido) { this.valorAluguelSugerido = valorAluguelSugerido; }

    public StatusImovel getStatus() { return status; }
    public void setStatus(StatusImovel status) { this.status = status; }
}