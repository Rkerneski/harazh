package br.com.harazh.Model;

public class manutencaoModel {

    private Integer id_manutencao;
    private Integer id_carro;
    private String data;
    private Float valor;
    private String descricao;

    public Integer getId_manutencao() {
        return id_manutencao;
    }

    public void setId_manutencao(Integer id_manutencao) {
        this.id_manutencao = id_manutencao;
    }

    public Integer getId_carro() {
        return id_carro;
    }

    public void setId_carro(Integer id_carro) {
        this.id_carro = id_carro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
