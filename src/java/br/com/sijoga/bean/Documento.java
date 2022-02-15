package br.com.sijoga.bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_documento")
@SequenceGenerator(name = "seq_documento", sequenceName = "tb_documento_id_documento_seq")
public class Documento implements Serializable {
    private int id;
    private String extensao;
    private FaseProcesso fase;

    public Documento() {
    }
       
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_documento")
    @Column(name="id_documento")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="ext_documento")
    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }
    
    @OneToOne(mappedBy = "documento")
    public FaseProcesso getFase() {
        return fase;
    }

    public void setFase(FaseProcesso fase) {
        this.fase = fase;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.extensao);
        hash = 23 * hash + Objects.hashCode(this.fase);
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
        final Documento other = (Documento) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.extensao, other.extensao)) {
            return false;
        }
        if (!Objects.equals(this.fase, other.fase)) {
            return false;
        }
        return true;
    }
}
