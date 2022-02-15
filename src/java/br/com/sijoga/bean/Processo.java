package br.com.sijoga.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="tb_processo")
@SequenceGenerator(name = "seq_processo", sequenceName = "tb_processo_id_processo_seq")
public class Processo implements Serializable {
    private int id;
    private Date dataInicio;
    private Juiz juiz;
    private Parte promovente;
    private Parte promovido;
    private Parte vencedor;
    private Advogado advogadoPromovente;
    private Advogado advogadoPromovido;
    private List<FaseProcesso> fases;
    private String parecer;

    public Processo() {
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_processo")
    @Column(name="id_processo")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="dataHora_processo")
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    @ManyToOne
    @JoinColumn(name="id_juiz_processo")
    public Juiz getJuiz() {
        return juiz;
    }

    public void setJuiz(Juiz juiz) {
        this.juiz = juiz;
    }
    
    @ManyToOne
    @JoinColumn(name="id_promovente_processo")
    public Parte getPromovente() {
        return promovente;
    }

    public void setPromovente(Parte promovente) {
        this.promovente = promovente;
    }
    
    @ManyToOne
    @JoinColumn(name="id_promovido_processo")
    public Parte getPromovido() {
        return promovido;
    }

    public void setPromovido(Parte promovido) {
        this.promovido = promovido;
    }
    
    @ManyToOne
    @JoinColumn(name="id_vencedor_processo")
    public Parte getVencedor() {
        return vencedor;
    }

    public void setVencedor(Parte vencedor) {
        this.vencedor = vencedor;
    }
    
    @ManyToOne
    @JoinColumn(name="id_advogado_promovente_processo")
    public Advogado getAdvogadoPromovente() {
        return advogadoPromovente;
    }

    public void setAdvogadoPromovente(Advogado advogadoPromovente) {
        this.advogadoPromovente = advogadoPromovente;
    }
    
    @ManyToOne
    @JoinColumn(name="id_advogado_promovido_processo")
    public Advogado getAdvogadoPromovido() {
        return advogadoPromovido;
    }

    public void setAdvogadoPromovido(Advogado advogadoPromovido) {
        this.advogadoPromovido = advogadoPromovido;
    }
    
    @OrderBy("id_fase")
    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<FaseProcesso> getFases() {
        return fases;
    }

    public void setFases(List<FaseProcesso> fases) {
        this.fases = fases;
    }
    
    @Column(name="parecer_processo")
    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.dataInicio);
        hash = 23 * hash + Objects.hashCode(this.juiz);
        hash = 23 * hash + Objects.hashCode(this.promovente);
        hash = 23 * hash + Objects.hashCode(this.promovido);
        hash = 23 * hash + Objects.hashCode(this.vencedor);
        hash = 23 * hash + Objects.hashCode(this.advogadoPromovente);
        hash = 23 * hash + Objects.hashCode(this.advogadoPromovido);
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
        final Processo other = (Processo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
            return false;
        }
        if (!Objects.equals(this.juiz, other.juiz)) {
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
        if (!Objects.equals(this.advogadoPromovente, other.advogadoPromovente)) {
            return false;
        }
        if (!Objects.equals(this.advogadoPromovido, other.advogadoPromovido)) {
            return false;
        }
        return true;
    }
}
