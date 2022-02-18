package br.com.sijoga.mb;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@ManagedBean
@Named
@ViewScoped
public class RelatorioMb implements Serializable {
    
    private Date dataInicio;
    private Date dataFim;
    private Integer tipo;

    @PostConstruct
    public void init() {

    }

    
    public void gerarRelatorio() {
        try {
            
        } catch (Exception e) {
            
        }
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
    
    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
