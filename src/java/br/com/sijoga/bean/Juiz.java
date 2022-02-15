package br.com.sijoga.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name="id_juiz")
@Table(name="tb_juiz")
public class Juiz extends Pessoa implements Serializable {
    private String senha;
    private int registroOab;
    private List<Processo> processos;

    public Juiz() {
    }
    
    @Column(name = "senha_juiz")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Column(name = "reg_oab_juiz")
    public int getRegistroOab() {
        return registroOab;
    }

    public void setRegistroOab(int registroOab) {
        this.registroOab = registroOab;
    }
    
    @OneToMany(mappedBy = "juiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.senha);
        hash = 19 * hash + this.registroOab;
        hash = 19 * hash + Objects.hashCode(this.processos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Juiz other = (Juiz) obj;
        if (this.registroOab != other.registroOab) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.processos, other.processos)) {
            return false;
        }
        return true;
    }
}
