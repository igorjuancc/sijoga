package br.com.sijoga.mb;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Parte;
import br.com.sijoga.facade.LoginFacade;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginMb implements Serializable {

    private String email;
    private String senha;
    private int tipoLogin;
    private Advogado advogado = new Advogado();
    private Juiz juiz = new Juiz();
    private Parte parte = new Parte();

    public LoginMb() {
    }

    public void efetuaLogin() {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage msg;

            switch (this.tipoLogin) {
                case 1:
                    this.advogado.setSenha(Seguranca.md5(this.senha));
                    this.advogado.setEmail(this.email);
                    this.advogado = LoginFacade.loginAdvogado(this.advogado);

                    if (this.advogado != null) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Advogado/InicioAdvogado.jsf");
                    } else {
                        this.senha = "";
                        this.advogado = new Advogado();
                        this.juiz = new Juiz();
                        this.parte = new Parte();
                        msg = SijogaUtil.emiteMsg("Usuario ou Senha Inválido!", 3);
                        ctx.addMessage(null, msg);
                    }
                    break;
                case 2:
                    this.juiz.setSenha(Seguranca.md5(this.senha));
                    this.juiz.setEmail(this.email);
                    this.juiz = LoginFacade.loginJuiz(this.juiz);

                    if (this.juiz != null) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Juiz/InicioJuiz.jsf");
                    } else {
                        this.senha = "";
                        this.advogado = new Advogado();
                        this.juiz = new Juiz();
                        this.parte = new Parte();
                        msg = SijogaUtil.emiteMsg("Usuario ou Senha Inválido!", 3);
                        ctx.addMessage(null, msg);
                    }
                    break;
                case 3:
                    this.parte.setSenha(Seguranca.md5(this.senha));
                    this.parte.setEmail(this.email);
                    this.parte = LoginFacade.loginParte(this.parte);

                    if (this.parte != null) {
                        ctxExt.redirect(ctxExt.getRequestContextPath() + "/Parte/InicioParte.jsf");
                    } else {
                        this.senha = "";
                        this.advogado = new Advogado();
                        this.juiz = new Juiz();
                        this.parte = new Parte();
                        msg = SijogaUtil.emiteMsg("Usuario ou Senha Inválido!", 3);
                        ctx.addMessage(null, msg);
                    }
                    break;
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao efetuar login");
        }
    }

    public void logout() {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            if ((this.advogado.getId() != 0) || (this.juiz.getId() != 0) || (this.parte.getId() != 0)) {
                ctxExt.invalidateSession();
                ctxExt.redirect(ctxExt.getRequestContextPath() + "/index.jsf");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao efetuar logout");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(int tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public Advogado getAdvogado() {
        return advogado;
    }

    public void setAdvogado(Advogado advogado) {
        this.advogado = advogado;
    }

    public Juiz getJuiz() {
        return juiz;
    }

    public void setJuiz(Juiz juiz) {
        this.juiz = juiz;
    }

    public Parte getParte() {
        return parte;
    }

    public void setParte(Parte parte) {
        this.parte = parte;
    }
}
