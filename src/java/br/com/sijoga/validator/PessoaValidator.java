package br.com.sijoga.validator;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.bean.Pessoa;
import br.com.sijoga.exception.AdvogadoException;
import br.com.sijoga.exception.JuizException;
import br.com.sijoga.util.Seguranca;
import br.com.sijoga.util.SijogaUtil;
import java.util.Date;

public class PessoaValidator {

    private static String validaPessoa(Pessoa pessoa) {
        String mensagem = "";
        Date hoje = new Date();

        if ((pessoa.getNome() == null)
                || (pessoa.getNome().isEmpty())
                || (pessoa.getNome().trim().equals(""))) {
            mensagem += "Nome inválido<br/>";
        } else if (pessoa.getNome().length() > 100) {
            mensagem += "O nome não pode ultrapassar [100] caracteres<br/>";
        }
        if (!SijogaUtil.isCPF(pessoa.getCpf())) {
            mensagem += "CPF inválido<br/>";
        }
        if ((pessoa.getEmail() != null)
                && (!pessoa.getEmail().isEmpty())
                && (!pessoa.getEmail().trim().equals(""))) {
            if (!Seguranca.isEmail(pessoa.getEmail())) {
                mensagem += "Email inválido<br/>";
            }
        }
        if (pessoa.getDataNascimento() == null) {
            mensagem += "Data de nascimento inválida<br/>";
        } else if (pessoa.getDataNascimento().after(hoje)) {
            mensagem += "Data de nascimento não pode ser superior a data de hoje "
                    + "[" + SijogaUtil.formataData(hoje) + "]<br/>";
        }
        if ((pessoa.getSexo() == null)
                || (pessoa.getSexo().trim().equals("")
                || ((!pessoa.getSexo().equals("M") && !pessoa.getSexo().equals("F"))))) {
            mensagem += "Sexo inválido<br/>";
        }
        if ((pessoa.getFone() != null) && (!pessoa.getFone().trim().isEmpty())) {
            if ((pessoa.getFone().length() < 10) && (pessoa.getFone().length() > 11)) {
                mensagem += "Telefone inválido<br/>";
            }
        }
        return mensagem;
    }

    public static void validaAdvogado(Advogado advogado) throws AdvogadoException {
        if (advogado == null) {
            throw new AdvogadoException("Advogado inválido");
        } else {
            String mensagem = validaPessoa(advogado);
            if (SijogaUtil.idade(advogado.getDataNascimento()) < 18) {
                mensagem += "O advogado não pode ser menor de idade<br/>";
            }
            if (advogado.getRegistroOab() == 0) {
                mensagem += "Numero de registro na OAB inválido<br/>";
            }
            if ((advogado.getSenha() == null)
                    || (advogado.getSenha().isEmpty())
                    || (advogado.getSenha().trim().equals(""))) {
                mensagem += "Senha inválida<br/>";
            } else {
                if (advogado.getSenha().length() < 5) {
                    mensagem += "Senha inválida, Mínimo [5] caracteres<br/>";
                }
                if (advogado.getSenha().length() > 100) {
                    mensagem += "Senha inválida, Máximo [100] caracteres<br/>";
                }
            }
            if (!mensagem.equals("")) {
                throw new AdvogadoException(mensagem);
            }
        }
    }
    
    public static void validaJuiz(Juiz juiz) throws JuizException {
        if (juiz == null) {
            throw new JuizException("Juiz inválido");
        } else {
            String mensagem = validaPessoa(juiz);
            if (SijogaUtil.idade(juiz.getDataNascimento()) < 18) {
                mensagem += "O juiz não pode ser menor de idade<br/>";
            }
            if (juiz.getRegistroOab() == 0) {
                mensagem += "Numero de registro na OAB inválido<br/>";
            }
            if ((juiz.getSenha() == null)
                    || (juiz.getSenha().isEmpty())
                    || (juiz.getSenha().trim().equals(""))) {
                mensagem += "Senha inválida<br/>";
            } else {
                if (juiz.getSenha().length() < 5) {
                    mensagem += "Senha inválida, Mínimo [5] caracteres<br/>";
                }
                if (juiz.getSenha().length() > 100) {
                    mensagem += "Senha inválida, Máximo [100] caracteres<br/>";
                }
            }
            if (!mensagem.equals("")) {
                throw new JuizException(mensagem);
            }
        }
    }

}
