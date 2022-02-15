package br.com.sijoga.dto;

import java.io.Serializable;
import java.util.Date;

public class IntimacaoDto implements Serializable {
    private int id;
    private int processo;
    private Date dataHora;
    private Date dataHoraExec;
    private String cpf;
    private String nome;
    private Boolean status;
    private OficialDto oficial;
    private EnderecoDto endereco;

    public IntimacaoDto() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Date getDataHoraExec() {
        return dataHoraExec;
    }

    public void setDataHoraExec(Date dataHoraExec) {
        this.dataHoraExec = dataHoraExec;
    }    

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 
    public OficialDto getOficial() {
        return oficial;
    }

    public void setOficial(OficialDto oficial) {
        this.oficial = oficial;
    }

    public EnderecoDto getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDto endereco) {
        this.endereco = endereco;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
