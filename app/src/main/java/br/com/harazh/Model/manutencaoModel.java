package br.com.harazh.Model;

public class manutencaoModel {

    private Integer id_manutencao;
    private Integer id_carro;
    private String data;
    private String valor;
    private String descricao;
    private String titulo;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
