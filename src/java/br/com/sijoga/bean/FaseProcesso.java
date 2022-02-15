package br.com.sijoga.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="tb_fase")
@SequenceGenerator(name = "seq_fase", sequenceName = "tb_fase_id_fase_seq")
public class FaseProcesso implements Serializable {
    private int id;
    private int tipo;
    private String titulo;
    private String descricao;
    private String justificativa;
    private Date dataHora;
    private Documento documento;
    private Advogado advogado;
    private Processo processo;

    public FaseProcesso() {
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_fase")
    @Column(name="id_fase")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="tipo_fase")
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    @Column(name="titulo_fase")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    @Column(name="desc_fase")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Column(name="justi_fase")
    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="dataHora_fase")
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_documento_fase", updatable = true)
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    
    @ManyToOne
    @JoinColumn(name="id_advogado_fase")
    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }
    
    @ManyToOne
    @JoinColumn(name="id_processo_fase")
    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        hash = 17 * hash + this.tipo;
        hash = 17 * hash + Objects.hashCode(this.titulo);
        hash = 17 * hash + Objects.hashCode(this.descricao);
        hash = 17 * hash + Objects.hashCode(this.justificativa);
        hash = 17 * hash + Objects.hashCode(this.dataHora);
        hash = 17 * hash + Objects.hashCode(this.documento);
        hash = 17 * hash + Objects.hashCode(this.advogado);
        hash = 17 * hash + Objects.hashCode(this.processo);
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
        final FaseProcesso other = (FaseProcesso) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.justificativa, other.justificativa)) {
            return false;
        }
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.advogado, other.advogado)) {
            return false;
        }
        if (!Objects.equals(this.processo, other.processo)) {
            return false;
        }
        return true;
    }
}
