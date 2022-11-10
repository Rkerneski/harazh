package br.com.harazh.Model;

public class carroModel {

    private Integer id;
    private String modelo;
    private String placa;
    private String ano;

    public carroModel(){

    }

    public carroModel(String modelo, String placa, String ano){

        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
