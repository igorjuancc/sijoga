package br.com.sijoga.dto;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoDto implements Serializable {

    private int id;
    private int numero;
    private String cep;
    private String rua;
    private String bairro;
    private String complemento;
    private int cidade;
    private IntimacaoDto intimacao;

    public EnderecoDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getCidade() {
        return cidade;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }

    public IntimacaoDto getIntimacao() {
        return intimacao;
    }

    public void setIntimacao(IntimacaoDto intimacao) {
        this.intimacao = intimacao;
    }

}
