package br.com.sijoga.util;

import br.com.sijoga.bean.Processo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

public class SijogaUtil {

    //Validação CPF
    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0         
                // (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    //Impressão CPF
    public static String imprimeCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
                + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    //Calculo idade
    public static int idade(Date dataCalc) {
        Calendar dataNascimento = new GregorianCalendar();
        Calendar dataHoje = Calendar.getInstance();
        int idade;

        dataNascimento.setTime(dataCalc);
        idade = dataHoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);
        return idade;
    }

    //Emissão de msg primeface
    public static FacesMessage emiteMsg(String mensagem, int tipo) {
        FacesMessage msg = null;
        switch (tipo) {
            case 1:
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
                break;
            case 2:
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, mensagem);
                break;
            case 3:
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
                break;
        }
        return msg;
    }

    //Mensagem e redirecionamento erro
    public static void mensagemErroRedirecionamento(String msg) {
        try {
            ExternalContext ctxExt = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().addMessage(null, emiteMsg(msg, 3));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            ctxExt.redirect(ctxExt.getRequestContextPath() + "/ErroPage.jsf");
        } catch (IOException ex) {
            Logger.getLogger(SijogaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Data formatada para impressão
    public static String formataData(Date data) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.format(data);
    }

    //Imprime fone
    public static String imprimeFone(String fon) {
        try {
            return ("(" + fon.substring(0, 2) + ")" + fon.substring(2, 7) + "-"
                    + fon.substring(7, 11));
        } catch (Exception e) {
            return null;
        }
    }

    //Salvar arquivo em pasta
    public static Boolean salvarArquivo(UploadedFile arquivo, String local, String nomeArquivo) {
        Boolean concluido = false;
        FileOutputStream fos = null;
        try {
            File caminho = new File(local, nomeArquivo);
            fos = new FileOutputStream(caminho);
            fos.write(arquivo.getContents());
            concluido = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SijogaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SijogaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SijogaUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return concluido;
    }

    //Excluir arquivo pasta
    public static Boolean apagarImagem(String local, String nomeArquivo) {
        try {
            File pasta = new File(local, nomeArquivo);
            pasta.delete();
            return true;
        } catch (Exception e) {
            System.out.println("****Problemas ao apagar arquivo" + nomeArquivo + "****");
            e.printStackTrace();
            return false;
        }
    }

    public static String printStatusProcesso(Processo p) {
        String rtn = "EM ABERTO";
        try {
            if (p.getVencedor() != null) {
                rtn = "ENCERRADO";
            } else if ((p.getFases() != null) && (!p.getFases().isEmpty())) {
                if (((p.getFases().get(p.getFases().size() - 1)).getTipo() == 2)
                        && ((p.getFases().get(p.getFases().size() - 1)).getJustificativa() == null)) {
                    rtn = "FASE DELIBERATIVA";
                }
            }
            return rtn;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            SijogaUtil.mensagemErroRedirecionamento("Houve um problema ao imprimir status do processo");
            return null;
        }
    }

    public static String statusHttp(int cod) {
        String rtn;
        switch (cod) {
            case 100:
                rtn = "Continuar";
                break;
            case 101:
                rtn = "Mudando protocolos";
                break;
            case 200:
                rtn = "Ok";
                break;
            case 201:
                rtn = "Criado";
                break;
            case 202:
                rtn = "Aceito";
                break;
            case 203:
                rtn = "Não Autorizado";
                break;
            case 204:
                rtn = "Nenhum conteúdo";
                break;
            case 205:
                rtn = "Reset";
                break;
            case 206:
                rtn = "Conteúdo parcial";
                break;
            case 207:
                rtn = "Status multi";
                break;
            case 300:
                rtn = "Múltipla escolha";
                break;
            case 301:
                rtn = "Movido permanentemente";
                break;
            case 302:
                rtn = "Encontrado";
                break;
            case 304:
                rtn = "Não modificado";
                break;
            case 305:
                rtn = "Use proxy";
                break;
            case 307:
                rtn = "Redirecionamento temporário";
                break;
            case 400:
                rtn = "Requisição inválida";
                break;
            case 401:
                rtn = "Não autorizado";
                break;
            case 402:
                rtn = "Pagamento necessário";
                break;
            case 403:
                rtn = "Proibido";
                break;
            case 404:
                rtn = "Não encontrado";
                break;
            case 405:
                rtn = "Método não permitido";
                break;
            case 406:
                rtn = "Não Aceitável";
                break;
            case 407:
                rtn = "Autenticação de proxy necessária";
                break;
            case 408:
                rtn = "Tempo de requisição esgotou";
                break;
            case 409:
                rtn = "Conflito";
                break;
            case 500:
                rtn = "Erro interno do servidor";
                break;
            case 501:
                rtn = "Não implementado";
                break;
            case 502:
                rtn = "Bad gateway";
                break;
            case 503:
                rtn = "Serviço indisponível";
                break;
            case 504:
                rtn = "Gateway time-out";
                break;
            case 505:
                rtn = "HTTP Version not supported.";
                break;
            default:
                rtn = "Desconhecido";
        }
        rtn = "[" + Integer.toString(cod) + "] - " + rtn;
        return rtn;
    }
    
    public static String caminhoProjeto() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.getRealPath("/");
    }
}
