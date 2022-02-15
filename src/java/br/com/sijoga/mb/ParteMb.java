package br.com.sijoga.mb;

import br.com.sijoga.bean.Endereco;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.facade.CidadeFacade;
import br.com.sijoga.facade.ParteFacade;
import br.com.sijoga.facade.ProcessoFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarParte() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            if (!this.parte.getSenha().equals(this.confirmaSenha)) {
                msg = SijogaUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                List<String> mensagens = ParteFacade.cadastrarParte(this.parte);
                if (!mensagens.isEmpty()) {
                    for (String print : mensagens) {
                        msg = SijogaUtil.emiteMsg(print, 2);
                        ctx.addMessage(null, msg);
                    }
                } else {
                    this.cadastroConcluido = true;
                }
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void buscaCidadePorEstado() {
        try {
            this.estadoSelect.setCidades(CidadeFacade.listaCidadePorEstado(this.estadoSelect));
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ParteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void verProcesso(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Parte/VisualizarProcesso.jsf");
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(AdvogadoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
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
