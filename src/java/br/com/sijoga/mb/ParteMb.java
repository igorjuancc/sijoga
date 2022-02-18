package br.com.sijoga.mb;

import br.com.sijoga.bean.Endereco;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.exception.EnderecoException;
import br.com.sijoga.exception.ParteException;
import br.com.sijoga.facade.CidadeFacade;
import br.com.sijoga.facade.ParteFacade;
import br.com.sijoga.facade.ProcessoFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ParteMb implements Serializable {

    @Inject
    private LoginMb login;
    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Parte parte;
    private List<Estado> estados;
    private Estado estadoSelect;
    private List<Processo> processos;

    @PostConstruct
    public void init() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (ctx.getViewRoot().getViewId().equals("/Parte/InicioParte.xhtml")) {
                this.processos = ProcessoFacade.listaTodosProcessosParte(this.login.getParte());
            } else {
                this.parte = new Parte();
                this.parte.setEndereco(new Endereco());
                this.estados = CidadeFacade.listaEstado();
                this.estadoSelect = this.estados.get(0);
                this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            String msg = "Problemas ao inicializar página " + FacesContext.getCurrentInstance().getViewRoot().getViewId();
            SijogaUtil.mensagemErroRedirecionamento(msg);
        }
    }

    public void cadastrarParte() {
        FacesContext ctx = null;
        try {
            FacesMessage msg;
            ctx = FacesContext.getCurrentInstance();

            if (!this.parte.getSenha().equals(this.confirmaSenha)) {
                msg = SijogaUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                ParteFacade.cadastrarParte(this.parte);
                this.cadastroConcluido = true;
            }
        } catch (EnderecoException | ParteException e) {
            if (ctx != null) {
                ctx.addMessage(null, SijogaUtil.emiteMsg(e.getMessage(), 2));
            } else {
                e.printStackTrace(System.out);
                SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao cadastrar parte");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao cadastrar parte");
        }
    }

    public void buscaCidadePorEstado() {
        try {
            this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao buscar dados de cidades");
        }
    }

    public void verProcesso(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Parte/VisualizarProcesso.jsf");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao visualizar detalhes do processo");
        }
    }

    public String printStatusProcesso(Processo p) {
        return SijogaUtil.printStatusProcesso(p);
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public Boolean getCadastroConcluido() {
        return cadastroConcluido;
    }

    public void setCadastroConcluido(Boolean cadastroConcluido) {
        this.cadastroConcluido = cadastroConcluido;
    }

    public Parte getParte() {
        return parte;
    }

    public void setParte(Parte parte) {
        this.parte = parte;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public Estado getEstadoSelect() {
        return estadoSelect;
    }

    public void setEstadoSelect(Estado estadoSelect) {
        this.estadoSelect = estadoSelect;
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }

    public LoginMb getLogin() {
        return login;
    }

    public void setLogin(LoginMb login) {
        this.login = login;
    }
}
