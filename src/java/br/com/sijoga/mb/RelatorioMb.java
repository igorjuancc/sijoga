package br.com.sijoga.mb;

import br.com.sijoga.exception.RelatorioException;
import br.com.sijoga.facade.RelatorioFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.PF;

@ManagedBean
@Named
@ViewScoped
public class RelatorioMb implements Serializable {

    private Date dataInicio;
    private Date dataFim;
    private Integer tipo;
    private byte[] previewRelatorio;

    @PostConstruct
    public void init() {
    }

    public void gerarRelatorio() {
        try {
            switch (this.tipo) {
                case 1:
                    relatorioProcessosAbertos();
                    break;
                case 2:
                    relatorioProcessosEncerrados();
                    break;
                default:
                    SijogaUtil.mensagemErroRedirecionamento("Relatório não encontrado");
            }
        } catch (RelatorioException e) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (ctx != null) {
                ctx.addMessage(null, SijogaUtil.emiteMsg(e.getMessage(), 2));
                PF.current().ajax().update("formRelatorio:mensagem");
            } else {
                e.printStackTrace(System.out);
                SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao processar relatório");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao processar relatório");
        }
    }

    private void relatorioProcessosAbertos() throws RelatorioException {
        this.previewRelatorio = RelatorioFacade.relatorioProcessosAbertos(dataInicio, dataFim);
        if (this.previewRelatorio.length <= 961) {
            throw new RelatorioException("Não há dados para as datas informadas");
        }
        PF.current().executeScript("PF('divViewRelatorio').show()");
    }
    
    private void relatorioProcessosEncerrados() throws RelatorioException {
        this.previewRelatorio = RelatorioFacade.relatorioProcessosEncerrados(dataInicio, dataFim);
        if (this.previewRelatorio.length <= 961) {
            throw new RelatorioException("Não há dados para as datas informadas");
        }
        PF.current().executeScript("PF('divViewRelatorio').show()");
    }
    
    public String verRelatorioBase64(byte[] relatorio) {
        return Base64.getEncoder().encodeToString(relatorio);
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

    public byte[] getPreviewRelatorio() {
        return previewRelatorio;
    }

    public void setPreviewRelatorio(byte[] previewRelatorio) {
        this.previewRelatorio = previewRelatorio;
    }
}
