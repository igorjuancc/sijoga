package br.com.sijoga.mb;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.bean.Processo;
import br.com.sijoga.dto.EnderecoDto;
import br.com.sijoga.dto.IntimacaoDto;
import br.com.sijoga.dto.OficialDto;
import br.com.sijoga.facade.AdvogadoFacade;
import br.com.sijoga.facade.FaseProcessoFacade;
import br.com.sijoga.facade.IntimacaoFacade;
import br.com.sijoga.facade.ParteFacade;
import br.com.sijoga.facade.ProcessoFacade;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class ProcessoMb implements Serializable {

    @Inject
    private LoginMb login;
    private Boolean cadastroConcluido;
    private Processo processo;
    private FaseProcesso faseProcesso;
    private List<Parte> clientes;
    private List<Advogado> advogados;
    private UploadedFile arquivo;
    private int renderJuiz;
    private List<OficialDto> oficiais;
    private IntimacaoDto intimacao;
    private Parte intimado;

    @PostConstruct
    public void init() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();

            switch (ctx.getViewRoot().getViewId()) {
                case "/Advogado/CadastroProcesso.xhtml":
                    this.processo = new Processo();
                    this.faseProcesso = new FaseProcesso();
                    this.advogados = AdvogadoFacade.listaAdvogados();
                    this.clientes = ParteFacade.listaClientes();
                    this.advogados.remove(this.login.getAdvogado());

                    this.faseProcesso.setAdvogado(this.login.getAdvogado());
                    this.processo.setAdvogadoPromovente(this.login.getAdvogado());
                    this.processo.setFases(new ArrayList<>());
                    this.processo.getFases().add(this.faseProcesso);
                    break;
                case "/Advogado/VisualizarProcesso.xhtml":
                    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso") == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    } else {
                        int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso");
                        this.processo = ProcessoFacade.buscaProcessoId(idProcesso);
                    }
                    break;
                case "/Advogado/VisualizarFase.xhtml":
                    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idFase") == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    } else {
                        int idFaseProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idFase");
                        this.faseProcesso = FaseProcessoFacade.buscaFaseProcessoId(idFaseProcesso);
                        this.processo = this.faseProcesso.getProcesso();
                    }
                    break;
                case "/Advogado/NovaFase.xhtml":
                    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso") == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    } else {
                        int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso");
                        this.processo = ProcessoFacade.buscaProcessoId(idProcesso);
                        this.faseProcesso = new FaseProcesso();
                        this.faseProcesso.setAdvogado(this.login.getAdvogado());
                        this.faseProcesso.setProcesso(this.processo);
                    }
                    break;
                case "/Juiz/VisualizarProcesso.xhtml":
                    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso") == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Juiz/InicioJuiz.jsf");
                    } else {
                        int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso");
                        this.processo = ProcessoFacade.buscaProcessoId(idProcesso);
                        this.faseProcesso = this.processo.getFases().get(this.processo.getFases().size() - 1);
                        this.oficiais = IntimacaoFacade.listaOficiais();
                        this.intimacao = new IntimacaoDto();
                        this.intimado = new Parte();
                    }
                    break;
                case "/Parte/VisualizarProcesso.xhtml":
                    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso") == null) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/Parte/InicioParte.jsf");
                    } else {
                        int idProcesso = (int) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idProcesso");
                        this.processo = ProcessoFacade.buscaProcessoId(idProcesso);
                        this.faseProcesso = this.processo.getFases().get(this.processo.getFases().size() - 1);
                        this.oficiais = IntimacaoFacade.listaOficiais();
                    }
                    break;
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarProcesso() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            List<String> mensagens = ProcessoFacade.cadastrarProcesso(this.processo, this.arquivo);
            if (!mensagens.isEmpty()) {
                for (String print : mensagens) {
                    msg = SijogaUtil.emiteMsg(print, 2);
                    ctx.addMessage(null, msg);
                }
            } else {
                this.cadastroConcluido = true;
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cadastrarFaseProcesso() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();

            List<String> mensagens = FaseProcessoFacade.cadastrarFaseProcesso(this.faseProcesso, this.arquivo);
            if (!mensagens.isEmpty()) {
                for (String print : mensagens) {
                    msg = SijogaUtil.emiteMsg(print, 2);
                    ctx.addMessage(null, msg);
                }
            } else {
                this.cadastroConcluido = true;
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void verFase(int id) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idFase", id);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/VisualizarFase.jsf");
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Exceutado pelo juiz
    public void atualizarFase(int opc) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            switch (opc) {
                case 1:
                    this.faseProcesso.setJustificativa("");
                    FaseProcessoFacade.atualizarFaseProcesso(this.faseProcesso);
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", this.processo.getId());
                    ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/VisualizarProcesso.jsf");
                    break;
                case 2:
                    this.faseProcesso.setJustificativa(this.faseProcesso.getJustificativa().toUpperCase());
                    FaseProcessoFacade.atualizarFaseProcesso(this.faseProcesso);
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", this.processo.getId());
                    ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/VisualizarProcesso.jsf");
                    break;
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void encerrarProcesso() {
        try {
            String mensagem = ProcessoFacade.finalizarProcesso(this.processo);
            if (mensagem != null) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage msg = SijogaUtil.emiteMsg(mensagem, 2);
                ctx.addMessage(null, msg);
            } else {
                ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", this.processo.getId());
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/VisualizarProcesso.jsf");
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void criarIntimacao() {
        try {
            FacesMessage msg;
            FacesContext ctx = FacesContext.getCurrentInstance();
            
            this.intimacao.setCpf(this.intimado.getCpf());
            this.intimacao.setEndereco(new EnderecoDto());
            this.intimacao.getEndereco().setBairro(this.intimado.getEndereco().getBairro());
            this.intimacao.getEndereco().setCep(this.intimado.getEndereco().getCep());            
            this.intimacao.getEndereco().setCidade(this.intimado.getEndereco().getCidade().getId());
            this.intimacao.getEndereco().setComplemento(this.intimado.getEndereco().getComplemento());
            this.intimacao.getEndereco().setNumero(this.intimado.getEndereco().getNumero());
            this.intimacao.getEndereco().setRua(this.intimado.getEndereco().getRua());
            this.intimacao.setCpf(this.intimado.getCpf());
            this.intimacao.setNome(this.intimado.getNome());
            this.intimacao.setProcesso(this.processo.getId());
            
            List<String> mensagens = IntimacaoFacade.cadastrarIntimacao(this.intimacao);
            if (!mensagens.isEmpty()) {
                for (String print : mensagens) {
                    msg = SijogaUtil.emiteMsg(print, 2);
                    ctx.addMessage(null, msg);
                }
            } else {
                ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idProcesso", this.processo.getId());
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/VisualizarProcesso.jsf");                
            }
        } catch (Exception e) {
            try {
                SijogaUtil.mensagemErroRedirecionamento(e);
            } catch (IOException ex) {
                Logger.getLogger(ProcessoMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String printStatusProcesso(Processo p) {
        return SijogaUtil.printStatusProcesso(p);
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public LoginMb getLogin() {
        return login;
    }

    public void setLogin(LoginMb login) {
        this.login = login;
    }

    public FaseProcesso getFaseProcesso() {
        return faseProcesso;
    }

    public void setFaseProcesso(FaseProcesso faseProcesso) {
        this.faseProcesso = faseProcesso;
    }

    public List<Parte> getClientes() {
        return clientes;
    }

    public void setClientes(List<Parte> clientes) {
        this.clientes = clientes;
    }

    public List<Advogado> getAdvogados() {
        return advogados;
    }

    public void setAdvogados(List<Advogado> advogados) {
        this.advogados = advogados;
    }

    public Boolean getCadastroConcluido() {
        return cadastroConcluido;
    }

    public void setCadastroConcluido(Boolean cadastroConcluido) {
        this.cadastroConcluido = cadastroConcluido;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public void uploadArquivo(FileUploadEvent event) {
        this.arquivo = event.getFile();
    }

    public int getRenderJuiz() {
        return renderJuiz;
    }

    public void setRenderJuiz(int renderJuiz) {
        this.renderJuiz = renderJuiz;
    }

    public List<OficialDto> getOficiais() {
        return oficiais;
    }

    public void setOficiais(List<OficialDto> oficiais) {
        this.oficiais = oficiais;
    }

    public IntimacaoDto getIntimacao() {
        return intimacao;
    }

    public void setIntimacao(IntimacaoDto intimacao) {
        this.intimacao = intimacao;
    }

    public Parte getIntimado() {
        return intimado;
    }

    public void setIntimado(Parte intimado) {
        this.intimado = intimado;
    }
}
