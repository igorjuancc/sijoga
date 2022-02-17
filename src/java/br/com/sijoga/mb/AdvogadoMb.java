package br.com.sijoga.mb;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Endereco;
import br.com.sijoga.bean.Estado;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.exception.AdvogadoException;
import br.com.sijoga.exception.EnderecoException;
import br.com.sijoga.facade.AdvogadoFacade;
import br.com.sijoga.facade.CidadeFacade;
import br.com.sijoga.facade.ProcessoFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdvogadoMb implements Serializable {

    @Inject
    private LoginMb login;
    private String confirmaSenha;
    private Boolean cadastroConcluido;
    private Advogado advogado;
    private List<Estado> estados;
    private Estado estadoSelect;
    private List<Processo> processos;
    private int opcFiltro;

    @PostConstruct
    public void init() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            if (ctx.getViewRoot().getViewId().equals("/Advogado/InicioAdvogado.xhtml")) {
                this.processos = ProcessoFacade.listaTodosProcessosAdvogado(this.login.getAdvogado());
            } else {
                this.advogado = new Advogado();
                this.advogado.setEndereco(new Endereco());
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

    public void cadastrarAdvogado() {
        FacesContext ctx = null;
        try {
            FacesMessage msg;
            ctx = FacesContext.getCurrentInstance();
            if (!this.advogado.getSenha().equals(this.confirmaSenha)) {
                msg = SijogaUtil.emiteMsg("Senha e confirmação não conferem", 2);
                ctx.addMessage(null, msg);
            } else {
                AdvogadoFacade.cadastrarAdvogado(this.advogado);
                this.cadastroConcluido = true;
            }
        } catch (AdvogadoException | EnderecoException e) {
            if (ctx != null) {
                ctx.addMessage(null, SijogaUtil.emiteMsg(e.getMessage(), 2));
            } else {
                e.printStackTrace(System.out);
                SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao cadastrar advogado");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao cadastrar advogado");
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

    public void listaProcessoAdvogado() {
        try {
            switch (this.opcFiltro) {
                case 1:
                    this.processos = ProcessoFacade.listaTodosProcessosAdvogado(this.login.getAdvogado());
                    break;
                case 2:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoAbertos(this.login.getAdvogado());
                    break;
                case 3:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoFechados(this.login.getAdvogado());
                    break;
                case 4:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoPromovido(this.login.getAdvogado());
                    break;
                case 5:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoPromovente(this.login.getAdvogado());
                    break;
                case 6:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoPromovidoGanho(this.login.getAdvogado());
                    break;
                case 7:
                    this.processos = ProcessoFacade.listaProcessosAdvogadoPromoventeGanho(this.login.getAdvogado());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao listar processos de advogado");
        }
    }

    public void verProcesso(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/VisualizarProcesso.jsf");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao visualizar detalhes do processo");
        }
    }

    public void redirecionar() {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/index.jsf");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao redirecionar página");
        }
    }

    public void novaFase(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/NovaFase.jsf");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao redirecionar página de nova fase");
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

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
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

    public int getOpcFiltro() {
        return opcFiltro;
    }

    public void setOpcFiltro(int opcFiltro) {
        this.opcFiltro = opcFiltro;
    }
}
