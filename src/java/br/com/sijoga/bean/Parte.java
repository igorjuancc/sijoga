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
@PrimaryKeyJoinColumn(name = "id_parte")
@Table(name = "tb_parte")
public class Parte extends Pessoa implements Serializable {

    private String senha;
    private List<Processo> promovente;
    private List<Processo> promovido;
    private List<Processo> vencedor;

    public Parte() {
    }

    @Column(name = "senha_parte")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @OneToMany(mappedBy = "promovente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getPromovente() {
        return promovente;
    }

    public void setPromovente(List<Processo> promovente) {
        this.promovente = promovente;
    }

    @OneToMany(mappedBy = "promovido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getPromovido() {
        return promovido;
    }

    public void setPromovido(List<Processo> promovido) {
        this.promovido = promovido;
    }

    @OneToMany(mappedBy = "vencedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Processo> getVencedor() {
        return vencedor;
    }

    public void setVencedor(List<Processo> vencedor) {
        this.vencedor = vencedor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.senha);
        hash = 41 * hash + Objects.hashCode(this.promovente);
        hash = 41 * hash + Objects.hashCode(this.promovido);
        hash = 41 * hash + Objects.hashCode(this.vencedor);
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
        final Parte other = (Parte) obj;
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.promovente, other.promovente)) {
            return false;
        }
        if (!Objects.equals(this.promovido, other.promovido)) {
            return false;
        }
        if (!Objects.equals(this.vencedor, other.vencedor)) {
            return false;
        }
        return true;
    }
}
