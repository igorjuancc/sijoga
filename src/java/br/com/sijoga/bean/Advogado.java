package br.com.sijoga.bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name="id_advogado")
@Table(name="tb_advogado")
public class Advogado extends Pessoa implements Serializable {
    private String senha;
    private int registroOab;
    private List<Processo> promovente;
    private List<Processo> promovido;
    private List<FaseProcesso> fasesProcesso;

    public Advogado() {
    }
    
    @Column(name = "senha_advogado")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Column(name = "reg_oab_advogado")
    public int getRegistroOab() {
        return registroOab;
    }

    public void setRegistroOab(int registroOab) {
        this.registroOab = registroOab;
    }
    
    @OneToMany(mappedBy = "advogadoPromovente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getPromovente() {
        return promovente;
    }

    public void setPromovente(List<Processo> promovente) {
        this.promovente = promovente;
    }
    
    @OneToMany(mappedBy = "advogadoPromovido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getPromovido() {
        return promovido;
    }

    public void setPromovido(List<Processo> promovido) {
        this.promovido = promovido;
    }
    
    @OneToMany(mappedBy = "advogado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<FaseProcesso> getFasesProcesso() {
        return fasesProcesso;
    }

    public void setFasesProcesso(List<FaseProcesso> fasesProcesso) {
        this.fasesProcesso = fasesProcesso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.registroOab;
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
        final Advogado other = (Advogado) obj;
        if (this.registroOab != other.registroOab) {
            return false;
        }
        return true;
    }
}
